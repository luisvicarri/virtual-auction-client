package auction.views.panels;

import auction.dispatchers.MessageDispatcher;
import auction.handlers.AuctionStarted;
import auction.main.ClientAuctionApp;
import auction.services.AuctionService;
import auction.utils.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitingRoom extends javax.swing.JPanel {

    private final ObjectMapper mapper = JsonUtil.getObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(WaitingRoom.class);

    public WaitingRoom() {
        initComponents();
        MessageDispatcher dispatcher = ClientAuctionApp.frame.getAppController().getMulticastController().getDispatcher();
        dispatcher.registerHandler("AUCTION-STARTED", new AuctionStarted(new AuctionService()));
        ClientAuctionApp.frame.getAppController().getMulticastController().startListening(dispatcher::addMessage);
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
