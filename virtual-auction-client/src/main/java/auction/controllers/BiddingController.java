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
    
    public void storeBids(List<Bid> bids) {
        if (bids == null || bids.isEmpty()) {
            return;
        }
        service.storeBids(bids);
    }
    
    public List<Bid> getBids() {
        return service.getBids();
    }
    
    public void placeBid(Item currentItem) {
        service.placeBid(currentItem);
    }
    
}