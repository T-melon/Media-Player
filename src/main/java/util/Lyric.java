package util;

/**
 * @author Tian
 */
public class Lyric {
    private final Long showTime;
    private final String content;

    public Lyric(long time, String content) {
        this.content = content;
        this.showTime = time;
    }

    public long getShowTime() {
        return this.showTime;
    }

    public String getContent() {
        return this.content;
    }
}
