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
    
    /**
     * Retorna todos os lances organizados por item.
     */
    public Map<UUID, List<Bid>> getBidsByItem() {
        return service.getBidsByItem();
    }

    /**
     * Adiciona um lance a um item específico.
     */
    public void addBid(UUID itemId, Bid bid) {
        service.addBid(itemId, bid);
    }

    /**
     * Obtém os lances de um item específico.
     */
    public List<Bid> getBidsByItemId(UUID itemId) {
        return service.getBidsByItemId(itemId);
    }

    /**
     * Remove todos os lances armazenados.
     */
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