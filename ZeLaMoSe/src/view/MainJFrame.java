package view;

import domain.*;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import network.GameParams;
import network.client.NetworkHandlerAbstract;
import util.NameGenerator;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class MainJFrame extends javax.swing.JFrame {

    private TetrisController tetrisController;
    private ChatController chatController;
    private NameGenerator nameGenerator;

    public ReplayData readReplayData(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fis);
        ReplayData replayData = (ReplayData) in.readObject();
        in.close();
        return replayData;
    }

    private void setButtonsEnabled(boolean enabled) {
        btnCreateGame.setEnabled(enabled);
        btnExit.setEnabled(enabled);
        btnHelp.setEnabled(enabled);
        btnJoinGame.setEnabled(enabled);
        btnStartGame.setEnabled(enabled);
        btnWatchReplay.setEnabled(enabled);
    }

    public enum GameMode {

        SINGLE_PLAYER, MULTI_PLAYER_HOST, MULTI_PLAYER_JOIN
    }

    /**
     * Creates new form frmMain
     */
    public MainJFrame(TetrisController tetrisController, domain.ChatController chatController) {
        this.tetrisController = tetrisController;
        this.chatController = chatController;
        try {
            nameGenerator = new NameGenerator("/resource/util/syllables.txt");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        initComponents();
        setupErrorDialog();
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
        btnWatchReplay = new javax.swing.JButton();
        pnlMultiPlayer = new javax.swing.JPanel();
        btnCreateGame = new javax.swing.JButton();
        btnJoinGame = new javax.swing.JButton();
        pnlControl = new javax.swing.JPanel();
        btnHelp = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        lblNickName = new javax.swing.JLabel();
        txtNickname = new javax.swing.JTextField();
        lblMultiPlayer = new javax.swing.JLabel();
        lblSinglePlayer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(417, 396));
        setMinimumSize(new java.awt.Dimension(417, 396));
        setName("frmMain"); // NOI18N
        setPreferredSize(new java.awt.Dimension(417, 396));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel1.setText("<html><h1>~ZeLaMoSe - Tetris~</h1></html>");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(70, 0, 265, 50);

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

        btnWatchReplay.setText("Watch Replay");
        btnWatchReplay.setMaximumSize(new java.awt.Dimension(95, 23));
        btnWatchReplay.setMinimumSize(new java.awt.Dimension(95, 23));
        btnWatchReplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWatchReplayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSinglePlayerLayout = new javax.swing.GroupLayout(pnlSinglePlayer);
        pnlSinglePlayer.setLayout(pnlSinglePlayerLayout);
        pnlSinglePlayerLayout.setHorizontalGroup(
            pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSinglePlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStartGame, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnWatchReplay, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlSinglePlayerLayout.setVerticalGroup(
            pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSinglePlayerLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlSinglePlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStartGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWatchReplay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(pnlSinglePlayer);
        pnlSinglePlayer.setBounds(40, 100, 330, 78);

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
        btnJoinGame.setPreferredSize(new java.awt.Dimension(95, 23));
        btnJoinGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinGameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMultiPlayerLayout = new javax.swing.GroupLayout(pnlMultiPlayer);
        pnlMultiPlayer.setLayout(pnlMultiPlayerLayout);
        pnlMultiPlayerLayout.setHorizontalGroup(
            pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCreateGame, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(btnJoinGame, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlMultiPlayerLayout.setVerticalGroup(
            pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMultiPlayerLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlMultiPlayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateGame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnJoinGame, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        getContentPane().add(pnlMultiPlayer);
        pnlMultiPlayer.setBounds(37, 195, 338, 70);

        pnlControl.setOpaque(false);
        pnlControl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnHelp.setText("Help");
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblNickName.setText("<html><strong>Nickname:</strong></html>");

        txtNickname.setText(nameGenerator.compose(2));
        txtNickname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNicknameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlControlLayout = new javax.swing.GroupLayout(pnlControl);
        pnlControl.setLayout(pnlControlLayout);
        pnlControlLayout.setHorizontalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHelp)
                .addGap(41, 41, 41)
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlControlLayout.createSequentialGroup()
                        .addComponent(txtNickname, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addComponent(btnExit))
                    .addGroup(pnlControlLayout.createSequentialGroup()
                        .addComponent(lblNickName, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlControlLayout.setVerticalGroup(
            pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlControlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNickName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHelp)
                    .addComponent(btnExit))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(pnlControl);
        pnlControl.setBounds(37, 283, 338, 86);

        lblMultiPlayer.setBackground(new java.awt.Color(217, 19, 19));
        lblMultiPlayer.setText("<html><strong>MultiPlayer</strong></html>");
        getContentPane().add(lblMultiPlayer);
        lblMultiPlayer.setBounds(40, 180, 76, 16);

        lblSinglePlayer.setText("<html><strong>SinglePlayer</strong></html>");
        getContentPane().add(lblSinglePlayer);
        lblSinglePlayer.setBounds(40, 80, 83, 16);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resource/image/tetrisbg.jpeg")));
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 420, 390);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createGame() {
        try {
            tetrisController.startServer();
        } catch (Exception ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void btnStartGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartGameActionPerformed
        setButtonsEnabled(false);
        createGame();
        setupConnectionHandler(GameMode.SINGLE_PLAYER);
    }//GEN-LAST:event_btnStartGameActionPerformed

    private void btnCreateGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateGameActionPerformed
        setButtonsEnabled(false);
        createGame();
        setupConnectionHandler(GameMode.MULTI_PLAYER_HOST);
    }//GEN-LAST:event_btnCreateGameActionPerformed

    private void btnJoinGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinGameActionPerformed
        setButtonsEnabled(false);
        setupConnectionHandler(GameMode.MULTI_PLAYER_JOIN);
    }

    private void setupErrorDialog() {
        tetrisController.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                switch ((TetrisController.UpdateType) o1) {
                    case EXCEPTION_THROWN:
                        showExceptionDialog(tetrisController.getThrownException());
                        break;
                }
            }
        });
    }

    private void showExceptionDialog(Exception exception) throws HeadlessException {
        JOptionPane.showMessageDialog(MainJFrame.this, exception.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
    }

    private void setupConnectionHandler(final GameMode gameMode) {
        final JoinGameJDialog joinDialog = new JoinGameJDialog(this, true, gameMode, tetrisController, txtNickname.getText());
        joinDialog.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                switch (joinDialog.getReturnValue()) {
                    case CONNECTED:
                        showLobby(gameMode);
                        break;
                    case EXCEPTION_THROWN:
                        setButtonsEnabled(true);
                        showExceptionDialog(joinDialog.getThrownException());
                        break;
                    case CANCELLED:
                        setButtonsEnabled(true);
                        break;
                }
            }
        });
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                joinDialog.setVisible(true);
            }
        });
    }//GEN-LAST:event_btnJoinGameActionPerformed

    private void txtNicknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNicknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNicknameActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            File tf;
            try {
                tf = File.createTempFile("manual", ".pdf");
                tf.deleteOnExit();
                byte buffer[] = new byte[0x1000];
                InputStream in = this.getClass().getClassLoader().getResourceAsStream("resource/util/UserManual.pdf");
                FileOutputStream out = new FileOutputStream(tf);
                int cnt;
                while ((cnt = in.read(buffer)) != -1) {
                    out.write(buffer, 0, cnt);
                }
                in.close();
                out.close();
                desktop.open(tf);
            } catch (Exception e) {
                System.out.println("RMI file not found");
            }
        }
    }//GEN-LAST:event_btnHelpActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnWatchReplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWatchReplayActionPerformed
        setButtonsEnabled(false);
        try {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                ReplayData replayData = readReplayData(file);

                SimulationController simulationController = new SimulationController();
                GameParams params = replayData.getGameParams();
                simulationController.setLevel(params.getStartLevel());
                final ReplayController replayController = new ReplayController(replayData, simulationController);
                
                GameEngine localGameEngine = new GameEngine(replayData.getOwnSessionId(), params.getBlockQueueSeed(), params.isIncludeSpecialBlocks(), params.getNbrOfJokers());
                simulationController.addSession(replayData.getOwnSessionId(), replayData.getSessionList().get(replayData.getOwnSessionId()), localGameEngine);
                
                List<SimulationStateAbstract> otherEngines = new ArrayList<SimulationStateAbstract>();
                for (Map.Entry<Integer, String> session : replayData.getSessionList().entrySet()) {
                    if (session.getKey() != replayData.getOwnSessionId()) {
                        GameEngine gameEngine = new GameEngine(session.getKey(), params.getBlockQueueSeed(), params.isIncludeSpecialBlocks(), params.getNbrOfJokers());
                        otherEngines.add(gameEngine);
                        simulationController.addSession(session.getKey(), session.getValue(), gameEngine);
                    }
                }
                
                final GameFieldJFrame gameFieldJFrame = new GameFieldJFrame(new InputSampler() {

                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        return false;
                    }
                }, localGameEngine, otherEngines);

                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        setVisible(false);
                        gameFieldJFrame.setVisible(true);
                        replayController.run();
                    }
                });
            } else {
                setButtonsEnabled(true);
            }
        } catch (FileNotFoundException e) {
            setButtonsEnabled(true);
            showExceptionDialog(e);
        } catch (IOException e) {
            setButtonsEnabled(true);
            JOptionPane.showMessageDialog(MainJFrame.this, "Not a Replay File", "Exception", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            setButtonsEnabled(true);
            JOptionPane.showMessageDialog(MainJFrame.this, "Old Version of Replay File", "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnWatchReplayActionPerformed

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
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
                NetworkHandlerAbstract nh = ControllerFactory.getNetworkHandler();
                new MainJFrame(ControllerFactory.getTetrisController(nh), new ChatController(nh)).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreateGame;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnJoinGame;
    private javax.swing.JButton btnStartGame;
    private javax.swing.JButton btnWatchReplay;
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


    private void showLobby(final GameMode gameMode) {
        final LobbyJFrame lobby = new LobbyJFrame(tetrisController, chatController, gameMode);
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (gameMode != GameMode.SINGLE_PLAYER) {
                    lobby.setVisible(true);
                }
                setVisible(false);
            }
        });
    }
}
