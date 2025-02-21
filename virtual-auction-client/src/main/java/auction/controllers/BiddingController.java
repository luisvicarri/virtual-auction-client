package auction.controllers;

import auction.models.Bid;
import auction.models.Item;
import auction.services.BiddingService;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BiddingController {
    
    private final BiddingService service;

    public BiddingController(BiddingService service) {
        this.service = service;
    }
    
    public Map<UUID, List<Bid>> getBidsByItem() {
        return service.getBidsByItem();
    }

    public void addBid(UUID itemId, Bid bid) {
        service.addBid(itemId, bid);
    }

    public List<Bid> getBidsByItemId(UUID itemId) {
        return service.getBidsByItemId(itemId);
    }

    public void clearAllBids() {
        service.clearAllBids();
    }
    
    public void placeBid(Item currentItem) {
        service.placeBid(currentItem);
    }
    
    public void addBids(UUID itemId, List<Bid> bids) {
        service.addBids(itemId, bids);
    }
}