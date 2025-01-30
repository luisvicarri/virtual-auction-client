package auction.views.frames;

import auction.controllers.AppController;
import auction.views.panels.Auction;
import auction.views.panels.SignIn;
import auction.views.panels.SignUp;
import auction.views.panels.WaitingRoom;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Frame extends javax.swing.JFrame {

    private final AppController appController;
    
    public static SignIn signIn;
    public static SignUp signUp;
    public static Auction auction;
    public static WaitingRoom waitingRoom;
    
    public Frame(AppController appController) {
        initComponents();
        this.appController = appController;
    }

    public AppController getAppController() {
        return appController;
    }
    
    public void start() {
        this.setLayout(new BorderLayout());

        signIn = new SignIn();
        initNewPanel(signIn);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void initNewPanel(JPanel newJPanel) {
        this.getContentPane().removeAll();
        this.add(newJPanel, BorderLayout.CENTER);
        this.pack();
    }
    
    public void clearForm(JComponent... components) {
        for (JComponent component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            } else if (component instanceof JPasswordField) {
                ((JPasswordField) component).setText("");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
