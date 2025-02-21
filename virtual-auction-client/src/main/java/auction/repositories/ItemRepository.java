package auction.repositories;

import auction.models.Item;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemRepository {

    private static final Logger logger = LoggerFactory.getLogger(ItemRepository.class);
    private Optional<Item> currentItem = Optional.empty(); // Item atual do leilão

    public void setCurrentItem(Item item) {
        if (item == null) {
            logger.warn("Attempted to set a null item as the current item.");
            this.currentItem = Optional.empty();
            return;
        }
        this.currentItem = Optional.of(item);
        logger.info("Current item updated: {}", item);
    }

    public Optional<Item> getCurrentItem() {
        return currentItem;
    }

    public void clearCurrentItem() {
        this.currentItem = Optional.empty();
        logger.info("Current item has been cleared.");
    }
}