package Components;

import javax.swing.*;
import java.awt.*;

public class PlayerTextButton extends JButton {
    public PlayerTextButton(String text) {
        super(text);
        setBackground(null);
        setBorder(null);
        setVisible(true);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
