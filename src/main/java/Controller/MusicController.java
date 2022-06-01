package Controller;

import util.FileList;
import value.MediaDirectory;
import value.MediaType;
import View.Bottom.BottomMenuLeft;
import View.Bottom.BottomMenuRight;
import View.MainFrame;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import java.io.*;
import java.util.Objects;
import java.util.Vector;

public class MusicController {

    private boolean isPlaying = false; //正在播放
    private boolean isRandomPlaying = false; //随机播放
    private boolean isOnePlaying = false; //单曲循环
    private String fileName = ""; //当前处理的文件名
    private byte[] musicBytes; //音乐文件流字节
    private BufferedInputStream bufferedInputStream;//音乐缓冲流，播放流
    private PlayMusicThread playMusicThread; //播放线程对象
    private EndThread endThread;//播放结束线程
    private int length; //播放歌曲的长度

    private static MusicController musicController = new MusicController();

    public static MusicController getInstance() {
        return musicController;
    }

    //启动时初始化，显示第一首歌
    public void init() throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        Vector v = FileList.getFileList(MediaDirectory.MusicDirectory, MediaType.MusicType);
        String path = MediaDirectory.MusicDirectory + v.get(0);
        this.endThread = new EndThread(); //监听事件结束
        this.endThread.start();
        prepare(path); //初始化文件流
        this.playMusicThread = new PlayMusicThread(this.bufferedInputStream);
        this.playMusicThread.start();
        this.playMusicThread.suspend();

        ProcessController.getInstance().init();
        LyricsController.getInstance().init();
    }

    //播放歌曲之前预先做好：准备好播放流、设置左下方标题
    private void prepare(String path) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        this.fileName = path.substring(path.lastIndexOf('/') + 1);
        String musicName = this.fileName.substring(0, this.fileName.lastIndexOf('.'));
        BottomMenuLeft left = (BottomMenuLeft) MainFrame.getInstance().getBottom().getLeft();
        left.setTitle(musicName);
        this.length = mp3Length(path);
        File mp3 = new File(path);
        FileInputStream fileInputStream = new FileInputStream(mp3);//音乐文件流
        this.musicBytes = streamToByte(fileInputStream); //音乐字节数组
        this.bufferedInputStream = new BufferedInputStream(Objects.requireNonNull(byteToStream(this.musicBytes)));
    }

    //从头开始播放歌曲，可以认为是新的歌曲
    public void playMusic(String path) throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        this.playMusicThread.stop();
        prepare(path);
        this.isPlaying = true;
        this.playMusicThread = new PlayMusicThread(this.bufferedInputStream);
        this.playMusicThread.start();

        ProcessController.getInstance().changeMusic(this.length);
        LyricsController.getInstance().changeMusic(this.fileName.substring(0, this.fileName.lastIndexOf('.')));
    }

    //根据文件路径得到mp3音乐播放时长
    private int mp3Length(String path) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        File file = new File(path);
        AudioFile mp3 = AudioFileIO.read(file);
        return mp3.getAudioHeader().getTrackLength();
    }

    //获得当前音乐时长
    public int getLength() {
        return this.length;
    }

    public String getFileName() {
        return fileName;
    }

    //设置进度，percent是百分比
    public void setProcess(double percent, double time) {
        byte[] bytes = this.musicBytes;
        int index = (int) (bytes.length * percent);
        byte[] b = new byte[(int) (bytes.length * (1 - percent))];
        System.arraycopy(bytes, index, b, 0, b.length);
        InputStream bs = new ByteArrayInputStream(b);
        this.bufferedInputStream = new BufferedInputStream(bs);
        this.playMusicThread.stop();//重新设置设置进度后，将线程销毁，重新创建线程
        this.playMusicThread = new PlayMusicThread(this.bufferedInputStream);
        if (isPlaying) {
            this.playMusicThread.start();
        }

        ProcessController.getInstance().setProcess((int) time);
        LyricsController.getInstance().setProcess((long) (time * 1000));
    }

    //暂停播放
    public void pause() {
        this.isPlaying = false;
        this.playMusicThread.suspend();

        ProcessController.getInstance().pause();
        LyricsController.getInstance().pause();
    }

    //继续播放
    public void replay() {
        this.isPlaying = true;
        this.playMusicThread.resume();

        ProcessController.getInstance().replay();
        LyricsController.getInstance().replay();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public boolean isRandomPlaying() {
        return isRandomPlaying;
    }

    public void setRandomPlaying(boolean flag) {
        isRandomPlaying = flag;
    }

    public boolean isOnePlaying() {
        return isOnePlaying;
    }

    public void setOnePlaying(boolean flag) {
        isOnePlaying = flag;
    }

    public void playLastMusic() throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        String name = FileList.getMusicName(-1);
        playMusic(MediaDirectory.MusicDirectory + name);
        ProcessController.getInstance().changeMusic(this.length);
        LyricsController.getInstance().changeMusic(this.fileName.substring(0, this.fileName.lastIndexOf('.')));
    }

    public void playNextMusic() throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
        String name = FileList.getMusicName(1);
        playMusic(MediaDirectory.MusicDirectory + name);
        ProcessController.getInstance().changeMusic(this.length);
        LyricsController.getInstance().changeMusic(this.fileName.substring(0, this.fileName.lastIndexOf('.')));
    }

    private void playEnd() {
        isPlaying = false;
        try {
            if (isOnePlaying) {
                playMusic(MediaDirectory.MusicDirectory + getFileName());
                ProcessController.getInstance().changeMusic(this.length);
                LyricsController.getInstance().changeMusic(this.fileName.substring(0, this.fileName.lastIndexOf('.')));
            } else if (isRandomPlaying) {
                playMusic(MediaDirectory.MusicDirectory + FileList.getRandomMusic());
                ProcessController.getInstance().changeMusic(this.length);
                LyricsController.getInstance().changeMusic(this.fileName.substring(0, this.fileName.lastIndexOf('.')));
            } else {
                playMusic(MediaDirectory.MusicDirectory + getFileName());
                ProcessController.getInstance().changeMusic(this.length);
                LyricsController.getInstance().changeMusic(this.fileName.substring(0, this.fileName.lastIndexOf('.')));
                pause();
                ProcessController.getInstance().pause();
                LyricsController.getInstance().pause();
                BottomMenuRight right = (BottomMenuRight) MainFrame.getInstance().getBottom().getRight();
                right.playEnd(); //切换显示图标
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] streamToByte(FileInputStream fs) throws IOException {
        BufferedInputStream all = new BufferedInputStream(fs);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 50];
        int n;
        while ((n = all.read(buffer)) != -1)
            output.write(buffer, 0, n);
        return output.toByteArray();
    }

    private FileInputStream byteToStream(byte[] bytes) {
        File file = new File("src/main/resources/bin/cache");
        FileInputStream fileInputStream;
        try {
            OutputStream output = new FileOutputStream(file);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(bytes);
            fileInputStream = new FileInputStream(file);
            file.deleteOnExit();
            return fileInputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //播放歌曲线程
    class PlayMusicThread extends Thread {
        private BufferedInputStream bis;

        public PlayMusicThread(BufferedInputStream bufferedInputStream) {
            this.bis = bufferedInputStream;
        }

        @Override
        public void run() {
            try {
                Player player = new Player(this.bis);
                player.play();
                endThread.resume();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    //等待线程
    class EndThread extends Thread {

        @Override
        public void run() {
            while (true) {
                suspend();
                try {
                    Thread.sleep(1000);
                    playEnd();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


