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
    
    public void signUp(User newUser) {
        
    }
    
}
