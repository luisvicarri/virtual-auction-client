package auction.views.panels;

import auction.dispatchers.MessageDispatcher;
import auction.main.ClientAuctionApp;

public class PnWaitingRoom extends javax.swing.JPanel {

    public PnWaitingRoom() {
        initComponents();
        MessageDispatcher dispatcher = ClientAuctionApp.frame.getAppController().getMulticastController().getDispatcher();
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
