/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.*;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;
import network.client.NetworkHandler;
import network.client.NetworkHandlerImpl;
import util.NameGenerator;
import view.music.MusicEngine;
import view.music.MusicFile;
import view.music.OnMusicEngine;

/**
 *
 * @author Patrick Zenhäusern
 */
public class MainJFrame extends javax.swing.JFrame {

    private TetrisController tetrisController;
    private MusicEngine musicEngine;
    private NetworkHandler networkHandler;
    private NameGenerator nameGenerator;

    /**
     * Creates new form frmMain
     */
    public MainJFrame(TetrisController tetrisController, NetworkHandler networkHandler) {
        this.tetrisController = tetrisController;

        this.networkHandler = networkHandler;

        try {
            nameGenerator = new NameGenerator("/util/syllables.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        initComponents();
        musicEngine = new OnMusicEngine(MusicFile.mainBackgroundMusic);
    }

    private void createGame(final boolean isSinglePlayer) throws HeadlessException {
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                switch ((TetrisController.UpdateType) o1) {
                    case CONNECTION_ESTABLISHED:
                        tetrisController.deleteObserver(this);                
                        showLobby(true,isSinglePlayer);
                        break;
                    case EXCEPTION_THROWN:
                        JOptionPane.showMessageDialog(MainJFrame.this, tetrisController.getThrownException(), "Exception", JOptionPane.ERROR_MESSAGE);
                        tetrisController.deleteObserver(this);
                        break;
                }
            }
        };
        tetrisController.addObserver(observer);
        try {
            tetrisController.startServer();
            tetrisController.connectToServer(tetrisController.getServerIP(), TetrisController.SERVER_PORT, txtNickname.getText());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Exception", JOptionPane.ERROR_MESSAGE);
            tetrisController.deleteObserver(observer);
        }
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
        btnStartGame = new javax.swing.JButton();
        pnlMultiPlayer = new javax.swing.JPanel();
        btnCreateGame = new javax.swing.JButton();
        btnJoinGame = new javax.swing.JButton();
        lblNickName = new javax.swing.JLabel();
        txtNickname = new javax.swing.JTextField();
        pnlControl = new javax.swing.JPanel();
        btnHelp = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        lblMultiPlayer = new javax.swing.JLabel();
        lblSinglePlayer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(417, 396));
        setMinimumSize(new java.awt.Dimension(417, 396));
        setName("frmMain");
        setPreferredSize(new java.awt.Dimension(417, 396));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel1.setText("<html><h1>~ZeLaMoSe - Tetris~</h1></html>");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(78, 19, 254, 49);

        pnlSinglePlayer.setOpaque(false);
        pnlSinglePlayer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnStartGame.setText("Start Game");
        btnStartGame.setMaximumSize(new java.awt.Dimension(95, 23));
        btnStartGame.setMinimumSize(new java.awt.Dimension(95, 23));
        btnStartGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSinglePlayerLayout = new javax.swing.GroupLayout(pnlSinglePlayer);
        pnlSinglePlayer.setLayout(pnlSinglePlayerLayout);
        pnlSinglePlayerLayout.setHorizontalGroup(
            pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSinglePlayerLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addComponent(btnStartGame, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );
        pnlSinglePlayerLayout.setVerticalGroup(
            pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSinglePlayerLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(btnStartGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        getContentPane().add(pnlSinglePlayer);
        pnlSinglePlayer.setBounds(37, 74, 338, 72);

        pnlMultiPlayer.setOpaque(false);
        pnlMultiPlayer.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnCreateGame.setText("Create Game");
        btnCreateGame.setPreferredSize(new java.awt.Dimension(87, 23));
        btnCreateGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateGameActionPerformed(evt);
            }
        });

        btnJoinGame.setText("Join Game");
        btnJoinGame.setMaximumSize(new java.awt.Dimension(95, 23));
        btnJoinGame.setMinimumSize(new java.awt.Dimension(95, 23));
        btnJoinGame.setPreferredSize(new java.awt.Dimension(87, 23));
        btnJoinGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinGameActionPerformed(evt);
            }
        });

        lblNickName.setText("Nickname:");

        txtNickname.setText(nameGenerator.compose(2));
        txtNickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNicknameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMultiPlayerLayout = new javax.swing.GroupLayout(pnlMultiPlayer);
        pnlMultiPlayer.setLayout(pnlMultiPlayerLayout);
        pnlMultiPlayerLayout.setHorizontalGroup(
            pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCreateGame, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(lblNickName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnJoinGame, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(txtNickname))
                .addGap(46, 46, 46))
        );
        pnlMultiPlayerLayout.setVerticalGroup(
            pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNickName)
                    .addComponent(txtNickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJoinGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        getContentPane().add(pnlMultiPlayer);
        pnlMultiPlayer.setBounds(37, 164, 338, 101);

        pnlControl.setOpaque(false);
        pnlControl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnHelp.setText("Help");

        btnExit.setText("Exit");

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHelp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
                .addComponent(btnExit)
                .addContainerGap())
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHelp)
                    .addComponent(btnExit))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(pnlControl);
        pnlControl.setBounds(37, 283, 338, 82);

        lblMultiPlayer.setBackground(new java.awt.Color(217, 19, 19));
        lblMultiPlayer.setText("<html><strong>MultiPlayer</strong></html>");
        getContentPane().add(lblMultiPlayer);
        lblMultiPlayer.setBounds(40, 150, 64, 14);

        lblSinglePlayer.setText("<html><strong>SinglePlayer</strong></html>");
        getContentPane().add(lblSinglePlayer);
        lblSinglePlayer.setBounds(40, 60, 70, 14);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/image/tetrisbg.jpeg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 420, 390);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartGameActionPerformed
        createGame(true);
    }//GEN-LAST:event_btnStartGameActionPerformed

    private void btnCreateGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateGameActionPerformed
        createGame(false);
    }//GEN-LAST:event_btnCreateGameActionPerformed

    private void btnJoinGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinGameActionPerformed
        final String ip = JOptionPane.showInputDialog(null, "Eingabe der IP");
        tetrisController.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                switch ((TetrisController.UpdateType) o1) {
                    case CONNECTION_ESTABLISHED:
                        tetrisController.deleteObserver(this);
                        showLobby(false, false);
                        break;
                    case EXCEPTION_THROWN:
                        JOptionPane.showMessageDialog(MainJFrame.this, tetrisController.getThrownException(), "Exception", JOptionPane.ERROR_MESSAGE);
                        tetrisController.deleteObserver(this);
                        break;
                }
            }
        });       
        tetrisController.connectToServer(ip, TetrisController.SERVER_PORT, txtNickname.getText());
    }//GEN-LAST:event_btnJoinGameActionPerformed

    private void txtNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNicknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNicknameActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details
         * see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
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
                InputSampler is = new InputSampler();
                NetworkHandler nh = new NetworkHandlerImpl();
                new MainJFrame(new TetrisController(new SimulationController(), nh, new StepGeneratorImpl(is)), nh).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateGame;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnJoinGame;
    private javax.swing.JButton btnStartGame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblMultiPlayer;
    private javax.swing.JLabel lblNickName;
    private javax.swing.JLabel lblSinglePlayer;
    private javax.swing.JPanel pnlControl;
    private javax.swing.JPanel pnlMultiPlayer;
    private javax.swing.JPanel pnlSinglePlayer;
    private javax.swing.JTextField txtNickname;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (!b) {
            musicEngine.stopMusic();
        } else {
            musicEngine.playMusic(true, 0.8f);
        }

    }

    private void showLobby(boolean host, final boolean isSinglePlayer) {
        final LobbyJFrame lobby = new LobbyJFrame(tetrisController, new ChatController(networkHandler), host, this, isSinglePlayer);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                lobby.setVisible(!isSinglePlayer);
                setVisible(false);
            }
        });
    }
}
