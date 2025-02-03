package auction.controllers;

import auction.dispatchers.MessageDispatcher;
import auction.handlers.AuctionInfo;
import auction.handlers.AuctionStarted;
import auction.handlers.PlaceBid;
import auction.handlers.TimeUpdate;
import auction.proxies.UserServiceProxy;
import auction.repositories.BiddingRepository;
import auction.repositories.ItemRepository;
import auction.repositories.UserRepository;
import auction.services.AuctionService;
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

        addHandlers();
    }

    private void addHandlers() {
        MessageDispatcher dispatcher = getMulticastController().getDispatcher();
        dispatcher.registerHandler("BID-UPDATED", new PlaceBid(new AuctionService()));
        dispatcher.registerHandler("AUCTION-STARTED", new AuctionStarted(new AuctionService()));
        dispatcher.registerHandler("TIME-UPDATE", new TimeUpdate(new AuctionService()));
        dispatcher.registerHandler("AUCTION-INFO", new AuctionInfo(new AuctionService()));
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
