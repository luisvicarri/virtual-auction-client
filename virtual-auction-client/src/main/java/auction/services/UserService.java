package auction.services;

import auction.controllers.SessionController;
import auction.models.User;
import auction.proxies.UserServiceProxy;
import auction.repositories.UserRepository;
import auction.utils.PasswordUtil;
import java.util.Optional;
import java.util.UUID;
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

    public boolean signIn(String name, String password, SessionController session) {
        Optional<User> userOptional = repository.findByUsername(name);
        if (userOptional.isPresent()) {
            User userFinded = userOptional.get();
            if (proxy.signIn(Optional.of(userFinded.getId()), name, password)) {
                session.logIn(userFinded);
                return true;
            }
        }

        return false;
    }

    public boolean insert(User newUser) {
        logger.info("Attempting to register user: {}", newUser.getName());
        if (repository.findByUsername(newUser.getName()).isPresent()) {
            logger.info("Username already in the server");
            return false;
        }

        boolean isRegistered = proxy.insert(newUser);
        boolean success = repository.addUser(newUser);

        if (isRegistered && success) {
            logger.info("User registered successfully: {} - {}", newUser.getName(), newUser.getId().toString());
            return true;
        } else {
            logger.warn("Failed to register user: {}", newUser.getName());
            return false;
        }

    }

    public User getUserLogged(SessionController session) {
        return session.getUserLogged();
    }
    
    public String hashPassword(String password) {
        return PasswordUtil.hashPassword(password);
    }
    
    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }
}