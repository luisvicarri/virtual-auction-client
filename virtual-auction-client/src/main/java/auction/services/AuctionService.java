package auction.services;

import auction.controllers.BiddingController;
import auction.main.ClientAuctionApp;
import auction.models.Bid;
import auction.models.Item;
import auction.models.dtos.Response;
import auction.utils.JsonUtil;
import auction.utils.UIUpdateManager;
import auction.views.frames.Frame;
import auction.views.panels.PnAuction;
import auction.views.panels.PnWaitingRoom;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
                logger.info("Message ignored:" + message);
                return;
            }

            Response response = mapper.readValue(message, Response.class);

            if ("AUCTION-STARTED".equals(response.getStatus())
                    || "AUCTION-INFO".equals(response.getStatus())) {

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
                Frame.pnAuction = new PnAuction(item);
                ClientAuctionApp.frame.initNewPanel(Frame.pnAuction);
            }
        } catch (JsonProcessingException | IllegalArgumentException ex) {
            logger.error("Error processing auction message.", ex);
        }
    }

    public void updateTime(String message) {
        try {
            Response response = mapper.readValue(message, Response.class);
            response.getData().ifPresent(data -> {
                Object timeObj = data.get("timeLeft");
                if (timeObj instanceof Number number) {
                    long timeLeftSeconds = number.longValue(); // Obtém o tempo em segundos

                    // Converte para Duration
                    Duration duration = Duration.ofSeconds(timeLeftSeconds);

                    // Formata para o padrão hh:mm:ss
                    String formattedDuration = String.format("%02dhrs : %02dmins : %02dsecs",
                            duration.toHours(),
                            duration.toMinutesPart(),
                            duration.toSecondsPart()
                    );

                    UIUpdateManager.getTimeUpdater().accept(formattedDuration);

                    if (duration.isZero()) {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                null,
                                "The auction has closed.",
                                "INFO",
                                JOptionPane.INFORMATION_MESSAGE)
                        );

                        Response auctionEnded = new Response("AUCTION-ENDED", "Auction closed");
                        ClientAuctionApp.frame.getAppController().getItemController().getCurrentItem().ifPresent(finalItem -> {
                            auctionEnded.addData("finalItem", finalItem);
                            ClientAuctionApp.frame.getAppController().getMulticastController().send(auctionEnded);
                        });

                    }

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

                UUID itemId = mapper.convertValue(data.get("itemId"), UUID.class);
                List<Map<String, Object>> bids = (List<Map<String, Object>>) data.get("bids");

                if (bids != null) {
                    ClientAuctionApp.frame.getAppController().getBiddingController().clearAllBids();
                    for (Map<String, Object> bidData : bids) {
                        UUID bidId = mapper.convertValue(bidData.get("id"), UUID.class);
                        UUID bidderId = mapper.convertValue(bidData.get("bidderId"), UUID.class);
                        double bidAmount = mapper.convertValue(bidData.get("amount"), Double.class);
                        double timestampSecs = mapper.convertValue(bidData.get("timestamp"), Double.class);
                        Instant timestamp = Instant.ofEpochSecond((long) timestampSecs);

                        Bid bid = new Bid(bidId, itemId, bidderId, bidAmount, timestamp);
                        ClientAuctionApp.frame.getAppController().getBiddingController().addBid(itemId, bid);
                    }

                    // Atualizar a interface com o último lance recebido
                    List<Bid> updatedBids = ClientAuctionApp.frame.getAppController().getBiddingController().getBidsByItemId(itemId);
                    UIUpdateManager.getBidListUpdater().accept(updatedBids);

                    Bid lastBid = updatedBids.get(updatedBids.size() - 1);
                    UIUpdateManager.getWinningBidderUpdater().accept("Winning Bidder: " + lastBid.getBidderName());
                    UIUpdateManager.getCurrentBidUpdater().accept("Current Bid: " + lastBid.getAmount());

                    // Atualizar o valor atual do item
                    ClientAuctionApp.frame.getAppController().getItemController().getCurrentItem().ifPresent(currentItem -> {
                        currentItem.setWinningBidder(Optional.of(lastBid.getBidderId()));
                        
                        double currentBid = currentItem.getCurrentBid();
                        double bidIncrement = currentItem.getData().getBidIncrement();
                        currentItem.setCurrentBid(currentBid + bidIncrement);
                        logger.info("Current bid updated to {}", currentItem.getCurrentBid());
                    });

                    // Notificar se foi o usuário que deu o lance
                    boolean isUserBid = lastBid.getBidderId().equals(ClientAuctionApp.frame.getAppController().getSessionController().getUserLogged().getId());
                    if (isUserBid) {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Your bid has been successfully registered.", "INFO", JOptionPane.INFORMATION_MESSAGE));
                        logger.debug("Your bid has been successfully registered.");
                    } else {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "User " + lastBid.getBidderName() + " placed a bid", "INFO", JOptionPane.INFORMATION_MESSAGE));
                        logger.debug("User: {} placed a bid.", lastBid.getBidderName());
                    }
                }
            });
        } catch (JsonProcessingException ex) {
            logger.error("Error parsing message {} to json", message, ex);
        }
    }

    public void auctionEnded(String message) {
        try {
            if (!message.trim().startsWith("{")) {
                logger.info("Message ignored:" + message);
                return;
            }

            Response response = mapper.readValue(message, Response.class);
            if (response.getMessage().equals("The bid did not reach the requested amount")
                    || response.getMessage().equals("Congratulations you made the winning bid")) {
                response.getData().ifPresent(data -> {
                    UUID bidderId = mapper.convertValue(data.get("bidderId"), UUID.class);
                    boolean isUserBid = bidderId.equals(ClientAuctionApp.frame.getAppController().getSessionController().getUserLogged().getId());
                    if (isUserBid) {

                        if (response.getMessage().equals("The bid did not reach the requested amount")) {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                    null,
                                    """
                                    Your bid did not reach the requested amount.
                                    Bid denied by the administrator.
                                    """,
                                    "WARN",
                                    JOptionPane.WARNING_MESSAGE)
                            );
                        } else {
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                                    null,
                                    "Congratulations, you made the highest bid and won the item.",
                                    "INFO",
                                    JOptionPane.INFORMATION_MESSAGE)
                            );
                        }

                    }
                });
            }

            Frame.pnWaitingRoom = new PnWaitingRoom();
            ClientAuctionApp.frame.initNewPanel(Frame.pnWaitingRoom);
        } catch (JsonProcessingException ex) {
            logger.error("Error processing auction message.", ex);
        }
    }
}