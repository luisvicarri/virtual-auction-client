package auction.controllers;

import auction.services.AuctionService;

public class AuctionController {
    
    private final AuctionService service;

    public AuctionController(AuctionService service) {
        this.service = service;
    }
    
    public void startAuction(String message) {
        service.startAuction(message);
    }
    
    public void updateTime(String message) {
        service.updateTime(message);
    }
    
}
