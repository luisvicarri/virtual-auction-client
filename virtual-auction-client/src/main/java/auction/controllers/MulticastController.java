package auction.controllers;

import auction.dispatchers.MessageDispatcher;
import auction.services.MulticastService;
import java.util.function.Consumer;

public class MulticastController {
    
    private final MulticastService service;
    private final MessageDispatcher dispatcher;

    public MulticastController(MulticastService service) {
        this.service = service;
        this.dispatcher = new MessageDispatcher();
    }

    public MessageDispatcher getDispatcher() {
        return dispatcher;
    }
    
    public void connect() {
        service.connect();
    }
    
    public void disconnect() {
        service.disconnect();
    }
    
    public void send(String msg) {
        service.send(msg);
    }
    
    public void send(Object obj) {
        service.send(obj);
    }
    
    public String receiveString() {
        return service.receiveString();
    }
    
    public <T> T receiveObject(Class<T> type) {
        return service.receiveObject(type);
    }
    
//    public void startListening(Consumer<String> onMessageReceived) {
//        service.startListening(onMessageReceived);
//    }
    
    public void startListening(Consumer<String> onMessageReceived) {
        service.startListening(onMessageReceived);
    }
}
