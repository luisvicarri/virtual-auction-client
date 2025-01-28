package auction.main;

import auction.views.frames.Frame;

public class ClientAuctionApp {

    public static Frame frame;
    
    public static void main(String[] args) {
        Seeding seed = new Seeding();
        seed.start();
        
        ClientAuctionApp.frame = new Frame();
        ClientAuctionApp.frame.start();
    }

}
