package View.Bottom;

import value.PlayerColor;
import value.PlayerFont;

import javax.swing.*;


public class BottomMenuLeft extends JPanel {

    private JLabel title;

    public BottomMenuLeft() {
        setLayout(null);
        setBackground(PlayerColor.BottomLeftBackground);
        title = new JLabel("", JLabel.CENTER);
        title.setVisible(true);
        title.setBounds(0, 0, 170, 100);
        title.setFont(PlayerFont.BottomLeft);
        title.setForeground(PlayerColor.BottomTextColor);
        add(title);
    }

    public void setTitle(String name) {
        this.title.setText(name);
    }

    public String getTitle(){
        return title.getText();
    }

}
