package auction.main;

import auction.controllers.AppController;
import auction.models.User;
import auction.utils.PasswordUtil;

public class Seeding {

    private final AppController appController;

    public Seeding(AppController appController) {
        this.appController = appController;
    }
    
    public void start() {
        String name = "JohnDoe";
        String plainPassword = "securePassword";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        System.out.println("Registering user...");
        User newUser = new User(name, plainPassword, hashedPassword);
        boolean isRegistered = appController.getUserController().signUp(newUser);
        if (isRegistered) {
            System.out.println("User registered successfully!");
        } else {
            System.out.println("Failed to register user.");
        }
    }

}
