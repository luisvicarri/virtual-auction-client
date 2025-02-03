package auction.handlers;

import auction.services.AuctionService;
import auction.services.interfaces.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuctionInfo implements MessageHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(AuctionInfo.class);
    private final AuctionService service;

    public AuctionInfo(AuctionService service) {
        this.service = service;
    }
    
    @Override
    public void handle(String message) {
        System.out.println("");
        logger.info("Processing auction info message: {}", message);
        service.startAuction(message);
    }
}