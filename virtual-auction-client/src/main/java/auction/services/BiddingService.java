package auction.services;

import auction.controllers.SessionController;
import auction.controllers.UserController;
import auction.main.ClientAuctionApp;
import auction.models.Bid;
import auction.models.Item;
import auction.models.User;
import auction.models.dtos.Response;
import auction.repositories.BiddingRepository;
import auction.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiddingService {

    private static final Logger logger = LoggerFactory.getLogger(BiddingService.class);
    private final BiddingRepository repository;
    private final ObjectMapper mapper = JsonUtil.getObjectMapper();

    public BiddingService(BiddingRepository repository) {
        this.repository = repository;
    }

    public Map<UUID, List<Bid>> getBidsByItem() {
        return repository.getBidsByItem();
    }

    public void addBid(UUID itemId, Bid bid) {
        if (itemId == null || bid == null) {
            logger.warn("Attempted to add a bid with null values. ItemId: {}, Bid: {}", itemId, bid);
            return;
        }
        repository.addBid(itemId, bid);
    }

    public List<Bid> getBidsByItemId(UUID itemId) {
        return repository.getBidsByItemId(itemId);
    }

    public void clearAllBids() {
        repository.clearAllBids();
    }
    
    public void addBids(UUID itemId, List<Bid> bids) {
        repository.addBids(itemId, bids);
    }

    public void placeBid(Item currentItem) {
        UUID itemId = currentItem.getId();

        UserController controller = ClientAuctionApp.frame.getAppController().getUserController();
        User userLogged = controller.getUserLogged(SessionController.getInstance());
        UUID bidderId = userLogged.getId();

        Double amount = currentItem.getCurrentBid() + currentItem.getData().getBidIncrement();
        Bid bidToPlace = new Bid(itemId, bidderId, amount);

        // Criando um Response para encapsular o lance
        Map<String, Object> data = new HashMap<>();
        data.put("bid", bidToPlace);
        data.put("itemId", itemId);

        Response response = new Response("NEW-BID", "User placed a new bid", data);

        // Enviando via MulticastController
        ClientAuctionApp.frame.getAppController().getMulticastController().send(response);
    }
}