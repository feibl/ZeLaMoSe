package view;

import com.jogamp.opengl.util.FPSAnimator;
import domain.Config;
import domain.InputSampler;
import domain.SimulationStateAbstract;
import java.awt.KeyboardFocusManager;
import java.util.Observable;
import java.util.Observer;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.swing.SwingUtilities;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class OwnGameFieldJPanel extends javax.swing.JPanel implements Observer {


    /**
     * Creates new form pnlGameField
     */
    public OwnGameFieldJPanel() {
        initComponents();


    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code.
     * The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        glPnlGameField = new javax.media.opengl.awt.GLJPanel(getGLCaps());
        lblNextPiece = new javax.swing.JLabel();
        lblYourScoreValue = new javax.swing.JLabel();
        lblPlayerName = new javax.swing.JLabel();
        lblZeLaMoSe = new javax.swing.JLabel();
        lblLevelValue = new javax.swing.JLabel();
        lblLevel = new javax.swing.JLabel();
        lblNumberOfLinesValue = new javax.swing.JLabel();
        glPnlNextBlock = new javax.media.opengl.awt.GLJPanel(getGLCaps());
        lblBlockCounter1 = new javax.swing.JLabel();
        lblJokerValue = new javax.swing.JLabel();
        lblNbrOfLines1 = new javax.swing.JLabel();
        lblJoker1 = new javax.swing.JLabel();
        lblNbrOfBlocks = new javax.swing.JLabel();
        lblScore1 = new javax.swing.JLabel();
        lblNbrOfBlocks1 = new javax.swing.JLabel();
        lblBlockCounter2 = new javax.swing.JLabel();
        lblBlockCounter3 = new javax.swing.JLabel();
        lblBlockCounter4 = new javax.swing.JLabel();
        lblBlockCounter5 = new javax.swing.JLabel();
        lblBlockCounter6 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(730, 1080));

        glPnlGameField.setPreferredSize(new java.awt.Dimension(480, 880));

        javax.swing.GroupLayout glPnlGameFieldLayout = new javax.swing.GroupLayout(glPnlGameField);
        glPnlGameField.setLayout(glPnlGameFieldLayout);
        glPnlGameFieldLayout.setHorizontalGroup(
            glPnlGameFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        glPnlGameFieldLayout.setVerticalGroup(
            glPnlGameFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );

        lblNextPiece.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNextPiece.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNextPiece.setText("<html><b>Next:</b></html>");
        lblNextPiece.setMaximumSize(new java.awt.Dimension(81, 18));
        lblNextPiece.setMinimumSize(new java.awt.Dimension(81, 18));
        lblNextPiece.setPreferredSize(new java.awt.Dimension(81, 18));

        lblYourScoreValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblYourScoreValue.setText("<YourScoreValue>");

        lblPlayerName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblPlayerName.setText("<PlayerName>");

        lblZeLaMoSe.setText("© ZeLaMoSe");

        lblLevelValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblLevelValue.setText("<LevelValue>");

        lblLevel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblLevel.setText("<html><b>Level:</b></html>");
        lblLevel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblLevel.setMaximumSize(new java.awt.Dimension(81, 21));
        lblLevel.setMinimumSize(new java.awt.Dimension(81, 21));
        lblLevel.setPreferredSize(new java.awt.Dimension(81, 21));

        lblNumberOfLinesValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNumberOfLinesValue.setText("<NumberOfLinesValue>");

        glPnlNextBlock.setMaximumSize(new java.awt.Dimension(160, 160));
        glPnlNextBlock.setMinimumSize(new java.awt.Dimension(160, 160));
        glPnlNextBlock.setPreferredSize(new java.awt.Dimension(160, 160));

        javax.swing.GroupLayout glPnlNextBlockLayout = new javax.swing.GroupLayout(glPnlNextBlock);
        glPnlNextBlock.setLayout(glPnlNextBlockLayout);
        glPnlNextBlockLayout.setHorizontalGroup(
            glPnlNextBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        glPnlNextBlockLayout.setVerticalGroup(
            glPnlNextBlockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        lblBlockCounter1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBlockCounter1.setText("<YourBlocksValue>");

        lblJokerValue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblJokerValue.setText("<JokerValue>");

        lblNbrOfLines1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNbrOfLines1.setText("<html><b>Number of Lines:</b></html>");
        lblNbrOfLines1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblNbrOfLines1.setMaximumSize(new java.awt.Dimension(81, 21));
        lblNbrOfLines1.setMinimumSize(new java.awt.Dimension(81, 21));
        lblNbrOfLines1.setPreferredSize(new java.awt.Dimension(81, 21));

        lblJoker1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblJoker1.setText("<html><b>Joker:</b></html>");
        lblJoker1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblJoker1.setMaximumSize(new java.awt.Dimension(81, 21));
        lblJoker1.setMinimumSize(new java.awt.Dimension(81, 21));
        lblJoker1.setPreferredSize(new java.awt.Dimension(81, 21));

        lblNbrOfBlocks.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNbrOfBlocks.setText("<html><b>Number of Blocks:</b></html>");
        lblNbrOfBlocks.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblNbrOfBlocks.setMaximumSize(new java.awt.Dimension(81, 21));
        lblNbrOfBlocks.setMinimumSize(new java.awt.Dimension(81, 21));
        lblNbrOfBlocks.setPreferredSize(new java.awt.Dimension(81, 21));

        lblScore1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblScore1.setText("<html><b>Score:</b></html>");
        lblScore1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblScore1.setMaximumSize(new java.awt.Dimension(81, 21));
        lblScore1.setMinimumSize(new java.awt.Dimension(81, 21));
        lblScore1.setPreferredSize(new java.awt.Dimension(81, 21));

        lblNbrOfBlocks1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNbrOfBlocks1.setText("<html><b>Controls:</b></html>");
        lblNbrOfBlocks1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lblNbrOfBlocks1.setMaximumSize(new java.awt.Dimension(81, 21));
        lblNbrOfBlocks1.setMinimumSize(new java.awt.Dimension(81, 21));
        lblNbrOfBlocks1.setPreferredSize(new java.awt.Dimension(81, 21));

        lblBlockCounter2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBlockCounter2.setText("Move:      left / right arrow");

        lblBlockCounter3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBlockCounter3.setText("Rotate:     X / Y");

        lblBlockCounter4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBlockCounter4.setText("Softdrop:  down arrow");

        lblBlockCounter5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBlockCounter5.setText("Harddrop: up arrow / space");

        lblBlockCounter6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblBlockCounter6.setText("Joker:       J");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPlayerName, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(glPnlGameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(glPnlNextBlock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblJoker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblNbrOfBlocks, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                                .addComponent(lblNbrOfLines1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblNbrOfBlocks1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                            .addComponent(lblNextPiece, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblBlockCounter1)
                                    .addComponent(lblNumberOfLinesValue)
                                    .addComponent(lblLevelValue)
                                    .addComponent(lblYourScoreValue)
                                    .addComponent(lblJokerValue)))
                            .addComponent(lblBlockCounter3)
                            .addComponent(lblBlockCounter2)
                            .addComponent(lblBlockCounter4)
                            .addComponent(lblBlockCounter5)
                            .addComponent(lblBlockCounter6)))))
            .addGroup(layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(lblZeLaMoSe))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPlayerName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblYourScoreValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblLevelValue)
                        .addGap(41, 41, 41)
                        .addComponent(lblJoker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblJokerValue)
                        .addGap(43, 43, 43)
                        .addComponent(lblNbrOfLines1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNumberOfLinesValue)
                        .addGap(18, 18, 18)
                        .addComponent(lblNbrOfBlocks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBlockCounter1)
                        .addGap(18, 18, 18)
                        .addComponent(lblNbrOfBlocks1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblBlockCounter2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBlockCounter3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBlockCounter4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBlockCounter5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblBlockCounter6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNextPiece, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(glPnlNextBlock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(glPnlGameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblZeLaMoSe)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        lblNextPiece.getAccessibleContext().setAccessibleName("<html><b>Next</b></html>");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.media.opengl.awt.GLJPanel glPnlGameField;
    private javax.media.opengl.awt.GLJPanel glPnlNextBlock;
    private javax.swing.JLabel lblBlockCounter1;
    private javax.swing.JLabel lblBlockCounter2;
    private javax.swing.JLabel lblBlockCounter3;
    private javax.swing.JLabel lblBlockCounter4;
    private javax.swing.JLabel lblBlockCounter5;
    private javax.swing.JLabel lblBlockCounter6;
    private javax.swing.JLabel lblJoker1;
    private javax.swing.JLabel lblJokerValue;
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblLevelValue;
    private javax.swing.JLabel lblNbrOfBlocks;
    private javax.swing.JLabel lblNbrOfBlocks1;
    private javax.swing.JLabel lblNbrOfLines1;
    private javax.swing.JLabel lblNextPiece;
    private javax.swing.JLabel lblNumberOfLinesValue;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JLabel lblScore1;
    private javax.swing.JLabel lblYourScoreValue;
    private javax.swing.JLabel lblZeLaMoSe;
    // End of variables declaration//GEN-END:variables
    private GameFieldRenderer renderer;
    private SimulationStateAbstract gameEngine;

    public void initRenderer(SimulationStateAbstract gameEngine) {
        this.gameEngine = gameEngine;
        gameEngine.addObserver(this);
        initOwnGameFieldRenderer();
        initNextBlockRenderer();


    }

    public void setInputSampler(InputSampler is) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(is);
    }

    private GLCapabilities getGLCaps() {
        //best GL settings
        GLProfile glp = GLProfile.getDefault();
        return new GLCapabilities(glp);
    }

    @Override
    public void update(Observable o, Object arg) {
        final SimulationStateAbstract ge = (SimulationStateAbstract) o;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                lblLevelValue.setText(Integer.toString(ge.getLevel()));
                lblYourScoreValue.setText(Integer.toString(ge.getScore()));
                lblNumberOfLinesValue.setText(Integer.toString(ge.getTotalRemovedLines()));
                lblBlockCounter1.setText(Integer.toString(ge.getBlockCounter()));
                lblJokerValue.setText(Integer.toString(ge.getNumberOfJokers()));
            }
        });


    }

    private void initOwnGameFieldRenderer() {
        renderer = new GameFieldRenderer(Config.ownGameFieldBlockSize, gameEngine, true);
        glPnlGameField.addGLEventListener(renderer);
        FPSAnimator ownGameFieldAnimator = new FPSAnimator(glPnlGameField, Config.frameRate, true);
        ownGameFieldAnimator.start();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                lblPlayerName.setText(gameEngine.getNickName());
            }
        });
    }

    private void initNextBlockRenderer() {

        glPnlNextBlock.addGLEventListener(new NextBlockRenderer(gameEngine));
        FPSAnimator ownGameFieldAnimator = new FPSAnimator(glPnlNextBlock, Config.frameRate, true);
        ownGameFieldAnimator.start();
    }
}
