package auction.views.panels;

import auction.main.ClientAuctionApp;
import auction.utils.FontUtil;
import auction.views.frames.Frame;
import java.awt.Font;

public class SignIn extends javax.swing.JPanel {

    private FontUtil fontUtil;

    public SignIn() {
        initComponents();
        customizeComponents();
    }

    private void customizeComponents() {
        fontUtil = new FontUtil();
        String path = "views/fonts/Questrial-Regular.ttf";

        Font font = fontUtil.loadFont(path, 14f);
        fontUtil.applyFont(lbName, font);
        fontUtil.applyFont(lbPassword, font);
        fontUtil.applyFont(lbSignIn, font);
        fontUtil.applyFont(lbSignUp, font);

        font = fontUtil.loadFont(path, 12f);
        fontUtil.applyFont(tfName, font);
        fontUtil.applyFont(jpfPassword, font);

        font = fontUtil.loadFont(path, 32f);
        fontUtil.applyFont(lbText, font);
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
        lbSignIn.setForeground(new java.awt.Color(0, 0, 0));
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
