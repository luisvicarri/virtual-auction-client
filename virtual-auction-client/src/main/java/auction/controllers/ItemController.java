package auction.controllers;

import auction.models.Item;
import auction.services.ItemService;
import java.util.Optional;

public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    /**
     * Define o item atual.
     */
    public void setCurrentItem(Item item) {
        service.setCurrentItem(item);
    }

    /**
     * Obtém o item atual.
     */
    public Optional<Item> getCurrentItem() {
        return service.getCurrentItem();
    }

    /**
     * Limpa o item atual.
     */
    public void clearCurrentItem() {
        service.clearCurrentItem();
    }
}