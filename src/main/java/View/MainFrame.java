package View;

import Controller.MusicController;
import value.PlayerImg;
import View.Aside.Aside;
import View.Bottom.BottomMenu;

import javax.swing.*;

public class MainFrame extends JFrame {
    private BottomMenu bottom;
    private Aside aside;
    private LyricsPanel lyricsPanel;
    private static MainFrame mainFrame;

    static {
        try {
            mainFrame = new MainFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MainFrame getInstance() {
        return mainFrame;
    }

    private MainFrame() {
        setLayout(null); //自定义布局
        setBounds(0, 0, 1090, 830);
        setResizable(false);
        setVisible(true);
        setIconImage(PlayerImg.music);//设置自己的图标
        setLocationRelativeTo(null); //居中显示
        bottom = new BottomMenu();
        bottom.setBounds(0, 700, 1080, 100);
        add(bottom);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //侧边栏
        aside = new Aside();
        aside.setBounds(0, 0, 300, 700);
        add(aside);
        //TopBar
        TopBar topBar = new TopBar();
        topBar.setBounds(300, 0, 780, 100);
        add(topBar);
        //歌词
        lyricsPanel = new LyricsPanel();
        lyricsPanel.setBounds(300, 100, 780, 600);
        add(lyricsPanel);
        repaint();
    }

    public BottomMenu getBottom() {
        return bottom;
    }

    public LyricsPanel getLyricsPanel() {
        return this.lyricsPanel;
    }

    public Aside getAside() {
        return this.aside;
    }

    public static void main(String[] args) throws Exception {
        MusicController.getInstance().init();
    }
}
