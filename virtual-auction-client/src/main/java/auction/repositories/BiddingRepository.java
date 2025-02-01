package auction.repositories;

import auction.models.Bid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiddingRepository {
    
    private static final Logger logger = LoggerFactory.getLogger(BiddingRepository.class);
    private final Map<UUID, List<Bid>> bidsByItem = new HashMap<>();
    private List<Bid> bidsTemp;

    public Map<UUID, List<Bid>> getBidsByItem() {
        return bidsByItem;
    }

    public List<Bid> getBidsTemp() {
        return bidsTemp;
    }
    
    public void storeBids(List<Bid> bids) {
        this.bidsTemp = bids;
    }
    
}