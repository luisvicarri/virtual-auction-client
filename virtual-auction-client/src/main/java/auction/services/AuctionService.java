package auction.services;

import auction.main.ClientAuctionApp;
import auction.models.Bid;
import auction.models.Item;
import auction.models.dtos.Response;
import auction.utils.JsonUtil;
import auction.views.frames.Frame;
import auction.views.panels.Auction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuctionService {

    private static final Logger logger = LoggerFactory.getLogger(AuctionService.class);
    private final ObjectMapper mapper = JsonUtil.getObjectMapper();

    public void startAuction(String message) {
        try {
            if (!message.trim().startsWith("{")) {
                logger.info("Mensagem ignorada: " + message);
                return;
            }

            Response response = mapper.readValue(message, Response.class);

            if ("AUCTION-STARTED".equals(response.getStatus())) {
                Map<String, Object> data = response.getData().orElseThrow();
                Object itemObject = data.get("item");
                // Converter manualmente o LinkedHashMap para Item
                Item item = mapper.convertValue(itemObject, Item.class);

                List<Bid> bids = (List<Bid>) data.get("bids");
                ClientAuctionApp.frame.getAppController().getBiddingController().storeBids(bids);

                // Criar o leilão e atualizar a interface gráfica
                Frame.auction = new Auction(item);
                ClientAuctionApp.frame.initNewPanel(Frame.auction);
            }
        } catch (JsonProcessingException | IllegalArgumentException e) {
            logger.error("Erro ao processar mensagem do leilão.", e);
        }
    }

    public void updateTime(String message, JLabel label) {
        try {
            Response response = mapper.readValue(message, Response.class);
            response.getData().ifPresent(data -> {
                Object timeObj = data.get("timeLeft");
                if (timeObj instanceof Number) {
                    long timeLeftSeconds = ((Number) timeObj).longValue(); // Obtém o tempo em segundos

                    // Converte para Duration
                    Duration duration = Duration.ofSeconds(timeLeftSeconds);

                    // Formata para o padrão hh:mm:ss
                    String formattedDuration = String.format("%02dhrs : %02dmins : %02dsecs",
                            duration.toHours(),
                            duration.toMinutesPart(),
                            duration.toSecondsPart()
                    );

                    SwingUtilities.invokeLater(() -> label.setText(formattedDuration));
                }
            });
        } catch (JsonProcessingException ex) {
            logger.error("Error desserializing json", ex);
        }
    }

    public void processBid(String message) {
        try {
            Response response = mapper.readValue(message, Response.class);

            response.getData().ifPresent(data -> {
                Object itemObj = data.get("itemId");
                UUID id = mapper.convertValue(itemObj, UUID.class);

                System.out.println("\tUUID do item recebido: " + id);
            });
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(AuctionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
