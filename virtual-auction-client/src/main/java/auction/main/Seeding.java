package auction.main;

import auction.controllers.AppController;
import auction.dispatchers.MessageDispatcher;

public class Seeding {

    private final AppController appController;

    public Seeding(AppController appController) {
        this.appController = appController;
    }
    
    public void start() {
        MessageDispatcher dispatcher = appController.getMulticastController().getDispatcher();
    }

}