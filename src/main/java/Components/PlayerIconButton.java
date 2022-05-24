package Components;

import javax.swing.*;
import java.awt.*;

public class PlayerIconButton extends JButton {
    public PlayerIconButton(Icon icon) {
        super("", icon);
        setBackground(null);
        setBorder(null);
        setVisible(true);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
