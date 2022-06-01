package component;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Cursor;

/**
 * @author Tian
 */
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
