package edu.csu.shan.queue;

import edu.csu.shan.pojo.LineMsg;
import util.FileUtils;
import util.HanUtils;
import java.io.*;
import static edu.csu.shan.queue.Constans.CORPUS_INPUT_PATH;

/**
 * 消息生产者
 */
public class Producer {
    public static void produce(String inputPath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), "utf-8"))) {
            String text;
            int lineCount = 1;
            MyBlockingQueue myBlockingQueue = new MyBlockingQueue();
            while ((text = reader.readLine()) != null) {
                text = text.trim();
                LineMsg lineMsg = new LineMsg(lineCount, text);
                myBlockingQueue.produce(lineMsg);
                lineCount++;
            }
            System.out.println(myBlockingQueue.fairQueue.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void produceNovel() {
        MyBlockingQueue myBlockingQueue = new MyBlockingQueue();
        // 读取小说文本
        String novel = FileUtils.readFileToString(CORPUS_INPUT_PATH);
        String[] replaceNonChinese = HanUtils.replaceNonChineseCharacterAsBlank(novel);  // 去掉非中文字符   里边没有逗号
        // 再拆分停用词
        for (int i = 0; i < replaceNonChinese.length; i++) {
            String textDS = replaceNonChinese[i];   // 这里没有逗号
           // if (StringUtils.isNotBlank(textDS) && textDS.length() != 1) {
                LineMsg lineMsg = new LineMsg(i+1, textDS);
                myBlockingQueue.produce(lineMsg);
           // }
        }

        System.out.println(myBlockingQueue.fairQueue.size()  +"==="+replaceNonChinese.length);
    }


    public static void main(String[] args) {
        //Producer  producer  = new Producer();
        Producer.produce("data/test-text.txt");
    }

}
