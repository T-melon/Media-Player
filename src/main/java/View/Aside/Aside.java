package View.Aside;

import UI.ScrollBarUI;
import Value.MediaType;

import javax.swing.*;

public class Aside extends JPanel {
    private AsideList asideList;
    private JScrollPane scrollPane;

    public Aside() {
        setLayout(null);
        setBounds(0, 0, 300, 700);
        AsideTop asideTop = new AsideTop();
        asideTop.setBounds(0, 0, 300, 100);
        asideTop.setVisible(true);
        add(asideTop);
        asideList = new AsideList(MediaType.MusicType);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new ScrollBarUI());
        scrollPane.setBounds(0, 100, 300, 600);
        scrollPane.setViewportView(asideList);
        scrollPane.setBorder(null);
        add(scrollPane);
    }

    public void showMusicPanel() {
        asideList = new AsideList(MediaType.MusicType);
        scrollPane.setViewportView(asideList);
        repaint();
    }

    public void showVideoPanel() {
        asideList = new AsideList(MediaType.VideoType);
        scrollPane.setViewportView(asideList);
        repaint();
    }

}
