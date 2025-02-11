package auction.proxies;

import auction.main.ClientAuctionApp;
import auction.models.User;
import auction.models.dtos.Request;
import auction.models.dtos.Response;
import auction.security.SecurityMiddleware;
import auction.services.interfaces.UserServiceInterface;
import auction.utils.ConfigManager;
import auction.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceProxy implements UserServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceProxy.class);
    private final ObjectMapper mapper = JsonUtil.getObjectMapper();
    private final SecurityMiddleware securityMiddleware;
    private final String host;
    private final int port;

    public UserServiceProxy() {
        this.host = ConfigManager.get("HOST");
        this.port = Integer.parseInt(ConfigManager.get("PORT"));
        this.securityMiddleware = new SecurityMiddleware();
    }

    @Override
    public boolean signIn(Optional<UUID> id, String name, String password) {
        Request request = new Request("SIGN-IN", "User authentication request");
        request.addData("username", name);
        request.addData("password", password);
        id.ifPresent(uuid -> request.addData("user_id", uuid.toString()));

        try {
            String requestJson = mapper.writeValueAsString(request);
            // Assinar a requisição
            String signature = signRequest(requestJson, id);
            Response response = sendRequest(requestJson, signature);
            if (response != null && "SUCCESS".equals(response.getStatus())) {
                response.getData().ifPresent(data -> {
                    ConfigManager.set("MULTICAST_ADDRESS", data.get("MULTICAST_ADDRESS").toString());
                    
                    String encodedSymmetricKey = (String) data.get("symmetricKey");
                    byte[] symmetricKey = Base64.getDecoder().decode(encodedSymmetricKey);                            
                    ClientAuctionApp.frame.getAppController().getKeyController().saveSymmetrickey(symmetricKey);
                });
                return true;
            }
        } catch (JsonProcessingException ex) {
            logger.error("Error serializing json", ex);
        }
        return false;
    }

    @Override
    public boolean insert(User newUser) {
        try {
            Request request = new Request("SIGN-UP", "User registration request");
            request.addData("username", newUser.getName());
            request.addData("password_hash", newUser.getHashedPassword());
            request.addData("public_key", newUser.getEncodedPublicKey());

            String requestJson = mapper.writeValueAsString(request);
            Response response = sendRequest(requestJson, null);
            if (response != null && "SUCCESS".equals(response.getStatus())) {
                response.getData().ifPresent(data -> {
                    String userId = data.get("userId").toString();
                    newUser.setId(UUID.fromString(userId));
                    logger.info("User registered successfully: {}", newUser.getId());

                    String encodedServerPublicKey = data.get("server-public-key").toString();
                    ClientAuctionApp.frame.getAppController().getKeyController().saveKey(encodedServerPublicKey);
                });

                return true;
            }

            logger.warn("Failed to register user: {}", response != null ? response.getMessage() : "No response from server");
            return false;
        } catch (JsonProcessingException ex) {
            logger.error("Error desserializing json", ex);
        }
        return false;
    }

    private String signRequest(String requestJson, Optional<UUID> userId) {
        if (userId.isEmpty()) {
            throw new IllegalStateException("User ID is required for signing the request");
        }
        return securityMiddleware.signRequest(requestJson, getUserPrivateKey(userId.get()));
    }

    private PrivateKey getUserPrivateKey(UUID userId) {
        return ClientAuctionApp.frame.getAppController()
                .getUserController()
                .findById(userId)
                .map(User::getPrivateKey)
                .orElseThrow(() -> new IllegalStateException("User not found or does not have a private key"));
    }

    private Response sendRequest(String requestJson, String signature) {
        try ( Socket socket = new Socket(host, port);  PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            logger.info("Sending request to server: {}", requestJson);
            out.println(requestJson);
            if (signature != null) {
                out.println(signature);
            } else {
                out.println(); // Envia uma linha vazia para não quebrar a leitura do servidor
            }

            String responseJson = in.readLine();
            String responseSignature = in.readLine();
            logger.info("Received response: {}", responseJson);
            logger.info("Received signature: {}", responseSignature);

            // Identificar o tipo de requisição
            Request request = mapper.readValue(requestJson, Request.class);
            boolean requiresSignatureValidation = "SIGN-IN".equals(request.getStatus());

            if (requiresSignatureValidation) {
                if (responseSignature == null || responseSignature.isEmpty()) {
                    logger.warn("Server response is not signed!");
                    return null;
                }

                // Obter chave pública do servidor
                PublicKey serverPublicKey = ClientAuctionApp.frame.getAppController()
                        .getKeyController()
                        .getServerPublicKey();

                // Validar assinatura
                boolean validSignature = securityMiddleware.verifyRequest(responseJson, responseSignature, serverPublicKey);
                if (!validSignature) {
                    logger.warn("Invalid signature on server response!");
                    return null;
                }
            }
            return mapper.readValue(responseJson, Response.class);

        } catch (IOException ex) {
            logger.error("Failed to establish connection with the server at {}:{}", host, port, ex);
            throw new RuntimeException("Error communicating with server", ex);
        }
    }
}
