package auction.handlers;

import auction.services.AuctionService;
import auction.services.interfaces.MessageHandler;
import javax.swing.JLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeUpdate implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(TimeUpdate.class);
    private final AuctionService service;
    private final JLabel label;

    public TimeUpdate(AuctionService service, JLabel label) {
        this.service = service;
        this.label = label;
    }

    @Override
    public void handle(String message) {
        logger.info("Processing auction start message: {}", message);
        service.updateTime(message, label);
    }

}
