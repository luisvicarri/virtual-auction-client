package auction.services;

import auction.controllers.BiddingController;
import auction.main.ClientAuctionApp;
import auction.models.Bid;
import auction.models.Item;
import auction.models.dtos.Response;
import auction.utils.JsonUtil;
import auction.utils.UIUpdateManager;
import auction.views.frames.Frame;
import auction.views.panels.Auction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
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
                ClientAuctionApp.frame.getAppController().getItemController().setCurrentItem(item);

                // Obtendo a lista bruta de lances (que é interpretada como uma lista de LinkedHashMap)
                List<?> rawBids = (List<?>) data.get("bids");

                // Convertendo a lista para uma lista de objetos Bid
                List<Bid> bids = rawBids.stream()
                        .map(bid -> mapper.convertValue(bid, Bid.class))
                        .collect(Collectors.toList());
                UUID itemId = item.getId();

                // Armazenar todos os lances de uma vez
                BiddingController biddingController = ClientAuctionApp.frame.getAppController().getBiddingController();
                biddingController.addBids(itemId, bids);

                // Criar o leilão e atualizar a interface gráfica
                Frame.auction = new Auction(item);
                ClientAuctionApp.frame.initNewPanel(Frame.auction);
            }
        } catch (JsonProcessingException | IllegalArgumentException e) {
            logger.error("Erro ao processar mensagem do leilão.", e);
        }
    }

    public void updateTime(String message) {
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

//                    SwingUtilities.invokeLater(() -> label.setText(formattedDuration));
                    UIUpdateManager.getTimeUpdater().accept(formattedDuration);
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
                Map<String, Object> bidData = (Map<String, Object>) data.get("bid");
                if (bidData != null) {
                    Object bidIdObj = bidData.get("id");
                    Object itemIdObj = data.get("itemId");
                    Object bidderIdObj = bidData.get("bidderId");
                    Object bidderNameObj = bidData.get("bidderName");
                    Object bidAmountObj = bidData.get("amount");
                    Object timestampObj = bidData.get("timestamp");

                    if (bidIdObj != null && itemIdObj != null && bidderIdObj != null && bidderNameObj != null && bidAmountObj != null && timestampObj != null) {
                        UUID bidId = mapper.convertValue(bidIdObj, UUID.class);
                        UUID itemId = mapper.convertValue(itemIdObj, UUID.class);
                        UUID bidderId = mapper.convertValue(bidderIdObj, UUID.class);
                        String bidderName = mapper.convertValue(bidderNameObj, String.class);
                        double bidAmount = mapper.convertValue(bidAmountObj, Double.class);
                        double timestampSecs = mapper.convertValue(timestampObj, Double.class);
                        Instant timestamp = Instant.ofEpochSecond((long) timestampSecs);

                        Bid bid = new Bid(bidId, itemId, bidderId, bidAmount, timestamp);
                        ClientAuctionApp.frame.getAppController().getBiddingController().addBid(itemId, bid);

                        List<Bid> updatedBids = ClientAuctionApp.frame.getAppController().getBiddingController().getBidsByItemId(itemId);
                        UIUpdateManager.getBidListUpdater().accept(updatedBids);
                        
                        // Atualiza labels conforme o lance recebido
                        UIUpdateManager.getWinningBidderUpdater().accept("Winning Bidder: " + bidderName);
                        UIUpdateManager.getCurrentBidUpdater().accept("Current Bid: " + bidAmount);
                        double currentBid = ClientAuctionApp.frame.getAppController().getItemController().getCurrentItem().getCurrentBid();
                        double bidIncrement = ClientAuctionApp.frame.getAppController().getItemController().getCurrentItem().getData().getBidIncrement();
                        ClientAuctionApp.frame.getAppController().getItemController().getCurrentItem().setCurrentBid(currentBid + bidIncrement);
                        logger.info("Current bid updated to {}", ClientAuctionApp.frame.getAppController().getItemController().getCurrentItem().getCurrentBid());
                        
                        boolean isUserBid = bidderId.equals(ClientAuctionApp.frame.getAppController().getSessionController().getUserLogged().getId());
                        if (isUserBid) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Your bid has been successfully registered.", "INFO", JOptionPane.INFORMATION_MESSAGE));
                            logger.debug("Your bid has been successfully registered.");
                        } else {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "User " + bidderName + " placed a bid", "INFO", JOptionPane.INFORMATION_MESSAGE));
                            logger.debug("User: {} placed a bid.", bidderName);
                        }

                    }

                }
            });
        } catch (JsonProcessingException ex) {
            logger.error("Error parsing message {} to json", message, ex);
        }
    }

}
