package auction.services;

import auction.models.Item;
import auction.repositories.ItemRepository;

public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Define o item atual.
     */
    public void setCurrentItem(Item item) {
        repository.setCurrentItem(item);
    }

    /**
     * Obtém o item atual.
     */
    public Item getCurrentItem() {
        return repository.getCurrentItem();
    }

    /**
     * Limpa o item atual.
     */
    public void clearCurrentItem() {
        repository.clearCurrentItem();
    }
}
