package auction.controllers;

import auction.models.User;
import auction.services.UserService;

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
    
}
