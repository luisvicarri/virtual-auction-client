package auction.controllers;

import auction.models.Item;
import auction.services.ItemService;
import java.util.Optional;

public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    public void setCurrentItem(Item item) {
        service.setCurrentItem(item);
    }

    public Optional<Item> getCurrentItem() {
        return service.getCurrentItem();
    }

    public void clearCurrentItem() {
        service.clearCurrentItem();
    }
}