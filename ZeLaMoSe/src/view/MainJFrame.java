/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Patrick Zenhäusern
 */
public class MainJFrame extends javax.swing.JFrame {

  /**
   * Creates new form frmMain
   */
  public MainJFrame() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code.
   * The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pnlSinglePlayer = new javax.swing.JPanel();
        lblSinglePlayer = new javax.swing.JLabel();
        lblStartGame = new javax.swing.JLabel();
        pnlMultiPlayer = new javax.swing.JPanel();
        lblMultiPlayer = new javax.swing.JLabel();
        lblCreateGame = new javax.swing.JLabel();
        lblJoinGame = new javax.swing.JLabel();
        pnlControl = new javax.swing.JPanel();
        lblHelp = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("<html><h1>~ZeLaMoSe - Tetris~</h1></html>");

        pnlSinglePlayer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblSinglePlayer.setText("<html><strong>SinglePlayer</strong></html>");

        lblStartGame.setText("Start Game");

        javax.swing.GroupLayout pnlSinglePlayerLayout = new javax.swing.GroupLayout(pnlSinglePlayer);
        pnlSinglePlayer.setLayout(pnlSinglePlayerLayout);
        pnlSinglePlayerLayout.setHorizontalGroup(
            pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSinglePlayerLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStartGame)
                    .addComponent(lblSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        pnlSinglePlayerLayout.setVerticalGroup(
            pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSinglePlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblStartGame)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMultiPlayer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblMultiPlayer.setText("<html><strong>MultiPlayer</strong></html>");

        lblCreateGame.setText("Create Game");

        lblJoinGame.setText("Join Game");

        javax.swing.GroupLayout pnlMultiPlayerLayout = new javax.swing.GroupLayout(pnlMultiPlayer);
        pnlMultiPlayer.setLayout(pnlMultiPlayerLayout);
        pnlMultiPlayerLayout.setHorizontalGroup(
            pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addGroup(pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMultiPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblJoinGame)
                    .addComponent(lblCreateGame))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMultiPlayerLayout.setVerticalGroup(
            pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMultiPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCreateGame)
                .addGap(18, 18, 18)
                .addComponent(lblJoinGame)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlControl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblHelp.setText("Help");

        lblExit.setText("Exit");

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblHelp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblExit)
                .addGap(20, 20, 20))
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHelp)
                    .addComponent(lblExit))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnlMultiPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlControl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSinglePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlMultiPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    /*
     * Set the Nimbus look and feel
     */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
     * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
     * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /*
     * Create and display the form
     */
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        new MainJFrame().setVisible(true);
      }
    });
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCreateGame;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JLabel lblJoinGame;
    private javax.swing.JLabel lblMultiPlayer;
    private javax.swing.JLabel lblSinglePlayer;
    private javax.swing.JLabel lblStartGame;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JPanel pnlMultiPlayer;
    private javax.swing.JPanel pnlSinglePlayer;
    // End of variables declaration//GEN-END:variables
}