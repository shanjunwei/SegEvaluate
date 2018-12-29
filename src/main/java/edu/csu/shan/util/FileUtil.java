package edu.csu.shan.util;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 文件读写工具类
 */
public class FileUtil {
    public static void writeMapResultToFile(String outputPath, List<Map.Entry<Integer, String>> list) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "utf-8"))) {
            for (Map.Entry<Integer, String> mapping : list) {
                writer.write(mapping.getValue() + "\n");
                //System.out.println(mapping.getKey()+":"+mapping.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
