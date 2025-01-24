package auction.views.frames;

import auction.views.panels.SignIn;
import auction.views.panels.SignUp;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Frame extends javax.swing.JFrame {

    public static SignIn signIn;
    public static SignUp signUp;
    
    public Frame() {
        initComponents();
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
