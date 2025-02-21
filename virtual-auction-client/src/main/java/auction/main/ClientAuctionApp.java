package auction.main;

import auction.controllers.AppController;
import auction.views.frames.Frame;

public class ClientAuctionApp {

    public static Frame frame;

    public static void main(String[] args) {
        AppController appController = new AppController();
        Seeding seeding = new Seeding(appController);
        seeding.start();
        frame = new Frame(appController);
        frame.start();
    }
}