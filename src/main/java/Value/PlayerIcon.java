package Value;

import javax.swing.*;

public class PlayerIcon {
    private static final String iconFileRoot = "src/main/resources/img/";
    public static final ImageIcon last = new ImageIcon(iconFileRoot + "上一首.png");
    public static final ImageIcon play = new ImageIcon(iconFileRoot + "播放.png");
    public static final ImageIcon pause = new ImageIcon(iconFileRoot + "暂停.png");
    public static final ImageIcon next = new ImageIcon(iconFileRoot + "下一首.png");
    public static final ImageIcon randomNext = new ImageIcon(iconFileRoot + "随机播放.png");
    public static final ImageIcon randomNextSelected = new ImageIcon(iconFileRoot + "选中随机播放.png");
    public static final ImageIcon cycle = new ImageIcon(iconFileRoot + "单曲循环.png");
    public static final ImageIcon cycleSelected = new ImageIcon(iconFileRoot + "选中单曲循环.png");
    public static final ImageIcon volume = new ImageIcon(iconFileRoot + "音量.png");
    public static final ImageIcon forward = new ImageIcon(iconFileRoot + "快进.png");
    public static final ImageIcon backward = new ImageIcon(iconFileRoot + "快退.png");
    public static final ImageIcon openSubTitle = new ImageIcon(iconFileRoot + "弹幕开.png");
    public static final ImageIcon closeSubTitle = new ImageIcon(iconFileRoot + "弹幕关.png");
}
