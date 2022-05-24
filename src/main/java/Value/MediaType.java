package Value;

public class MediaType {
    public static final String MusicType = "mp3";
    public static final String LyricsType = "lrc";
    public static final String VideoType = "mp4";

    public static boolean isMusic(String file) {
        return file.endsWith(".mp3");
    }

    public static boolean isVideo(String file) {
        return file.endsWith(".mp4") || file.endsWith(".flv");
    }
}
