package util;

/**
 * @author Tian
 */
public class TimeFormat {

    private TimeFormat() {
        throw new IllegalStateException("Utility class");
    }

    public static String timeFormat(int time) {
        String string;
        int minutes = time / 60;
        int limit = 10;
        if (minutes < limit) {
            string = "0" + minutes + ":";
        } else {
            string = "" + minutes + ":";
        }
        int seconds = time % 60;
        if (seconds < limit) {
            string = string + "0" + seconds;
        } else {
            string = string + seconds;
        }
        return string;
    }

}
