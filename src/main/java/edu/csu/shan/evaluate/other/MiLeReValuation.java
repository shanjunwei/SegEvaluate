package edu.csu.shan.evaluate.other;


import edu.csu.shan.evaluate.Evaluation;
import edu.csu.shan.evaluate.EvaluationResult;
import edu.csu.shan.queue.Constans;
import edu.csu.shan.queue.Consumer;
import edu.csu.shan.queue.Producer;
import edu.csu.shan.seg.WordSegmenter;
import edu.csu.shan.util.FileUtil;
import seg.Segment;

import java.util.*;

import static edu.csu.shan.queue.Constans.*;

/**
 * 自己写的 Mi+Le+Re 分词器效果评估
 *
 * @author 单俊维
 */
public class MiLeReValuation extends Evaluation implements WordSegmenter {
    public static final Segment segment = new Segment();

    @Override
    public List<EvaluationResult> run() throws Exception {
        List<EvaluationResult> list = new ArrayList<>();
        run(list, "信息熵互信息分词");
        return list;
    }

    private void run(List<EvaluationResult> list, String type) throws Exception {
        System.out.println("开始评估 信息熵与互信息 分词器-->" + type);
        list.add(run(type));
        Evaluation.generateReport(list, "互信息与左右邻接信息熵分词器分词效果评估报告.txt");
    }

    @Override
    public Map<String, String> segMore(String text) {
        return null;
    }

    private EvaluationResult run(String type) throws Exception {
        String resultText = "temp/result-text-ShanJW-" + type + ".txt";
        //对文本进行分词
/*        float rate = 0;
        rate = segFile(testText, resultText, text -> segment.extractWords(text));*/

        concurrentSegment(resultText);
        //对分词结果进行评估
        EvaluationResult evaluationResult = evaluate(resultText, standardText);
        evaluationResult.setAnalyzer("互信息与左右邻接信息熵分词器 " + type);
        //evaluationResult.setSegSpeed(rate);
        return evaluationResult;
    }

    public static void concurrentSegment(String resultText) {
        Producer.produce("data/test-text.txt");
        long t1 = System.currentTimeMillis();
        Consumer.consumer();
        try {
            countDownLatch.await();
            System.out.println("开的" + THREAD_NUM + "个子线程全部完成");
            System.out.println(Constans.EXTRACT_WORD_MAP.size());
            executorService.shutdown();
            pool.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("并发分词总计耗时:  " + (System.currentTimeMillis() - t1) + " ms");
        //  将map  排序后写入结果
        List<Map.Entry<Integer, String>> list = new ArrayList<>(Constans.EXTRACT_WORD_MAP.entrySet());
        //升序排序
        Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
        FileUtil.writeMapResultToFile(resultText, list);    // 将结果写入文件
    }

    public static void main(String[] args) {
        try {
            new MiLeReValuation().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}