package value;

import javax.swing.*;

/**
 * @author Tian
 */
public class PlayerIcon {
    private PlayerIcon() {
        throw new IllegalStateException("Utility class");
    }

    private static final String ICON_FILE_ROOT = "src/main/resources/img/";
    public static final ImageIcon LAST = new ImageIcon(ICON_FILE_ROOT + "上一首.png");
    public static final ImageIcon play = new ImageIcon(ICON_FILE_ROOT + "播放.png");
    public static final ImageIcon pause = new ImageIcon(ICON_FILE_ROOT + "暂停.png");
    public static final ImageIcon next = new ImageIcon(ICON_FILE_ROOT + "下一首.png");
    public static final ImageIcon randomNext = new ImageIcon(ICON_FILE_ROOT + "随机播放.png");
    public static final ImageIcon randomNextSelected = new ImageIcon(ICON_FILE_ROOT + "选中随机播放.png");
    public static final ImageIcon cycle = new ImageIcon(ICON_FILE_ROOT + "单曲循环.png");
    public static final ImageIcon cycleSelected = new ImageIcon(ICON_FILE_ROOT + "选中单曲循环.png");
    public static final ImageIcon volume = new ImageIcon(ICON_FILE_ROOT + "音量.png");
    public static final ImageIcon forward = new ImageIcon(ICON_FILE_ROOT + "快进.png");
    public static final ImageIcon backward = new ImageIcon(ICON_FILE_ROOT + "快退.png");
    public static final ImageIcon openSubTitle = new ImageIcon(ICON_FILE_ROOT + "弹幕开.png");
    public static final ImageIcon closeSubTitle = new ImageIcon(ICON_FILE_ROOT + "弹幕关.png");
}
