package Controller;

import View.Bottom.BottomMenuRight;
import View.MainFrame;

public class ProcessController {
    private int totalLength;
    private static ProcessController processController = new ProcessController();
    private MusicProcessThread musicProcessThread;

    public static ProcessController getInstance() {
        return processController;
    }

    //初始化进度条
    public void init() {
        this.totalLength = MusicController.getInstance().getLength();
        this.musicProcessThread = new MusicProcessThread(0, totalLength);
        this.musicProcessThread.start();
        this.musicProcessThread.suspend();
    }

    //暂停播放
    public void pause() {
        this.musicProcessThread.suspend();
    }

    //继续播放
    public void replay() {
        this.musicProcessThread.resume();
    }

    //调整进度，current是确定的时刻（秒）
    public void setProcess(int current) {
        this.musicProcessThread.stop();
        this.musicProcessThread = new MusicProcessThread(current, totalLength);
        this.musicProcessThread.start();
    }

    public void changeMusic(int length) {
        this.musicProcessThread.stop();
        this.totalLength = length;
        this.musicProcessThread = new MusicProcessThread(0, this.totalLength);
        this.musicProcessThread.start();
    }

    //进度条线程
    class MusicProcessThread extends Thread {
        private int current;

        public MusicProcessThread(int now, int time) {
            BottomMenuRight r = (BottomMenuRight) MainFrame.getInstance().getBottom().getRight();
            r.setTotalLength(time);
            this.current = now;
        }

        //执行时，控制当前播放进度的显示条
        @Override
        public void run() {
            BottomMenuRight r = (BottomMenuRight) MainFrame.getInstance().getBottom().getRight();
            while (current <= totalLength) {
                try {
                    r.setCurrent(current);
                    r.setPlayProcessSlider(current * 100 / totalLength);
                    current += 1;
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            r.setCurrent(0);
            r.setPlayProcessSlider(0);
        }
    }
}
