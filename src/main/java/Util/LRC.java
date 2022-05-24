package Util;

public class LRC {
    private Long showTime;
    private String content;

    public LRC(long time, String content) {
        this.content = content;
        this.showTime = time;
    }

    public long getshowTime() {
        return this.showTime;
    }

    public String getContent() {
        return this.content;
    }
}
