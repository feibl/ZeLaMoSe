/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Color;

/**
 *
 * @author Cyrill
 */
public enum StoneType {
    I(new Color(0,0,255)),
    L(new Color(255,0,0)),
    J(new Color(0,255,0));
    
    private final Color color;

    private StoneType(Color c) {
        color = c;
        
    }
    
    public Color getColor(){
        return color;
    }
    
    
    
}
