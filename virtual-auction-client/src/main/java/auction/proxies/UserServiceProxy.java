package auction.proxies;

import auction.models.User;
import auction.models.dtos.Request;
import auction.models.dtos.Response;
import auction.services.interfaces.UserServiceInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceProxy implements UserServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceProxy.class);
    private final String host;
    private final int port;
    private final ObjectMapper mapper = new ObjectMapper();

    public UserServiceProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean insert(User newUser) {
        String name = newUser.getName();
        String hashedPassword = newUser.getHashedPassword();
        Request request = new Request("signUp", name, hashedPassword);

        try (Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String requestJson = mapper.writeValueAsString(request);
            logger.info("Sending request to server: {}", requestJson);
            out.println(requestJson);

            String responseJson = in.readLine();
            logger.info("Received response from server: {}", responseJson);
            Response response = mapper.readValue(responseJson, Response.class);
            
            if ("success".equals(response.getStatus())) {
                UUID id = UUID.fromString(response.getMessage());
                newUser.setId(id);
                logger.info("User registered successfully: {}", newUser.getId());
                return true;
            } else {
                logger.warn("Failed to register user: {}", response.getMessage());
            }
        } catch (IOException ex) {
            logger.error("Failed to establish connection with the server at {}:{}", host, port, ex);
            throw new RuntimeException("Error communicating with server", ex);
        }

        return false;
        
    }

}
