package auction.views.panels;

import auction.main.ClientAuctionApp;
import auction.models.Item;
import auction.models.dtos.Response;
import auction.utils.JsonUtil;
import auction.views.frames.Frame;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitingRoom extends javax.swing.JPanel {

    private final ObjectMapper mapper = JsonUtil.getObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WaitingRoom.class);

    public WaitingRoom() {
        initComponents();
        ClientAuctionApp.frame.getAppController().getMulticastController().startListening(this::processMessage);
    }

    private void processMessage(String message) {
        try {
            if (!message.trim().startsWith("{")) {
                // Ignorar mensagens que não sejam JSON
                logger.info("Mensagem ignorada: " + message);
                return;
            }

            Response response = mapper.readValue(message, Response.class);

            if ("AUCTION-STARTED".equals(response.getStatus())) {
                Map<String, Object> data = response.getData().orElseThrow();
                Object itemObject = data.get("item");

                // Converter manualmente o LinkedHashMap para Item
                Item item = mapper.convertValue(itemObject, Item.class);

                Frame.auction = new Auction(item);
                ClientAuctionApp.frame.initNewPanel(Frame.auction);
            }
        } catch (JsonProcessingException | IllegalArgumentException e) {
            logger.error("Erro ao processar mensagem do leilão.", e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbBackground = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbBackground.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbBackground.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbBackground.setText("WAIT");
        lbBackground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lbBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 600));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbBackground;
    // End of variables declaration//GEN-END:variables
}
