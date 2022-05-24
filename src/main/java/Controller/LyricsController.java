package Controller;

import Util.LRC;
import Util.LRCHandler;
import Value.MediaDirectory;
import Value.MediaType;
import View.Bottom.BottomMenuLeft;
import View.MainFrame;

import java.util.List;


public class LyricsController {
    private List<LRC> lyricsList;
    private static LyricsController lyricsController = new LyricsController();
    private LyricsThread lyricsThread;
    private boolean isValid = false;

    public static LyricsController getInstance() {
        return lyricsController;
    }

    public void init() {
        BottomMenuLeft left = (BottomMenuLeft) MainFrame.getInstance().getBottom().getLeft();
        String title = left.getTitle();
        prepare(title);
        MainFrame.getInstance().getLyricsPanel().setLyricsContent("", "", title, "", "");
        lyricsThread = new LyricsThread(0);
        lyricsThread.start();
        lyricsThread.suspend();
    }

    private void prepare(String name) {
        String path = MediaDirectory.LyricsDirectory + name + '.' + MediaType.LyricsType;
        this.lyricsList = LRCHandler.parse(path);
        this.isValid = this.lyricsList != null && this.lyricsList.size() != 0;
    }

    public void replay() {
        if (this.isValid)
            this.lyricsThread.resume();
    }

    public void pause() {
        if (this.isValid)
            this.lyricsThread.suspend();
    }

    public void setProcess(long time) {
        if (this.isValid) {
            this.lyricsThread.stop();
            this.lyricsThread = new LyricsThread(time);
            lyricsThread.start();
        }
    }

    public void changeMusic(String name) {
        this.lyricsThread.stop();
        MainFrame.getInstance().getLyricsPanel().setLyricsContent("", "", "", "", "");
        prepare(name);
        lyricsThread = new LyricsThread(0);
        lyricsThread.start();
    }


    //进度条线程
    class LyricsThread extends Thread {

        private long start;

        //规定起始时刻
        private LyricsThread(long start) {
            this.start = start;
        }

        //执行时，显示歌词
        @Override
        public void run() {
            if (!isValid) {
                MainFrame.getInstance().getLyricsPanel().setLyricsContent(" ", " ", "暂无歌词", " ", " ");
            } else {
                int num = lyricsList.size();
                int i = 0;
                while (i < num && lyricsList.get(i).getshowTime() <= this.start)
                    i++;
                String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
                if (i - 3 >= 0)
                    s1 = lyricsList.get(i - 3).getContent();
                if (i - 2 >= 0)
                    s2 = lyricsList.get(i - 2).getContent();
                if (i - 1 >= 0)
                    s3 = lyricsList.get(i - 1).getContent();
                if (i < num)
                    s4 = lyricsList.get(i).getContent();
                if (i + 1 < num)
                    s5 = lyricsList.get(i + 1).getContent();
                MainFrame.getInstance().getLyricsPanel().setLyricsContent(s1, s2, s3, s4, s5);
                for (; i < num; i++) {
                    long sleepTime = lyricsList.get(i).getshowTime() - this.start;
                    try {
                        Thread.sleep(sleepTime);
                        this.start = lyricsList.get(i).getshowTime();
                        s1 = s2;
                        s2 = s3;
                        s3 = s4;
                        s4 = i + 1 < num ? lyricsList.get(i + 1).getContent() : "";
                        s5 = i + 2 < num ? lyricsList.get(i + 2).getContent() : "";
                        MainFrame.getInstance().getLyricsPanel().setLyricsContent(s1, s2, s3, s4, s5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
