package View.Bottom;

import Value.PlayerColor;

import javax.swing.*;


public class BottomMenu extends JPanel {
    private JPanel left;
    private JPanel right;

    public BottomMenu() {
        setBackground(PlayerColor.Bottom);
        setLayout(null);
        left = new BottomMenuLeft();
        left.setBounds(0, 0, 170, 100);
        left.setVisible(true);
        add(left);
        right = new BottomMenuRight();
        right.setBounds(170, 0, 900, 100);
        right.setVisible(true);
        add(right);
    }

    public JPanel getLeft() {
        return left;
    }

    public JPanel getRight() {
        return right;
    }
}
