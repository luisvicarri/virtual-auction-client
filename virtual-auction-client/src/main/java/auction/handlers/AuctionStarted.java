package auction.handlers;

import auction.services.AuctionService;
import auction.services.interfaces.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuctionStarted implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuctionStarted.class);
    private final AuctionService service;

    public AuctionStarted(AuctionService service) {
        this.service = service;
    }
    
    @Override
    public void handle(String message) {
        System.out.println("");
        logger.info("Processing auction start message: {}", message);
        service.startAuction(message);
    }
    
}