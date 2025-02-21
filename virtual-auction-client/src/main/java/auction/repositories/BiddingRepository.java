package auction.repositories;

import auction.models.Bid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiddingRepository {

    private static final Logger logger = LoggerFactory.getLogger(BiddingRepository.class);
    private final Map<UUID, List<Bid>> bidsByItem = new HashMap<>();

    public Map<UUID, List<Bid>> getBidsByItem() {
        return new HashMap<>(bidsByItem); // Retorna uma cópia para evitar alterações externas
    }

    public void addBid(UUID itemId, Bid bid) {
        if (itemId == null || bid == null) {
            logger.warn("Attempted to add a bid with null values. Item Id: {}, Bid Amount: {}", itemId, bid.getAmount());
            return;
        }
        bidsByItem.computeIfAbsent(itemId, k -> new ArrayList<>()).add(bid);
        logger.info("New bid added for item {}: {}", itemId, bid.getAmount());
    }

    public List<Bid> getBidsByItemId(UUID itemId) {
        return bidsByItem.getOrDefault(itemId, new ArrayList<>());
    }

    public void clearAllBids() {
        bidsByItem.clear();
        logger.info("All bids have been cleared.");
    }

    public void addBids(UUID itemId, List<Bid> bids) {
        if (itemId == null || bids == null || bids.isEmpty()) {
            logger.warn("Attempted to add bids with invalid parameters. ItemId: {}, Bids: {}", itemId, bids);
            return;
        }
        bidsByItem.computeIfAbsent(itemId, k -> new ArrayList<>()).addAll(bids);
        logger.info("Added {} bids for item {}", bids.size(), itemId);
    }
}