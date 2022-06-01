package component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Cursor;

/**
 * @author Tian
 */
public class PlayerIconButton extends JButton {
/**
  PlayerIconButton继承JButton类,创建一个无标签文本,有图标的按钮
  */
    public PlayerIconButton(Icon icon) {
        super("", icon);
        setBackground(null);
        setBorder(null);
        setVisible(true);
        setHorizontalTextPosition(SwingConstants.CENTER);
        //设置鼠标点击该按钮时候的光标是一个小手
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
