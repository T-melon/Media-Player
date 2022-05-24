package View.Aside;

import Components.PlayerTextButton;
import Controller.MusicController;
import Util.ClickEvent;
import Util.FileList;
import Value.MediaDirectory;
import Value.MediaType;
import Value.PlayerColor;
import Value.PlayerFont;
import View.Bottom.BottomMenuRight;
import View.MainFrame;
import View.VideoFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Vector;


enum Parity {odd, even}

class AsideListItem extends JPanel {

    public AsideListItem(final String name, final Parity parity) {
        if (parity == Parity.even) {
            setBackground(PlayerColor.ListEvenItemBackground);
        } else {
            setBackground(PlayerColor.ListOddItemBackground);
        }
        setLayout(null);
        JLabel title = new JLabel(name, JLabel.LEFT);
        title.setVisible(true);
        title.setBounds(20, 0, 280, 50);
        title.setFont(PlayerFont.AsideItem);
        title.setForeground(Color.BLACK);
        title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(title);
        JButton b = new PlayerTextButton("");
        b.setBounds(0, 0, 300, 50);
        b.setContentAreaFilled(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(b);
        b.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MediaType.isMusic(name)) {
                    try {
                        BottomMenuRight right = (BottomMenuRight) MainFrame.getInstance().getBottom().getRight();
                        right.playBegin();
                        MusicController.getInstance().playMusic(MediaDirectory.MusicDirectory + name);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new VideoFrame(name);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(PlayerColor.LyricsBackground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (parity == Parity.even) {
                    setBackground(PlayerColor.ListEvenItemBackground);
                } else {
                    setBackground(PlayerColor.ListOddItemBackground);
                }
            }

        });
    }
}

public class AsideList extends JPanel {

    public AsideList(String type) {
        setLayout(null);
        setBackground(PlayerColor.Default);
        Vector media;
        if (type.equals(MediaType.MusicType))
            media = FileList.getFileList(MediaDirectory.MusicDirectory, MediaType.MusicType);
        else
            media = FileList.getFileList(MediaDirectory.VideoDirectory, MediaType.VideoType);
        int num = media.size();
        for (int i = 0; i < num; i++) {
            AsideListItem item;
            item = new AsideListItem((String) media.get(i), i % 2 == 0 ? Parity.even : Parity.odd);
            item.setBounds(0, 50 * i, 300, 50);
            add(item);
        }
        setVisible(true);
        setPreferredSize(new Dimension(300, 50 * num));
    }


}
