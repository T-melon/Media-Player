package View;

import Components.PlayerIconButton;
import UI.SliderUI;
import Util.ClickEvent;
import Value.*;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class VideoFrame extends JFrame {
    private EmbeddedMediaPlayerComponent playerComponent;
    private JSlider slider;
    private PlayerIconButton play;
    private boolean showSubTitleFile = true;
    private String videoName;

    public VideoFrame(String fileName) {
        this.videoName = fileName;
        setTitle(fileName);
        setLayout(null); //自定义布局
        setBounds(0, 0, 712, 512);
        setResizable(false);
        setIconImage(PlayerImg.video);//设置自己的图标
        setLocationRelativeTo(null); //居中显示

        JPanel videoPanel = new JPanel();
        videoPanel.setBounds(0, 0, 712, 390);
        add(videoPanel);
        videoPanel.setLayout(null);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), MediaDirectory.vclDirectory);//安装目录
        this.playerComponent = new EmbeddedMediaPlayerComponent();
        this.playerComponent.setBounds(0, 0, 712, 390);
        videoPanel.add(playerComponent);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 390, 712, 122);
        panel.setBackground(PlayerColor.BottomBackground);

        this.slider = new JSlider(0, 100, 0);
        this.slider.setBounds(5, 0, 695, 24);
        this.slider.setUI(new SliderUI(slider));
        this.slider.setBackground(PlayerColor.Bottom);
        this.slider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(this.slider);

        PlayerIconButton back = new PlayerIconButton(PlayerIcon.backward);
        back.setBounds(200, 30, 35, 35);
        panel.add(back);
        PlayerIconButton fore = new PlayerIconButton(PlayerIcon.forward);
        fore.setBounds(480, 28, 35, 35);
        panel.add(fore);

        //控制弹幕开关
        final PlayerIconButton subtitle = new PlayerIconButton(PlayerIcon.openSubTitle);
        subtitle.setBounds(600, 30, 35, 35);
        subtitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (showSubTitleFile) {//关
                    playerComponent.getMediaPlayer().setSubTitleFile(new File("src\\main\\resources\\video\\clearSubTitle.ass"));
                    subtitle.setIcon(PlayerIcon.closeSubTitle);
                } else { //开
                    String tmp = MediaDirectory.VideoDirectory + videoName + "/" + videoName + ".ass";
                    playerComponent.getMediaPlayer().setSubTitleFile(new File(tmp));
                    subtitle.setIcon(PlayerIcon.openSubTitle);
                }
                showSubTitleFile = !showSubTitleFile;
            }
        });
        panel.add(subtitle);

        this.play = new PlayerIconButton(PlayerIcon.pause);
        this.play.setBounds(330, 20, 50, 50);
        panel.add(this.play);

        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setProcess(slider.getValue() * 0.01);
            }
        });

        back.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                backward();
            }
        });
        play.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isPlaying()) {//播放->暂停
                    play.setIcon(PlayerIcon.play);
                    pause();
                    repaint();
                } else {//暂停->播放
                    play.setIcon(PlayerIcon.pause);
                    replay();
                    repaint();
                }
            }
        });
        fore.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                forward();
            }
        });

        add(videoPanel);
        add(panel);

        File rootDirectory = new File(MediaDirectory.VideoDirectory + fileName);
        String[] fileList = rootDirectory.list();
        assert fileList != null;
        String videoFileName = "";
        for (String file : fileList) {
            if (MediaType.isVideo(file)) {
                videoFileName = file;
                break;
            }
        }
        assert videoFileName.length() > 0;
        this.playerComponent.getMediaPlayer().prepareMedia(MediaDirectory.VideoDirectory + fileName + '/' + videoFileName);
        setVisible(true);
        this.playerComponent.getMediaPlayer().play();

        new SwingWorker<String, Float>() {
            protected String doInBackground() throws Exception {
                while (true) {
                    long total = playerComponent.getMediaPlayer().getLength();//获得当前视频总时间长度
                    long curr = playerComponent.getMediaPlayer().getTime();//获得当期播放时间
                    float percent = ((float) curr / total);//获取播放视频的百分比
                    publish(percent);
                    Thread.sleep(1000);
                }
            }

            protected void process(java.util.List<Float> chunks) {
                for (float v : chunks) {
                    slider.setValue((int) (v * 100));
                }
            }
        }.execute();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                playerComponent.getMediaPlayer().stop();
            }
        });

    }

    private void backward() {
        long cur = this.playerComponent.getMediaPlayer().getTime();//当前时间
        cur -= 5000;//后退5秒
        if (cur > 0) {
            this.playerComponent.getMediaPlayer().setTime(cur);
        } else {
            this.playerComponent.getMediaPlayer().setTime(0);
        }
    }

    private void forward() {
        long cur = this.playerComponent.getMediaPlayer().getTime();//当前时间
        cur += 5000;//后退5秒
        if (cur < this.playerComponent.getMediaPlayer().getLength()) {
            this.playerComponent.getMediaPlayer().setTime(cur);
        } else {
            this.playerComponent.getMediaPlayer().setTime(this.playerComponent.getMediaPlayer().getLength() - 1000);
        }
    }

    private void setProcess(double to) {
        this.playerComponent.getMediaPlayer().setTime((long) (to * this.playerComponent.getMediaPlayer().getLength()));
    }

    private boolean isPlaying() {
        return this.playerComponent.getMediaPlayer().isPlaying();
    }

    private void pause() {
        this.playerComponent.getMediaPlayer().pause();

    }

    private void replay() {
        this.playerComponent.getMediaPlayer().play();

    }

}
