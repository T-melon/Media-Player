package util;

import value.VBScript;

import java.io.*;


/**
 * @author Tian
 */
public class SystemUtils {
    public static final int MUTE = 0;
    public static final int ADD = 1;
    public static final int MINUS = 2;

    public static void controlSystemVolume(int type) {
        String target;
        String wScript = "wScript ";
        switch (type) {
            case MUTE:
                target = wScript + VBScript.Mute;
                break;
            case ADD:
                target = wScript + VBScript.Add;
                break;
            case MINUS:
                target = wScript + VBScript.Minus;
                break;
            default:
                target = "";
        }
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        controlSystemVolume(0);
    }
}
