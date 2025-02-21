package auction.views.panels;

import auction.main.ClientAuctionApp;
import auction.models.User;
import auction.utils.FontUtil;
import auction.utils.ValidatorUtil;
import auction.views.frames.Frame;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PnSignUp extends javax.swing.JPanel {

    private static final Logger logger = LoggerFactory.getLogger(PnSignUp.class);
    private final FontUtil fontUtil;
    private final ValidatorUtil validator;

    public PnSignUp() {
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
                lbConfirmPass, 14f,
                lbSignIn, 14f,
                lbSignUp, 14f,
                tfName, 12f,
                jpfPassword, 12f,
                jpfConfirmPass, 12f
        );

        fontUtil.applyFont(components, path);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lbConfirmPass = new javax.swing.JLabel();
        jpfConfirmPass = new javax.swing.JPasswordField();
        lbSignIn = new javax.swing.JLabel();
        lbSignUp = new javax.swing.JLabel();
        lbText = new javax.swing.JLabel();
        jpfPassword = new javax.swing.JPasswordField();
        lbPassword = new javax.swing.JLabel();
        lbBackground = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbName.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        lbName.setForeground(new java.awt.Color(0, 0, 0));
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbName.setText("Name");
        lbName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        add(lbName, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 178, 466, 30));

        tfName.setBackground(new java.awt.Color(255, 255, 255));
        tfName.setForeground(new java.awt.Color(0, 0, 0));
        tfName.setBorder(null);
        add(tfName, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 212, 466, 35));

        lbConfirmPass.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        lbConfirmPass.setForeground(new java.awt.Color(0, 0, 0));
        lbConfirmPass.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbConfirmPass.setText("Confirm Password");
        lbConfirmPass.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        add(lbConfirmPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 340, 466, 30));

        jpfConfirmPass.setBackground(new java.awt.Color(255, 255, 255));
        jpfConfirmPass.setForeground(new java.awt.Color(0, 0, 0));
        jpfConfirmPass.setBorder(null);
        add(jpfConfirmPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 374, 466, 35));

        lbSignIn.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        lbSignIn.setForeground(new java.awt.Color(0, 0, 0));
        lbSignIn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSignIn.setText("Already have an account? Sign In");
        lbSignIn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbSignIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbSignInMouseClicked(evt);
            }
        });
        add(lbSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 439, 310, 45));

        lbSignUp.setFont(new java.awt.Font("Leelawadee UI", 0, 18)); // NOI18N
        lbSignUp.setForeground(new java.awt.Color(255, 255, 255));
        lbSignUp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbSignUp.setText("Sign Up");
        lbSignUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbSignUpMouseClicked(evt);
            }
        });
        add(lbSignUp, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 439, 135, 45));

        lbText.setFont(new java.awt.Font("Leelawadee UI", 1, 24)); // NOI18N
        lbText.setForeground(new java.awt.Color(0, 0, 0));
        lbText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbText.setText("Sign Up");
        add(lbText, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 100, 466, 67));

        jpfPassword.setBackground(new java.awt.Color(255, 255, 255));
        jpfPassword.setForeground(new java.awt.Color(0, 0, 0));
        jpfPassword.setBorder(null);
        add(jpfPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 293, 466, 35));

        lbPassword.setFont(new java.awt.Font("Leelawadee UI", 0, 12)); // NOI18N
        lbPassword.setForeground(new java.awt.Color(0, 0, 0));
        lbPassword.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbPassword.setText("Password");
        lbPassword.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        add(lbPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(407, 259, 466, 30));

        lbBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/backgrounds/bgSignUp.png"))); // NOI18N
        add(lbBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 600));
    }// </editor-fold>//GEN-END:initComponents

    private void lbSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSignUpMouseClicked
        if (validator.areFieldsEmpty(tfName, jpfPassword)) { // Se existem campos nulos
            logger.warn("The form contains unfilled fields");
            JOptionPane.showMessageDialog(null, "The form contains unfilled fields", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else if (!validator.isValidPassword(new String(jpfPassword.getPassword()))) { // Se a senha é compatível com o formato requisitado
            logger.warn("Invalid password format");
            JOptionPane.showMessageDialog(null, "Invalid password format", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else if (!validator.passAreTheSame(new String(jpfPassword.getPassword()), new String(jpfConfirmPass.getPassword()))) {
            logger.warn("Passwords do not match");
            JOptionPane.showMessageDialog(null, "Passwords do not match", "WARNING", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logger.info("Ready to sign up");
            String name = tfName.getText();
            String plainPassword = new String(jpfPassword.getPassword());
            String hashedPassword = ClientAuctionApp.frame.getAppController().getUserController().hashPassword(plainPassword);
            User newUser = new User(name, plainPassword, hashedPassword);

            boolean signedUp = ClientAuctionApp.frame.getAppController().getUserController().signUp(newUser);
            if (signedUp) {
                logger.info("Signed up user"); // Requisitos cumpridos
                JOptionPane.showMessageDialog(null, "Signed up user", "INFO", JOptionPane.PLAIN_MESSAGE);
            } else {
                logger.warn("Failed to sign up user"); // Requisitos cumpridos
                JOptionPane.showMessageDialog(null, "Failed to sign up user", "WARN", JOptionPane.ERROR_MESSAGE);
            }
            ClientAuctionApp.frame.clearForm(tfName, jpfPassword, jpfConfirmPass);
            Frame.pnSignIn = new PnSignIn();
            ClientAuctionApp.frame.initNewPanel(Frame.pnSignIn);
        }
    }//GEN-LAST:event_lbSignUpMouseClicked

    private void lbSignInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbSignInMouseClicked
        Frame.pnSignIn = new PnSignIn();
        ClientAuctionApp.frame.initNewPanel(Frame.pnSignIn);
    }//GEN-LAST:event_lbSignInMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField jpfConfirmPass;
    private javax.swing.JPasswordField jpfPassword;
    private javax.swing.JLabel lbBackground;
    private javax.swing.JLabel lbConfirmPass;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbSignIn;
    private javax.swing.JLabel lbSignUp;
    private javax.swing.JLabel lbText;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}