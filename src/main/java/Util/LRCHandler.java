package Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LRCHandler {
    //根据路径解析LRC歌词文件
    public static List<LRC> parse(String path) {
        List<LRC> list = new ArrayList<>();// 存储所有歌词信息的容器
        try {
            String encoding = "utf-8";
            File file = new File(path);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String regex = "\\[(\\d{1,2}):(\\d{1,2}).(\\d+)\\]"; // 正则表达式
                Pattern pattern = Pattern.compile(regex); // 创建 Pattern 对象
                String lineStr; // 每次读取一行字符串
                while ((lineStr = bufferedReader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(lineStr);
                    while (matcher.find()) {
                        String min = matcher.group(1); // 分
                        String sec = matcher.group(2); // 秒
                        String ms = matcher.group(3); // 秒
                        if (ms.length() == 2) ms = ms + "0";
                        long time = Long.parseLong(min) * 60 * 1000 + Long.parseLong(sec) * 1000 + Long.parseLong(ms);
                        String text = lineStr.substring(matcher.end());
                        if (text.length() > 0)
                            list.add(new LRC(time, text));// 添加到容器中
                    }
                }
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[]args){
//        parse("src/main/resources/lrc/与火星的孩子对话.lrc");
//    }
}
