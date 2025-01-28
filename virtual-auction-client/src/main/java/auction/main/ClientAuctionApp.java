package auction.main;

import auction.controllers.ItemController;
import auction.controllers.SessionController;
import auction.controllers.UserController;
import auction.models.User;
import auction.proxies.UserServiceProxy;
import auction.repositories.ItemRepository;
import auction.repositories.UserRepository;
import auction.services.ItemService;
import auction.services.UserService;
import auction.utils.ConfigManager;
import auction.utils.MulticastUtil;
import auction.utils.PasswordUtil;
import auction.views.frames.Frame;

public class ClientAuctionApp {

    public static Frame frame;

    public static void main(String[] args) {
//        Seeding seed = new Seeding();
//        seed.start();
//        
//        String host = ConfigManager.get("host");
//        int port = Integer.valueOf(ConfigManager.get("port"));
//        
//        UserServiceProxy userProxy = new UserServiceProxy(host, port);
//        UserRepository userRepository = new UserRepository();
//        UserService userService = new UserService(userRepository, userProxy);
//        UserController userController = new UserController(userService);
//        
//        ItemRepository itemRepository = new ItemRepository();
//        ItemService itemService = new ItemService(itemRepository);
//        ItemController itemController = new ItemController(itemService);
//        
//        String name = "JohnDoe";
//        String plainPassword = "securePassword";
//        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
//        
//        User newUser = new User(name, plainPassword, hashedPassword);
//        SessionController.getInstance().logIn(newUser);
//        
//        ClientAuctionApp.frame = new Frame(userController, itemController);
//        ClientAuctionApp.frame.start();

        MulticastUtil multicastUtil = new MulticastUtil();

        // Conecta-se ao grupo multicast
        multicastUtil.connect();

        try {
            // Envia mensagem de registro ao servidor
            multicastUtil.send("CLIENT_CONNECTED");
            System.out.println("Cliente enviou mensagem de registro ao servidor.");

            // Aguarda mensagens do servidor continuamente
            for (int i = 1; i <= 5; i++) {
                String receivedMessage = multicastUtil.receiveString();
                System.out.println("Cliente recebeu: " + receivedMessage);
            }
        } finally {
            // Desconecta-se do grupo multicast
            multicastUtil.disconnect();
        }
    }

}
