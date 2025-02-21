package auction.services;

import auction.models.Item;
import auction.repositories.ItemRepository;
import java.util.Optional;

public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public void setCurrentItem(Item item) {
        repository.setCurrentItem(item);
    }

    public Optional<Item> getCurrentItem() {
        return repository.getCurrentItem();
    }

    public void clearCurrentItem() {
        repository.clearCurrentItem();
    }
}