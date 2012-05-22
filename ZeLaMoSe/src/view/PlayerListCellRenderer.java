package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class PlayerListCellRenderer extends DefaultListCellRenderer {

    private final int ownSessionID;
    private final Color[] PLAYER_COLORS = new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.ORANGE, Color.CYAN, Color.YELLOW, Color.MAGENTA, Color.PINK};

    public PlayerListCellRenderer(int ownSessionID) {
        this.ownSessionID = ownSessionID;
        this.setFont(new Font("Helvetica", Font.BOLD, 12));
    }

    @Override
    public Component getListCellRendererComponent(JList jlist, Object o, int i, boolean bln, boolean bln1) {
        Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) o;
        if (entry.getKey() == ownSessionID) {
            setBackground(new Color(230, 245, 245));
        } else {
            setBackground(Color.white);
        }
        setForeground(getPlayerColor(entry.getKey()));
        setText(entry.getValue());
        return this;
    }

    public Color getPlayerColor(int sessionID) {
        return PLAYER_COLORS[sessionID % PLAYER_COLORS.length];
    }
}
