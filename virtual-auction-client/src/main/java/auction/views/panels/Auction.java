package auction.views.panels;

import auction.controllers.SessionController;
import auction.controllers.UserController;
import auction.dispatchers.MessageDispatcher;
import auction.handlers.TimeUpdate;
import auction.main.ClientAuctionApp;
import auction.models.Item;
import auction.models.User;
import auction.services.AuctionService;
import auction.utils.FontUtil;
import auction.utils.ImageUtil;
import auction.views.components.ScrollBarCustom;
import auction.views.panels.templates.Message;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Auction extends javax.swing.JPanel {

    private final ImageUtil imageUtil;
    private final FontUtil fontUtil;

    public Auction(Item currentItem) {
        initComponents();
        imageUtil = new ImageUtil();
        fontUtil = new FontUtil();
        customizeComponents();
        loadAuctionContent(currentItem);
        
        MessageDispatcher dispatcher = ClientAuctionApp.frame.getAppController().getMulticastController().getDispatcher();
        dispatcher.registerHandler("TIME-UPDATE", new TimeUpdate(new AuctionService(), lbTimer));
        ClientAuctionApp.frame.getAppController().getMulticastController().startListening(dispatcher::addMessage);

        pnMessageDisplay.setLayout(null);
        spMessageDisplay.setVerticalScrollBar(new ScrollBarCustom());
        List<String> messages = loadContent();
        addMessage(messages);

        UserController controller = ClientAuctionApp.frame.getAppController().getUserController();
        User userLogged = controller.getUserLogged(SessionController.getInstance());
        lbUsername.setText(userLogged.getName());
    }

    private void customizeComponents() {
        String path = "views/fonts/Questrial-Regular.ttf";

        Map<JComponent, Float> components = Map.ofEntries(
                Map.entry(lbUsername, 18f),
                Map.entry(lbAuction, 24f),
                Map.entry(lbBidNow, 18f),
                Map.entry(lbWinningBidder, 18f),
                Map.entry(lbBidIncrement, 14f),
                Map.entry(lbCurrentBid, 14f),
                Map.entry(lbOpenningBid, 14f),
                Map.entry(lbReservePrice, 14f),
                Map.entry(lbTimer, 14f),
                Map.entry(lbTitle, 18f),
                Map.entry(tpDescription, 12f)
        );

        fontUtil.applyFont(components, path);

        ImageIcon originalIcon = imageUtil.createImageIcon("/views/icons/icUserImage.png");
        ImageIcon resizedIcon = imageUtil.resizeIcon(originalIcon, 35, 35);
        lbUser.setIcon(resizedIcon);

        originalIcon = imageUtil.createImageIcon("/views/icons/icGenie.png");
        resizedIcon = imageUtil.resizeIcon(originalIcon, 24, 24);
        lbTitle.setIcon(resizedIcon);
    }

    private void loadAuctionContent(Item currentItem) {
        lbAuction.setText(currentItem.getData().getTitle());
        lbBidIncrement.setText("Bid Increment: " + String.valueOf(currentItem.getData().getBidIncrement()));
        lbCurrentBid.setText("Current Bid: " + String.valueOf(currentItem.getCurrentBid()));
        lbOpenningBid.setText("Openning Bid: " + String.valueOf(currentItem.getOpeningBid()));
        lbReservePrice.setText("Reserve Price: " + String.valueOf(currentItem.getData().getReservePrice()));

        tpDescription.setText(currentItem.getData().getDescription());
        StyledDocument doc = tpDescription.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), style, false);

        ImageIcon originalIcon = imageUtil.createImageIcon(currentItem.getData().getItemImage());
        ImageIcon resizedIcon = imageUtil.resizeIcon(originalIcon, 330, 395);
        lbPhoto.setIcon(resizedIcon);

        Duration duration = currentItem.getData().getAuctionDuration();

        long hours = duration.toHours();         // Obtém as horas completas
        long minutes = duration.toMinutesPart(); // Obtém os minutos restantes (após remover as horas)
        long seconds = duration.toSecondsPart(); // Obtém os segundos restantes (após remover minutos e horas)

        String formattedDuration = String.format("%02dhrs : %02dmins : %02dsecs", hours, minutes, seconds);
        lbTimer.setText(formattedDuration);
        originalIcon = imageUtil.createImageIcon("/views/icons/icTimer.png");
        resizedIcon = imageUtil.resizeIcon(originalIcon, 18, 18);
        lbTimer.setIcon(resizedIcon);
    }

    private List<String> loadContent() {
        List<String> messages = new ArrayList<>();

        messages.add("User0001 has the highest bid.");
        messages.add("User0009 has the highest bid.");
        messages.add("User0027 has the highest bid.");
        messages.add("User0012 has the highest bid.");
        messages.add("User0003 has the highest bid.");
        messages.add("User0033 has the highest bid.");
        messages.add("User0006 has the highest bid.");
        messages.add("User0090 has the highest bid.");
        messages.add("User0001 has the highest bid.");
        messages.add("User0013 has the highest bid.");
        messages.add("User0006 has the highest bid.");

        return messages;
    }

    private void addMessage(List<String> messages) {
        int rowSpacing = 20; // Espaçamento entre linhas
        int templateWidth = 291; // Largura do template
        int templateHeight = 60; // Altura do template

        // Ajusta o tamanho do painel "pnMessageDisplay"
        int panelWidth = templateWidth; // Apenas uma coluna, então a largura é fixa
        int panelHeight = messages.size() * (templateHeight + rowSpacing) - rowSpacing;
        pnMessageDisplay.setPreferredSize(new Dimension(panelWidth, panelHeight));

        for (int i = 0; i < messages.size(); i++) {
            String msg = messages.get(i);

            // Cria uma nova instância do TemplatePanel
            Message message = new Message();

            // Preenche os dados no template
            message.getLbMessage().setText(msg);

            // Calcula a posição (apenas na vertical)
            int y = i * (templateHeight + rowSpacing);

            // Define a posição e o tamanho do template
            message.setBounds(0, y, templateWidth, templateHeight);

            // Adiciona o template ao painel "pnMessageDisplay"
            pnMessageDisplay.add(message);
        }

        // Atualiza o painel
        pnMessageDisplay.revalidate();
        pnMessageDisplay.repaint();

        // Posiciona o JScrollPane na parte inferior
        SwingUtilities.invokeLater(() -> {
            Rectangle lastElementRect = new Rectangle(0, panelHeight - 1, panelWidth, 1);
            pnMessageDisplay.scrollRectToVisible(lastElementRect);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbUser = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        lbPhoto = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        tpDescription = new javax.swing.JTextPane();
        lbOpenningBid = new javax.swing.JLabel();
        lbReservePrice = new javax.swing.JLabel();
        lbCurrentBid = new javax.swing.JLabel();
        lbBidIncrement = new javax.swing.JLabel();
        lbTimer = new javax.swing.JLabel();
        lbBidNow = new javax.swing.JLabel();
        lbWinningBidder = new javax.swing.JLabel();
        lbAuction = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        spMessageDisplay = new javax.swing.JScrollPane();
        pnMessageDisplay = new javax.swing.JPanel();
        lbBackground = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbUser.setForeground(new java.awt.Color(255, 255, 255));
        lbUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lbUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1181, 15, 30, 30));

        lbName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbName.setForeground(new java.awt.Color(255, 255, 255));
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbName.setText("Auction");
        lbName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lbName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 120, 30));

        lbPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lbPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 144, 330, 395));

        lbTitle.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle.setText("Auction");
        add(lbTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(903, 162, 299, 38));

        tpDescription.setEditable(false);
        tpDescription.setBorder(null);
        tpDescription.setOpaque(false);
        add(tpDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 212, 378, 70));
        add(lbOpenningBid, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 323, 189, 29));

        lbReservePrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        add(lbReservePrice, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 323, 189, 29));
        add(lbCurrentBid, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 362, 189, 29));

        lbBidIncrement.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        add(lbBidIncrement, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 362, 189, 29));

        lbTimer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lbTimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 413, 189, 47));

        lbBidNow.setBackground(new java.awt.Color(45, 78, 164));
        lbBidNow.setForeground(new java.awt.Color(255, 255, 255));
        lbBidNow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbBidNow.setText("BID NOW");
        lbBidNow.setOpaque(true);
        add(lbBidNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(658, 413, 170, 47));
        add(lbWinningBidder, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 502, 378, 38));

        lbAuction.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(lbAuction, new org.netbeans.lib.awtextra.AbsoluteConstraints(451, 144, 378, 26));

        lbUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbUsername.setForeground(new java.awt.Color(255, 255, 255));
        lbUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUsername.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        add(lbUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(1061, 15, 120, 30));

        spMessageDisplay.setBackground(new java.awt.Color(232, 232, 232));
        spMessageDisplay.setBorder(null);
        spMessageDisplay.setForeground(new java.awt.Color(232, 232, 232));

        pnMessageDisplay.setBackground(new java.awt.Color(232, 232, 232));
        pnMessageDisplay.setForeground(new java.awt.Color(232, 232, 232));

        javax.swing.GroupLayout pnMessageDisplayLayout = new javax.swing.GroupLayout(pnMessageDisplay);
        pnMessageDisplay.setLayout(pnMessageDisplayLayout);
        pnMessageDisplayLayout.setHorizontalGroup(
            pnMessageDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        pnMessageDisplayLayout.setVerticalGroup(
            pnMessageDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );

        spMessageDisplay.setViewportView(pnMessageDisplay);

        add(spMessageDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(903, 200, 299, 323));

        lbBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/backgrounds/bgAuction.png"))); // NOI18N
        add(lbBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 600));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbAuction;
    private javax.swing.JLabel lbBackground;
    private javax.swing.JLabel lbBidIncrement;
    private javax.swing.JLabel lbBidNow;
    private javax.swing.JLabel lbCurrentBid;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbOpenningBid;
    private javax.swing.JLabel lbPhoto;
    private javax.swing.JLabel lbReservePrice;
    private javax.swing.JLabel lbTimer;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JLabel lbWinningBidder;
    private javax.swing.JPanel pnMessageDisplay;
    private javax.swing.JScrollPane spMessageDisplay;
    private javax.swing.JTextPane tpDescription;
    // End of variables declaration//GEN-END:variables
}
