package Util;

import Controller.MusicController;
import Value.MediaDirectory;
import Value.MediaType;


import java.io.File;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class FileList {

    /**
     * 得到文件名
     *
     * @param path      文件路径
     * @param mediaType 文件类型
     * @return 字符串列表
     */
    public static Vector getFileList(String path, String mediaType) {
        File rootDirectory = new File(path);
        String[] fileList = rootDirectory.list();
        Vector list = new Vector();
        assert fileList != null;
        if (mediaType.equals(MediaType.MusicType)) {
            for (String file : fileList)
                if (MediaType.isMusic(file))
                    list.add(file);
        } else {
            for (String file : fileList)
                if (!file.equals("clearSubTitle.ass"))
                    list.add(file);
        }
        return list;
    }

    /**
     * @param flag 小于零得到上一首，大于零得到下一首
     * @return 得到音乐文件名
     */
    public static String getMusicName(int flag) {
        Vector v = getFileList(MediaDirectory.MusicDirectory, MediaType.MusicType);
        int length = v.size();
        String now = MusicController.getInstance().getFileName();
        if (flag < 0) { // 上一首
            for (int i = 1; i < length; i++) {
                if (v.get(i).toString().equals(now))
                    return v.get(i - 1).toString();
            }
            return v.get(length - 1).toString();
        } else { // 下一首
            for (int i = 0; i < length - 1; i++) {
                if (v.get(i).toString().equals(now))
                    return v.get(i + 1).toString();
            }
            return v.get(0).toString();
        }
    }

    //随机一首音乐
    public static String getRandomMusic() {
        Vector v = getFileList(MediaDirectory.MusicDirectory, MediaType.MusicType);
        int length = v.size();
        int index = new Random().nextInt(length);
        return v.get(index).toString();
    }

}
