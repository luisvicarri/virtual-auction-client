package auction.views.panels;

import auction.controllers.SessionController;
import auction.main.ClientAuctionApp;
import auction.utils.FontUtil;
import auction.utils.ValidatorUtil;
import auction.views.frames.Frame;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignIn extends javax.swing.JPanel {

    private static final Logger logger = LoggerFactory.getLogger(SignIn.class);
    private final FontUtil fontUtil;
    private final ValidatorUtil validator;

    public SignIn() {
        initComponents();
        fontUtil = new FontUtil();
        validator = new ValidatorUtil();
        customizeComponents();
    }

    private void customizeComponents() {
        String path = "views/fonts/Questrial-Regular.ttf";

        Map<JComponent, Float> components = Map.of(
                lbText, 32f,
                lbName, 14f,
                lbPassword, 14f,
                lbSignIn, 14f,
                lbSignUp, 14f,
                tfName, 12f,
                jpfPassword, 12f
        );

        fontUtil.applyFont(components, path);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lbPassword = new javax.swing.JLabel();
        jpfPassword = new javax.swing.JPasswordField();
        lbSignUp = new javax.swing.JLabel();
        lbSignIn = new javax.swing.JLabel();
        lbText = new javax.swing.JLabel();
        lbBackground = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbName.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        lbName.setForeground(new java.awt.Color(0, 0, 0));
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbName.setText("Name");
        lbName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        add(lbName, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 222, 466, 30));

        tfName.setBackground(new java.awt.Color(255, 255, 255));
        tfName.setForeground(new java.awt.Color(0, 0, 0));
        tfName.setBorder(null);
        add(tfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 256, 466, 35));

        lbPassword.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        lbPassword.setForeground(new java.awt.Color(0, 0, 0));
        lbPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbPassword.setText("Password");
        lbPassword.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        add(lbPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 304, 466, 30));

        jpfPassword.setBackground(new java.awt.Color(255, 255, 255));
        jpfPassword.setForeground(new java.awt.Color(0, 0, 0));
        jpfPassword.setBorder(null);
        add(jpfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 338, 466, 35));

        lbSignUp.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        lbSignUp.setForeground(new java.awt.Color(0, 0, 0));
        lbSignUp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSignUp.setText(" Don't have an account? Sign Up");
        lbSignUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbSignUpMouseClicked(evt);
            }
        });
        add(lbSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 396, 310, 43));

        lbSignIn.setFont(new java.awt.Font("Leelawadee UI", 0, 18)); // NOI18N
        lbSignIn.setForeground(new java.awt.Color(255, 255, 255));
        lbSignIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSignIn.setText("Sign In");
        lbSignIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbSignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbSignInMouseClicked(evt);
            }
        });
        add(lbSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 396, 135, 43));

        lbText.setFont(new java.awt.Font("Leelawadee UI", 1, 24)); // NOI18N
        lbText.setForeground(new java.awt.Color(0, 0, 0));
        lbText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbText.setText("Sign In");
        add(lbText, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 140, 466, 67));

        lbBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/backgrounds/bgSignIn.png"))); // NOI18N
        add(lbBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 600));
    }// </editor-fold>//GEN-END:initComponents

    private void lbSignInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSignInMouseClicked
        if (validator.areFieldsEmpty(tfName, jpfPassword)) { // Se existem campos nulos
            logger.warn("The form contains unfilled fields");
            JOptionPane.showMessageDialog(null, "The form contains unfilled fields", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String name = tfName.getText();
            String password = new String(jpfPassword.getPassword());
            boolean signIn = ClientAuctionApp.frame.getAppController().getUserController().signIn(name, password, SessionController.getInstance());
            if (signIn) {
                logger.info("Logged in user"); // Requisitos cumpridos
                JOptionPane.showMessageDialog(null, "Logged in user", "INFO", JOptionPane.PLAIN_MESSAGE);
            } else {
                logger.warn("Failed to sign in user"); // Requisitos cumpridos
                JOptionPane.showMessageDialog(null, "Failed to sign in user", "WARN", JOptionPane.ERROR_MESSAGE);
            }

            ClientAuctionApp.frame.getAppController().getMulticastController().connect();
            ClientAuctionApp.frame.getAppController().getMulticastController().send("CLIENT_CONNECTED");
            System.out.println("Cliente enviou mensagem de registro ao servidor.");

            ClientAuctionApp.frame.clearForm(tfName, jpfPassword);
            Frame.auction = new Auction();
            ClientAuctionApp.frame.initNewPanel(Frame.auction);
        }
    }//GEN-LAST:event_lbSignInMouseClicked

    private void lbSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSignUpMouseClicked
        Frame.signUp = new SignUp();
        ClientAuctionApp.frame.initNewPanel(Frame.signUp);
    }//GEN-LAST:event_lbSignUpMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JLabel lbBackground;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbSignIn;
    private javax.swing.JLabel lbSignUp;
    private javax.swing.JLabel lbText;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
