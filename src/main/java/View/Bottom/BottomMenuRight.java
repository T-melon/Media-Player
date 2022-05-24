package View.Bottom;

import Components.PlayerIconButton;
import Controller.MusicController;
import UI.SliderUI;
import Util.ClickEvent;
import Util.SystemUtils;
import Util.TimeFormat;
import Value.PlayerColor;
import Value.PlayerIcon;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class BottomMenuRight extends JPanel {
    private JButton play;
    private JLabel current;
    private JLabel total;
    private JSlider voiceSlider;
    private JSlider playProcessSlider;
    private int vocality = 40;

    public BottomMenuRight() {
        setBackground(PlayerColor.Bottom);
        setLayout(null);

        play = new PlayerIconButton(PlayerIcon.play);
        play.setBounds(360, 5, 50, 50);
        add(play);
        play.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MusicController music = MusicController.getInstance();
                if (!music.isPlaying()) { //未在播放 -> 播放
                    play.setIcon(PlayerIcon.pause);
                    music.replay();
                } else { //处于播放 -> 暂停
                    play.setIcon(PlayerIcon.play);
                    music.pause();
                }
            }
        });

        JButton last = new PlayerIconButton(PlayerIcon.last);
        last.setBounds(270, 10, 35, 35);
        add(last);
        last.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                play.setIcon(PlayerIcon.pause);
                try {
                    MusicController.getInstance().playLastMusic();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });


        JButton next = new PlayerIconButton(PlayerIcon.next);
        next.setBounds(450, 10, 35, 35);
        add(next);
        next.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                play.setIcon(PlayerIcon.pause);
                try {
                    MusicController.getInstance().playNextMusic();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        final JButton randomNext = new PlayerIconButton(PlayerIcon.randomNext);
        randomNext.setBounds(740, 10, 35, 35);
        add(randomNext);

        final JButton cycle = new PlayerIconButton(PlayerIcon.cycle);
        cycle.setBounds(810, 10, 35, 35);
        add(cycle);

        randomNext.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MusicController music = MusicController.getInstance();
                if (music.isRandomPlaying()) { // 正在随机播放状态，点击取消
                    randomNext.setIcon(PlayerIcon.randomNext);
                    music.setRandomPlaying(false);
                } else {
                    randomNext.setIcon(PlayerIcon.randomNextSelected);
                    music.setRandomPlaying(true);
                    cycle.setIcon(PlayerIcon.cycle);
                    music.setOnePlaying(false);
                }
            }
        });


        cycle.addMouseListener(new ClickEvent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                MusicController music = MusicController.getInstance();
                if (music.isOnePlaying()) {// 正在循环播放状态，点击取消
                    cycle.setIcon(PlayerIcon.cycle);
                    music.setOnePlaying(false);
                } else {
                    cycle.setIcon(PlayerIcon.cycleSelected);
                    music.setOnePlaying(true);
                    randomNext.setIcon(PlayerIcon.randomNext);
                    music.setRandomPlaying(false);
                }
            }
        });

        voiceSlider = new JSlider(0, 100, 40);
        voiceSlider.setBounds(770, 60, 120, 24);
        voiceSlider.setUI(new SliderUI(voiceSlider));
        voiceSlider.setBackground(PlayerColor.Bottom);
        voiceSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(voiceSlider);
        voiceSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int target = ((JSlider) e.getSource()).getValue();
                while (target < vocality) { //调小音乐
                    SystemUtils.controlSystemVolume(SystemUtils.MINUS);
                    vocality -= 2;
                }
                while (target > vocality) {
                    SystemUtils.controlSystemVolume(SystemUtils.ADD);
                    vocality += 2;
                }
                voiceSlider.setValue(vocality);
            }
        });

        final JButton volume = new PlayerIconButton(PlayerIcon.volume);
        volume.setBounds(740, 60, 20, 20);
        add(volume);

        playProcessSlider = new JSlider(0, 100, 0);
        playProcessSlider.setBounds(220, 60, 310, 24);
        playProcessSlider.setUI(new SliderUI(playProcessSlider));
        playProcessSlider.setBackground(PlayerColor.Bottom);
        playProcessSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(playProcessSlider);

        playProcessSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (MusicController.getInstance().isPlaying()) {
                    double progress = e.getX() / 308.0;
                    double time = MusicController.getInstance().getLength() * progress; // 实际
                    MusicController.getInstance().setProcess(progress, time);
                }
            }
        });

        current = new JLabel("00:00");
        current.setBounds(180, 55, 50, 30);
        current.setForeground(PlayerColor.Default);
        add(current);

        total = new JLabel("00:00");
        total.setBounds(540, 55, 50, 30);
        total.setForeground(PlayerColor.Default);
        add(total);
    }

    public void playBegin() {
        this.play.setIcon(PlayerIcon.pause);
    }

    public void playEnd() {
        this.play.setIcon(PlayerIcon.play);
    }

    //t 当前时间，单位秒
    public void setCurrent(int t) {
        String str = TimeFormat.timeFormat(t);
        this.current.setText(str);
    }

    //设置总时间，单位秒
    public void setTotalLength(int t) {
        String str = TimeFormat.timeFormat(t);
        this.total.setText(str);
    }

    //设置播放进度条
    public void setPlayProcessSlider(int p) {
        this.playProcessSlider.setValue(p);
    }

}
