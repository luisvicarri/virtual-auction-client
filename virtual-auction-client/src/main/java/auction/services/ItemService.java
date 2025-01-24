package auction.services;

import auction.repositories.ItemRepository;

public class ItemService {
    
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }
    
}