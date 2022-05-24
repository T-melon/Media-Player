package View.Aside;

import Components.PlayerTextButton;
import Util.ClickEvent;
import Value.PlayerColor;
import Value.PlayerFont;
import View.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class AsideTop extends JPanel {
    private PlayerTextButton music;
    private PlayerTextButton video;

    public AsideTop() {
        setLayout(null);
        setBackground(PlayerColor.Default);
        setBounds(0, 0, 300, 100);
        music = new PlayerTextButton("音乐");
        music.setFont(PlayerFont.AsideTopSelected);
        music.setBounds(20, 60, 75, 35);
        add(music);
        video = new PlayerTextButton("视频");
        video.setFont(PlayerFont.AsideTop);
        video.setBounds(110, 60, 75, 35);
        add(video);

        music.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                music.setFont(PlayerFont.AsideTopSelected);
                video.setFont(PlayerFont.AsideTop);
                MainFrame.getInstance().getAside().showMusicPanel();
            }
        });

        video.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                music.setFont(PlayerFont.AsideTop);
                video.setFont(PlayerFont.AsideTopSelected);
                MainFrame.getInstance().getAside().showVideoPanel();
            }
        });
    }
}
