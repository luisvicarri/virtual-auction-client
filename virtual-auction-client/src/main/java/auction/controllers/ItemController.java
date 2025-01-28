package auction.controllers;

import auction.services.ItemService;

public class ItemController {
    
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }
    
}
