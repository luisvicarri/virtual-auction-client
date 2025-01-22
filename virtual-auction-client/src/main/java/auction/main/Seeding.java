package auction.main;

import auction.models.User;
import auction.proxies.UserServiceProxy;
import auction.repositories.UserRepository;
import auction.services.UserService;
import auction.utils.PasswordUtil;

public class Seeding {
    
    public void start() {
        String host = "localhost";
        int port = 8080;

        UserServiceProxy proxy = new UserServiceProxy(host, port);
        UserRepository repository = new UserRepository();
        UserService service = new UserService(repository, proxy);

        String name = "JohnDoe";
        String plainPassword = "securePassword";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);

        System.out.println("Registering user...");
        User newUser = new User(name, plainPassword, hashedPassword);
        boolean isRegistered = service.insert(newUser);

        if (isRegistered) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to register user.");
        }
        
    }
    
}
