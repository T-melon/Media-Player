package Util;

import Value.VBScript;

import java.io.*;


public class SystemUtils {
    public static final int MUTE = 0;
    public static final int ADD = 1;
    public static final int MINUS = 2;

    public static void controlSystemVolume(int type) {
        String target;
        switch (type) {
            case MUTE:
                target = "wscript " + VBScript.Mute;
                break;
            case ADD:
                target = "wscript " + VBScript.Add;
                break;
            case MINUS:
                target = "wscript " + VBScript.Minus;
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
