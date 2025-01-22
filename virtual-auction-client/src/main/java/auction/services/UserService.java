package auction.services;

import auction.models.User;
import auction.proxies.UserServiceProxy;
import auction.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {
    
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserServiceProxy proxy;

    public UserService(UserRepository repository, UserServiceProxy proxy) {
        this.repository = repository;
        this.proxy = proxy;
    }
    
    public boolean insert(User newUser) {
        logger.info("Attempting to register user: {}", newUser.getName());
        if (findByUsername(newUser.getName())!= null) {
            return false;
        }
        
        boolean isRegistered = proxy.insert(newUser);
        boolean success = repository.addUser(newUser);

        if (isRegistered && success) {
            logger.info("User registered successfully: {} - {}", newUser.getName(), newUser.getId().toString());
            return true;
        } else {
            logger.warn("Failed to register user: {} - {}", newUser.getName(), newUser.getId().toString());
            return false;
        }

    }
    
    public User findByUsername(String name) {
        return repository.getUsers().values()
                .stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}