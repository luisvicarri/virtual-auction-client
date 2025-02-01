package auction.services;

import auction.models.Bid;
import auction.repositories.BiddingRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiddingService {
    
    private static final Logger logger = LoggerFactory.getLogger(BiddingService.class);
    private final BiddingRepository repository;

    public BiddingService(BiddingRepository repository) {
        this.repository = repository;
    }
    
    public Map<UUID, List<Bid>> getBidsByItem() {
        return repository.getBidsByItem();
    }
    
    public void storeBids(List<Bid> bids) {
        repository.storeBids(bids);
    }
    
    public List<Bid> getBids() {
        return repository.getBidsTemp();
    }
    
}