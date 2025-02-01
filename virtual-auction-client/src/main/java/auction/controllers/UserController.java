package auction.controllers;

import auction.models.User;
import auction.services.UserService;
import java.util.Optional;
import java.util.UUID;

public class UserController {
    
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    
    public User getUserLogged(SessionController session){
        return service.getUserLogged(session);
    }
    
    public boolean signIn(String name, String password, SessionController session) {
        return service.signIn(name, password, session);
    }
    
    public boolean signUp(User newUser) {
        return service.insert(newUser);
    }
    
    public String hashPassword(String password) {
        return service.hashPassword(password);
    }
    
    public Optional<User> findById(UUID id) {
        return service.findById(id);
    }
}
