package View;

import value.PlayerColor;
import value.PlayerFont;

import javax.swing.*;

public class LyricsPanel extends JPanel {
    private JLabel lyrics1;
    private JLabel lyrics2;
    private JLabel lyrics3;
    private JLabel lyrics4;
    private JLabel lyrics5;

    public LyricsPanel() {
        setLayout(null);
        setBackground(PlayerColor.LyricsBackground);

        lyrics1 = new JLabel("", JLabel.CENTER);
        lyrics1.setForeground(PlayerColor.Lyrics23);
        lyrics1.setFont(PlayerFont.Lyrics1);
        lyrics1.setBounds(0, 100, 780, 20);
        add(lyrics1);

        lyrics2 = new JLabel("", JLabel.CENTER);
        lyrics2.setForeground(PlayerColor.Lyrics23);
        lyrics2.setFont(PlayerFont.Lyrics2);
        lyrics2.setBounds(0, 175, 780, 25);
        add(lyrics2);

        lyrics3 = new JLabel("", JLabel.CENTER);
        lyrics3.setForeground(PlayerColor.Lyrics1);
        lyrics3.setFont(PlayerFont.Lyrics3);
        lyrics3.setBounds(0, 250, 780, 30);
        add(lyrics3);

        lyrics4 = new JLabel("", JLabel.CENTER);
        lyrics4.setForeground(PlayerColor.Lyrics23);
        lyrics4.setFont(PlayerFont.Lyrics2);
        lyrics4.setBounds(0, 325, 780, 25);
        add(lyrics4);

        lyrics5 = new JLabel("", JLabel.CENTER);
        lyrics5.setForeground(PlayerColor.Lyrics23);
        lyrics5.setFont(PlayerFont.Lyrics1);
        lyrics5.setBounds(0, 400, 780, 20);
        add(lyrics5);
    }

    public void setLyricsContent(String s1, String s2, String s3, String s4, String s5) {
        lyrics1.setText(s1);
        lyrics2.setText(s2);
        lyrics3.setText(s3);
        lyrics4.setText(s4);
        lyrics5.setText(s5);
    }

}
