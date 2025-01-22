package auction.controllers;

import auction.models.User;

public class SessionController {

    private static SessionController instance;
    private User userLoggedIn;

    private SessionController() {
    }

    public static SessionController getInstance() {
        if (instance == null) {
            instance = new SessionController();
        }

        return instance;
    }

    public void logIn(User userToLogIn) {
        this.userLoggedIn = userToLogIn;
    }

    public void logOut() {
        this.userLoggedIn = null;
    }

    public User getUserLogged() {
        return this.userLoggedIn;
    }

    public boolean isLoggedIn() {
        return this.userLoggedIn != null;
    }

}