package View;

import value.PlayerColor;
import value.PlayerFont;

import javax.swing.*;

public class TopBar extends JPanel {
    public TopBar() {
        setLayout(null);
        setBackground(PlayerColor.Default);
        JLabel label = new JLabel("我的音乐");
        label.setFont(PlayerFont.TopBar);
        label.setBounds(30, 40, 500, 40);
        add(label);
    }
}
