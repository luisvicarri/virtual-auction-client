package auction.proxies;

import auction.models.User;
import auction.models.dtos.Request;
import auction.models.dtos.Response;
import auction.services.interfaces.UserServiceInterface;
import auction.utils.ConfigManager;
import auction.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceProxy implements UserServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceProxy.class);
    private final ObjectMapper mapper = JsonUtil.getObjectMapper();
    private final String host;
    private final int port;

    public UserServiceProxy() {
        this.host = ConfigManager.get("HOST");
        this.port = Integer.parseInt(ConfigManager.get("PORT"));
    }

    @Override
    public boolean signIn(Optional<UUID> id, String name, String password) {
        Request request = new Request(
                "SIGN-IN",
                name,
                password,
                id
        );
        Response response = sendRequest(request);
        return response != null && "SUCCESS".equals(response.getStatus());
    }

    @Override
    public boolean insert(User newUser) {
        Request request = new Request(
                "SIGN-UP",
                newUser.getName(),
                newUser.getHashedPassword(),
                null
        );
        Response response = sendRequest(request);

        if (response != null && "SUCCESS".equals(response.getStatus())) {
            newUser.setId(UUID.fromString(response.getMessage()));
            logger.info("User registered successfully: {}", newUser.getId());
            return true;
        }

        logger.warn("Failed to register user: {}", response != null ? response.getMessage() : "No response from server");
        return false;
    }

    private Response sendRequest(Request request) {
        try ( Socket socket = new Socket(host, port);  PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String requestJson = mapper.writeValueAsString(request);
            logger.info("Sending request to server: {}", requestJson);
            out.println(requestJson);

            String responseJson = in.readLine();
            logger.info("Received response from server: {}", responseJson);
            return mapper.readValue(responseJson, Response.class);

        } catch (IOException ex) {
            logger.error("Failed to establish connection with the server at {}:{}", host, port, ex);
            throw new RuntimeException("Error communicating with server", ex);
        }
    }

}
