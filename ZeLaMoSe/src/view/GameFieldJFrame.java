/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jogamp.opengl.util.FPSAnimator;
import domain.Config;
import domain.GameEngine;
import domain.InputSampler;
import domain.interfaces.SimulationStateInterface;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingUtilities;

/**
 *
 * @author Patrick Zenh√§usern
 */
public class GameFieldJFrame extends javax.swing.JFrame implements Observer {

    private SimulationStateInterface enemyEngine1;
    private SimulationStateInterface enemyEngine2;
    private SimulationStateInterface enemyEngine3;

    /**
     * Creates new form frmGame
     */
    public GameFieldJFrame() {
        initComponents();
    }

    public GameFieldJFrame(InputSampler is, SimulationStateInterface mainSimulation, List<SimulationStateInterface> otherSimulations) {
        initComponents();
        ownGameFieldJPanel1.setInputSampler(is);
        ownGameFieldJPanel1.initRenderer(mainSimulation);
        //TODO set Simulation on other Panels

        if (!otherSimulations.isEmpty()) {
            enemyEngine1 = otherSimulations.remove(0);
            enemyEngine1.addObserver(this);
            initEnemyArea1(enemyEngine1);
        } else {
            initEnemyArea1(null);
        }

        if (!otherSimulations.isEmpty()) {
            enemyEngine2 = otherSimulations.remove(0);
            enemyEngine2.addObserver(this);
            initEnemyArea2(enemyEngine2);
        } else {
            initEnemyArea2(null);
        }

        if (!otherSimulations.isEmpty()) {
            enemyEngine3 = otherSimulations.remove(0);
            enemyEngine3.addObserver(this);
            initEnemyArea3(enemyEngine3);
        } else {
            initEnemyArea3(null);
        }
        
        repaint();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        lblEnemyArea = new javax.swing.JLabel();
        lblOtherPlayerName1 = new javax.swing.JLabel();
        lblOtherScoreValue1 = new javax.swing.JLabel();
        lblOtherPlayerName2 = new javax.swing.JLabel();
        lblOtherPlayerName3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        ownGameFieldJPanel1 = new view.OwnGameFieldJPanel();
        gLPnlEnemyArea1 = new javax.media.opengl.awt.GLJPanel();
        gLPnlEnemyArea2 = new javax.media.opengl.awt.GLJPanel();
        gLPnlEnemyArea3 = new javax.media.opengl.awt.GLJPanel();
        lblOtherNumberOfBlocks1 = new javax.swing.JLabel();
        lblOtherLevel1 = new javax.swing.JLabel();
        lblOtherNumbersOfLines1 = new javax.swing.JLabel();
        lblNumberOfLines = new javax.swing.JLabel();
        lblEnemyScore1 = new javax.swing.JLabel();
        lblEnemyLevel1 = new javax.swing.JLabel();
        lblNumberOfBlocks1 = new javax.swing.JLabel();
        lblNumberOfBlocks2 = new javax.swing.JLabel();
        lblEnemyLevel2 = new javax.swing.JLabel();
        lblEnemyScore2 = new javax.swing.JLabel();
        lblNumberOfLines1 = new javax.swing.JLabel();
        lblOtherNumberOfBlocks2 = new javax.swing.JLabel();
        lblOtherNumbersOfLines2 = new javax.swing.JLabel();
        lblOtherLevel2 = new javax.swing.JLabel();
        lblOtherScoreValue2 = new javax.swing.JLabel();
        lblNumberOfBlocks3 = new javax.swing.JLabel();
        lblEnemyLevel3 = new javax.swing.JLabel();
        lblEnemyScore3 = new javax.swing.JLabel();
        lblNumberOfLines2 = new javax.swing.JLabel();
        lblOtherNumberOfBlocks3 = new javax.swing.JLabel();
        lblOtherNumbersOfLines3 = new javax.swing.JLabel();
        lblOtherLevel3 = new javax.swing.JLabel();
        lblOtherScoreValue3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lblEnemyArea.setText("EnemyArea");

        lblOtherPlayerName1.setText("<OtherPlayerName1>");

        lblOtherScoreValue1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherScoreValue1.setText("<OtherScoreValue1>");

        lblOtherPlayerName2.setText("<OtherPlayerName2>");

        lblOtherPlayerName3.setText("<OtherPlayerName3>");

        gLPnlEnemyArea1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gLPnlEnemyArea1.setMaximumSize(new java.awt.Dimension(120, 220));
        gLPnlEnemyArea1.setMinimumSize(new java.awt.Dimension(120, 220));
        gLPnlEnemyArea1.setPreferredSize(new java.awt.Dimension(120, 220));

        javax.swing.GroupLayout gLPnlEnemyArea1Layout = new javax.swing.GroupLayout(gLPnlEnemyArea1);
        gLPnlEnemyArea1.setLayout(gLPnlEnemyArea1Layout);
        gLPnlEnemyArea1Layout.setHorizontalGroup(
            gLPnlEnemyArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );
        gLPnlEnemyArea1Layout.setVerticalGroup(
            gLPnlEnemyArea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );

        gLPnlEnemyArea2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gLPnlEnemyArea2.setMaximumSize(new java.awt.Dimension(120, 220));
        gLPnlEnemyArea2.setMinimumSize(new java.awt.Dimension(120, 220));
        gLPnlEnemyArea2.setPreferredSize(new java.awt.Dimension(120, 220));

        javax.swing.GroupLayout gLPnlEnemyArea2Layout = new javax.swing.GroupLayout(gLPnlEnemyArea2);
        gLPnlEnemyArea2.setLayout(gLPnlEnemyArea2Layout);
        gLPnlEnemyArea2Layout.setHorizontalGroup(
            gLPnlEnemyArea2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );
        gLPnlEnemyArea2Layout.setVerticalGroup(
            gLPnlEnemyArea2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );

        gLPnlEnemyArea3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        gLPnlEnemyArea3.setMaximumSize(new java.awt.Dimension(120, 220));
        gLPnlEnemyArea3.setMinimumSize(new java.awt.Dimension(120, 220));
        gLPnlEnemyArea3.setPreferredSize(new java.awt.Dimension(120, 220));

        javax.swing.GroupLayout gLPnlEnemyArea3Layout = new javax.swing.GroupLayout(gLPnlEnemyArea3);
        gLPnlEnemyArea3.setLayout(gLPnlEnemyArea3Layout);
        gLPnlEnemyArea3Layout.setHorizontalGroup(
            gLPnlEnemyArea3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 116, Short.MAX_VALUE)
        );
        gLPnlEnemyArea3Layout.setVerticalGroup(
            gLPnlEnemyArea3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 216, Short.MAX_VALUE)
        );

        lblOtherNumberOfBlocks1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherNumberOfBlocks1.setText("<OtherNumberOfBlock1>");

        lblOtherLevel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherLevel1.setText("<OtherLevel1>");

        lblOtherNumbersOfLines1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherNumbersOfLines1.setText("<OtherNumbersOfLines1>");

        lblNumberOfLines.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfLines.setText("Number of Lines");
        lblNumberOfLines.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEnemyScore1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEnemyScore1.setText("Score");
        lblEnemyScore1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEnemyLevel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEnemyLevel1.setText("Level");
        lblEnemyLevel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNumberOfBlocks1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfBlocks1.setText("Number of Lines");
        lblNumberOfBlocks1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNumberOfBlocks2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfBlocks2.setText("Number of Lines");
        lblNumberOfBlocks2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEnemyLevel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEnemyLevel2.setText("Level");
        lblEnemyLevel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEnemyScore2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEnemyScore2.setText("Score");
        lblEnemyScore2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNumberOfLines1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfLines1.setText("Number of Lines");
        lblNumberOfLines1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblOtherNumberOfBlocks2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherNumberOfBlocks2.setText("<OtherNumberOfBlock1>");

        lblOtherNumbersOfLines2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherNumbersOfLines2.setText("<OtherNumbersOfLines1>");

        lblOtherLevel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherLevel2.setText("<OtherLevel1>");

        lblOtherScoreValue2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherScoreValue2.setText("<OtherScoreValue1>");

        lblNumberOfBlocks3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfBlocks3.setText("Number of Lines");
        lblNumberOfBlocks3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEnemyLevel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEnemyLevel3.setText("Level");
        lblEnemyLevel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblEnemyScore3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEnemyScore3.setText("Score");
        lblEnemyScore3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNumberOfLines2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumberOfLines2.setText("Number of Lines");
        lblNumberOfLines2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblOtherNumberOfBlocks3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherNumberOfBlocks3.setText("<OtherNumberOfBlock1>");

        lblOtherNumbersOfLines3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherNumbersOfLines3.setText("<OtherNumbersOfLines1>");

        lblOtherLevel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherLevel3.setText("<OtherLevel1>");

        lblOtherScoreValue3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOtherScoreValue3.setText("<OtherScoreValue1>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ownGameFieldJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jSeparator3)
                        .addGap(99, 99, 99))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEnemyArea)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(gLPnlEnemyArea3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(lblNumberOfBlocks3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEnemyScore3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblEnemyLevel3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblOtherPlayerName3)
                                            .addComponent(lblNumberOfLines2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblOtherNumberOfBlocks3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblOtherNumbersOfLines3, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblOtherLevel3)
                                            .addComponent(lblOtherScoreValue3))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gLPnlEnemyArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(lblOtherPlayerName1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lblNumberOfBlocks1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEnemyLevel1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEnemyScore1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNumberOfLines, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOtherNumberOfBlocks1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOtherNumbersOfLines1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOtherLevel1)
                                    .addComponent(lblOtherScoreValue1))
                                .addGap(9, 9, 9))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gLPnlEnemyArea2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(lblNumberOfBlocks2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOtherNumbersOfLines2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOtherLevel2)
                                    .addComponent(lblNumberOfLines1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOtherNumberOfBlocks2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEnemyLevel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblEnemyScore2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblOtherScoreValue2)
                                    .addComponent(lblOtherPlayerName2))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblEnemyArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherPlayerName1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblEnemyScore1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherScoreValue1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnemyLevel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherLevel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumberOfLines)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherNumbersOfLines1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumberOfBlocks1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherNumberOfBlocks1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(gLPnlEnemyArea1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblOtherPlayerName2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnemyScore2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherScoreValue2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnemyLevel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherLevel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumberOfLines1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherNumbersOfLines2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumberOfBlocks2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherNumberOfBlocks2))
                    .addComponent(gLPnlEnemyArea2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblOtherPlayerName3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnemyScore3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherScoreValue3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnemyLevel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherLevel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumberOfLines2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherNumbersOfLines3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumberOfBlocks3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblOtherNumberOfBlocks3))
                    .addComponent(gLPnlEnemyArea3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ownGameFieldJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
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
            java.util.logging.Logger.getLogger(GameFieldJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameFieldJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameFieldJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameFieldJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                GameFieldJFrame gamefield = new GameFieldJFrame();
                gamefield.setVisible(true);
                gamefield.ownGameFieldJPanel1.startFakeGame();
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.media.opengl.awt.GLJPanel gLPnlEnemyArea1;
    private javax.media.opengl.awt.GLJPanel gLPnlEnemyArea2;
    private javax.media.opengl.awt.GLJPanel gLPnlEnemyArea3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblEnemyArea;
    private javax.swing.JLabel lblEnemyLevel1;
    private javax.swing.JLabel lblEnemyLevel2;
    private javax.swing.JLabel lblEnemyLevel3;
    private javax.swing.JLabel lblEnemyScore1;
    private javax.swing.JLabel lblEnemyScore2;
    private javax.swing.JLabel lblEnemyScore3;
    private javax.swing.JLabel lblNumberOfBlocks1;
    private javax.swing.JLabel lblNumberOfBlocks2;
    private javax.swing.JLabel lblNumberOfBlocks3;
    private javax.swing.JLabel lblNumberOfLines;
    private javax.swing.JLabel lblNumberOfLines1;
    private javax.swing.JLabel lblNumberOfLines2;
    private javax.swing.JLabel lblOtherLevel1;
    private javax.swing.JLabel lblOtherLevel2;
    private javax.swing.JLabel lblOtherLevel3;
    private javax.swing.JLabel lblOtherNumberOfBlocks1;
    private javax.swing.JLabel lblOtherNumberOfBlocks2;
    private javax.swing.JLabel lblOtherNumberOfBlocks3;
    private javax.swing.JLabel lblOtherNumbersOfLines1;
    private javax.swing.JLabel lblOtherNumbersOfLines2;
    private javax.swing.JLabel lblOtherNumbersOfLines3;
    private javax.swing.JLabel lblOtherPlayerName1;
    private javax.swing.JLabel lblOtherPlayerName2;
    private javax.swing.JLabel lblOtherPlayerName3;
    private javax.swing.JLabel lblOtherScoreValue1;
    private javax.swing.JLabel lblOtherScoreValue2;
    private javax.swing.JLabel lblOtherScoreValue3;
    private view.OwnGameFieldJPanel ownGameFieldJPanel1;
    // End of variables declaration//GEN-END:variables

    public OwnGameFieldJPanel getMainPanel() {
        return ownGameFieldJPanel1;
    }

    private void initEnemyArea1(SimulationStateInterface gameEngine) {
        GLRenderer rendererEnemyArea1 = new GLRenderer(Config.EnemyGameFieldBlockSize, false, gameEngine);
        gLPnlEnemyArea1.addGLEventListener(rendererEnemyArea1);
        FPSAnimator animator1 = new FPSAnimator(gLPnlEnemyArea1, Config.frameRate, true);
        animator1.start();
    }

    private void initEnemyArea2(SimulationStateInterface gameEngine) {
        GLRenderer rendererEnemyArea2 = new GLRenderer(Config.EnemyGameFieldBlockSize, false, gameEngine);
        gLPnlEnemyArea2.addGLEventListener(rendererEnemyArea2);
        FPSAnimator animator2 = new FPSAnimator(gLPnlEnemyArea2, Config.frameRate, true);
        animator2.start();
    }

    private void initEnemyArea3(SimulationStateInterface gameEngine) {
        GLRenderer rendererEnemyArea3 = new GLRenderer(Config.EnemyGameFieldBlockSize, false, gameEngine);
        gLPnlEnemyArea3.addGLEventListener(rendererEnemyArea3);
        FPSAnimator animator3 = new FPSAnimator(gLPnlEnemyArea3, Config.frameRate, true);
        animator3.start();
    }

    @Override
    public void update(Observable observable, Object object) {
        final GameEngine ObservableEngine = (GameEngine) observable;
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (ObservableEngine == enemyEngine1) {
                    lblOtherScoreValue1.setText(Integer.toString(ObservableEngine.getScore()));
                    lblOtherLevel1.setText(Integer.toString(ObservableEngine.getLevel()));
                    lblOtherNumbersOfLines1.setText(Integer.toString(ObservableEngine.getTotalRemovedLines()));
                    lblOtherNumberOfBlocks1.setText(Integer.toString(ObservableEngine.getBlockCounter()));
                    
                } else if (ObservableEngine == enemyEngine2) {
                    lblOtherScoreValue2.setText(Integer.toString(ObservableEngine.getScore()));
                    lblOtherLevel2.setText(Integer.toString(ObservableEngine.getLevel()));
                    lblOtherNumbersOfLines2.setText(Integer.toString(ObservableEngine.getTotalRemovedLines()));
                    lblOtherNumberOfBlocks2.setText(Integer.toString(ObservableEngine.getBlockCounter()));
                } else if (ObservableEngine == enemyEngine3) {
                    lblOtherScoreValue3.setText(Integer.toString(ObservableEngine.getScore()));
                    lblOtherLevel3.setText(Integer.toString(ObservableEngine.getLevel()));
                    lblOtherNumbersOfLines3.setText(Integer.toString(ObservableEngine.getTotalRemovedLines()));
                    lblOtherNumberOfBlocks3.setText(Integer.toString(ObservableEngine.getBlockCounter()));
                }
            }
        });


    }
}
