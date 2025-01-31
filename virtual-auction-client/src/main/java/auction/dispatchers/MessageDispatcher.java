package auction.dispatchers;

import auction.services.interfaces.MessageHandler;
import auction.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageDispatcher {

    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private final Map<String, MessageHandler> handlers = new HashMap<>();

    public MessageDispatcher() {
        // Thread que processa mensagens continuamente
        new Thread(() -> {
            while (true) {
                try {
                    String message = messageQueue.take(); // Bloqueia até receber uma mensagem
                    dispatch(message);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Dispatcher interrompido.");
                    break;
                }
            }
        }).start();
    }

    public void registerHandler(String type, MessageHandler handler) {
        handlers.put(type, handler);
    }

    public void addMessage(String message) {
        messageQueue.offer(message);
    }

    private void dispatch(String message) {
        // Aqui, você pode parsear o JSON e extrair o tipo da mensagem
        String messageType = extractMessageType(message); // Suponha que essa função obtenha "AUCTION-STARTED" ou "TIME-UPDATE"

        MessageHandler handler = handlers.get(messageType);
        if (handler != null) {
            handler.handle(message);
        } else {
            System.err.println("Nenhum handler encontrado para o tipo de mensagem: " + messageType);
        }
    }

    private String extractMessageType(String message) {
        // Aqui você pode usar uma biblioteca JSON (como Jackson ou Gson) para extrair o campo "status"
        try {
            JsonNode jsonNode = JsonUtil.getObjectMapper().readTree(message);
            return jsonNode.get("status").asText();
        } catch (JsonProcessingException e) {
            System.err.println("Erro ao extrair tipo da mensagem: " + message);
            return "";
        }
    }

}