/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jogamp.opengl.util.FPSAnimator;
import domain.fake.FakeController;
import domain.GameEngine;
import domain.InputSampler;
import domain.StepGeneratorImpl;
import domain.interfaces.SimulationStateInterface;
import java.awt.KeyboardFocusManager;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;

/**
 *
 * @author Patrick Zenhäusern
 */
public class OwnGameFieldJPanel extends javax.swing.JPanel {
    private int FRAME_RATE = 30;

  /**
   * Creates new form pnlGameField
   */
  public OwnGameFieldJPanel() {
    initComponents();
    initGLRenderer();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code.
   * The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTimeValue = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        glPnlGameField = new javax.media.opengl.awt.GLJPanel(getGLCaps());
        pnlNextPiece = new javax.swing.JPanel();
        lblNextPiece = new javax.swing.JLabel();
        lblYourScoreValue = new javax.swing.JLabel();
        tglSound = new javax.swing.JToggleButton();
        lblPlayerName = new javax.swing.JLabel();
        lblYourScore = new javax.swing.JLabel();
        lblOtherPlayerName2 = new javax.swing.JLabel();
        lblOtherScores = new javax.swing.JLabel();
        lblOtherPlayerName1 = new javax.swing.JLabel();
        lblOtherScoreValue3 = new javax.swing.JLabel();
        lblOtherScoreValue2 = new javax.swing.JLabel();
        lblOtherScoreValue1 = new javax.swing.JLabel();
        lblOtherPlayerName3 = new javax.swing.JLabel();
        lblZeLaMoSe = new javax.swing.JLabel();
        lblLevelValue = new javax.swing.JLabel();
        lblLevel = new javax.swing.JLabel();
        lblNumberOfLines = new javax.swing.JLabel();
        lblNumberOfLinesValue = new javax.swing.JLabel();

        lblTimeValue.setText("<TimeValue>");

        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setText("Time");
        lblTime.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblTime.setMaximumSize(new java.awt.Dimension(81, 18));
        lblTime.setMinimumSize(new java.awt.Dimension(81, 18));
        lblTime.setPreferredSize(new java.awt.Dimension(81, 18));

        glPnlGameField.setPreferredSize(new java.awt.Dimension(360, 660));

        javax.swing.GroupLayout glPnlGameFieldLayout = new javax.swing.GroupLayout(glPnlGameField);
        glPnlGameField.setLayout(glPnlGameFieldLayout);
        glPnlGameFieldLayout.setHorizontalGroup(
            glPnlGameFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        glPnlGameFieldLayout.setVerticalGroup(
            glPnlGameFieldLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );

        pnlNextPiece.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout pnlNextPieceLayout = new javax.swing.GroupLayout(pnlNextPiece);
        pnlNextPiece.setLayout(pnlNextPieceLayout);
        pnlNextPieceLayout.setHorizontalGroup(
            pnlNextPieceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        pnlNextPieceLayout.setVerticalGroup(
            pnlNextPieceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 131, Short.MAX_VALUE)
        );

        lblNextPiece.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNextPiece.setText("Next Piece");
        lblNextPiece.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblNextPiece.setMaximumSize(new java.awt.Dimension(81, 18));
        lblNextPiece.setMinimumSize(new java.awt.Dimension(81, 18));
        lblNextPiece.setPreferredSize(new java.awt.Dimension(81, 18));

        lblYourScoreValue.setText("<YourScoreValue>");

        tglSound.setText("Sound on");

        lblPlayerName.setText("<PlayerName>");

        lblYourScore.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblYourScore.setText("Your Score");
        lblYourScore.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblYourScore.setMaximumSize(new java.awt.Dimension(81, 18));
        lblYourScore.setMinimumSize(new java.awt.Dimension(81, 18));
        lblYourScore.setPreferredSize(new java.awt.Dimension(81, 18));

        lblOtherPlayerName2.setText("<OtherPlayerName2>");

        lblOtherScores.setText("Other Scores");
        lblOtherScores.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblOtherScores.setMaximumSize(new java.awt.Dimension(81, 18));
        lblOtherScores.setMinimumSize(new java.awt.Dimension(81, 18));
        lblOtherScores.setPreferredSize(new java.awt.Dimension(81, 18));

        lblOtherPlayerName1.setText("<OtherPlayerName1>");

        lblOtherScoreValue3.setText("<OtherScoreValue3>");

        lblOtherScoreValue2.setText("<OtherScoreValue2>");

        lblOtherScoreValue1.setText("<OtherScoreValue1>");

        lblOtherPlayerName3.setText("<OtherPlayerName3>");

        lblZeLaMoSe.setText("© ZeLaMoSe");

        lblLevelValue.setText("<LevelValue>");

        lblLevel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLevel.setText("Level");
        lblLevel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        lblLevel.setMaximumSize(new java.awt.Dimension(81, 18));
        lblLevel.setMinimumSize(new java.awt.Dimension(81, 18));
        lblLevel.setPreferredSize(new java.awt.Dimension(81, 18));

        lblNumberOfLines.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfLines.setText("Number of Lines");
        lblNumberOfLines.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNumberOfLinesValue.setText("<NumberOfLinesValue>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(580, 580, 580)
                .addComponent(tglSound))
            .addGroup(layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(lblPlayerName))
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(glPnlGameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblYourScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblYourScoreValue)
                    .addComponent(lblOtherScores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOtherPlayerName1)
                    .addComponent(lblOtherPlayerName2)
                    .addComponent(lblOtherPlayerName3)
                    .addComponent(lblLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLevelValue)
                    .addComponent(lblNumberOfLines, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumberOfLinesValue)
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTimeValue)
                    .addComponent(lblNextPiece, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlNextPiece, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOtherScoreValue1)
                    .addComponent(lblOtherScoreValue2)
                    .addComponent(lblOtherScoreValue3)))
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(lblZeLaMoSe))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(tglSound)
                .addGap(10, 10, 10)
                .addComponent(lblPlayerName)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(glPnlGameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(lblYourScore, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(lblYourScoreValue)
                        .addGap(14, 14, 14)
                        .addComponent(lblOtherScores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(lblOtherPlayerName1)
                        .addGap(4, 4, 4)
                        .addComponent(lblOtherPlayerName2)
                        .addGap(4, 4, 4)
                        .addComponent(lblOtherPlayerName3)
                        .addGap(24, 24, 24)
                        .addComponent(lblLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblLevelValue)
                        .addGap(24, 24, 24)
                        .addComponent(lblNumberOfLines)
                        .addGap(20, 20, 20)
                        .addComponent(lblNumberOfLinesValue)
                        .addGap(14, 14, 14)
                        .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(lblTimeValue)
                        .addGap(14, 14, 14)
                        .addComponent(lblNextPiece, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(pnlNextPiece, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(lblOtherScoreValue1)
                        .addGap(4, 4, 4)
                        .addComponent(lblOtherScoreValue2)
                        .addGap(4, 4, 4)
                        .addComponent(lblOtherScoreValue3)))
                .addGap(8, 8, 8)
                .addComponent(lblZeLaMoSe))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.media.opengl.awt.GLJPanel glPnlGameField;
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblLevelValue;
    private javax.swing.JLabel lblNextPiece;
    private javax.swing.JLabel lblNumberOfLines;
    private javax.swing.JLabel lblNumberOfLinesValue;
    private javax.swing.JLabel lblOtherPlayerName1;
    private javax.swing.JLabel lblOtherPlayerName2;
    private javax.swing.JLabel lblOtherPlayerName3;
    private javax.swing.JLabel lblOtherScoreValue1;
    private javax.swing.JLabel lblOtherScoreValue2;
    private javax.swing.JLabel lblOtherScoreValue3;
    private javax.swing.JLabel lblOtherScores;
    private javax.swing.JLabel lblPlayerName;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTimeValue;
    private javax.swing.JLabel lblYourScore;
    private javax.swing.JLabel lblYourScoreValue;
    private javax.swing.JLabel lblZeLaMoSe;
    private javax.swing.JPanel pnlNextPiece;
    private javax.swing.JToggleButton tglSound;
    // End of variables declaration//GEN-END:variables

    private GLRenderer renderer;
    private void initGLRenderer() {
       
        renderer = new GLRenderer(360,660,30);
        glPnlGameField.addGLEventListener(renderer);
        FPSAnimator animator = new FPSAnimator(glPnlGameField, FRAME_RATE, true);
        animator.start();
    }
    
    public void startFakeGame() {
        InputSampler is = new InputSampler();
        setInputSampler(is);
        
        StepGeneratorImpl sg = new StepGeneratorImpl(is);
        sg.setSessionID(0);
        
        GameEngine ge = new GameEngine(0);
        setSimulation(ge);
        
        new FakeController(ge, sg);
        ge.startGame();
    }
    
    public void setInputSampler(InputSampler is) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(is);
    }
    
    public void setSimulation(SimulationStateInterface ge) {
        renderer.setEngine(ge);
    }
    
    private GLCapabilities getGLCaps(){
        //best GL settings
        GLProfile glp = GLProfile.getDefault();
        return new GLCapabilities(glp);
    }
}
