package auction.controllers;

import auction.proxies.UserServiceProxy;
import auction.repositories.BiddingRepository;
import auction.repositories.ItemRepository;
import auction.repositories.UserRepository;
import auction.services.BiddingService;
import auction.services.ItemService;
import auction.services.MulticastService;
import auction.services.UserService;

public final class AppController {
    
    private final UserController userController;
    private final ItemController itemController;
    private final SessionController sessionController;
    private final MulticastController multicastController;
    private final BiddingController biddingController;

    public AppController() {
        this.userController = configUserController();
        this.itemController = configItemController();
        this.multicastController = configMulticastController();
        this.sessionController = SessionController.getInstance();
        this.biddingController = configBiddingController();
    }
    
    private BiddingController configBiddingController() {
        BiddingRepository repository = new BiddingRepository();
        BiddingService service = new BiddingService(repository);
        return new BiddingController(service);
    }
    
    private MulticastController configMulticastController() {
        MulticastService service = new MulticastService();
        return new MulticastController(service);
    }
    
    private UserController configUserController() {
        UserRepository repository = new UserRepository();
        UserServiceProxy proxy = new UserServiceProxy();
        UserService service = new UserService(repository, proxy);
        return new UserController(service);
    }
    
    private ItemController configItemController() {
        ItemRepository repository = new ItemRepository();
        ItemService service = new ItemService(repository);
        return new ItemController(service);
    }

    public UserController getUserController() {
        return userController;
    }

    public ItemController getItemController() {
        return itemController;
    }

    public SessionController getSessionController() {
        return sessionController;
    }

    public MulticastController getMulticastController() {
        return multicastController;
    }

    public BiddingController getBiddingController() {
        return biddingController;
    }
    
}