package edu.csu.shan.evaluate.other;


import edu.csu.shan.evaluate.Evaluation;
import edu.csu.shan.evaluate.EvaluationResult;
import edu.csu.shan.seg.WordSegmenter;
import seg.Segment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  自己写的 Mi+Le+Re 分词器效果评估
 * @author  单俊维
 */
public class MiLeReValuation extends Evaluation implements WordSegmenter {
    public static final Segment segment  = new Segment();

    @Override
    public List<EvaluationResult> run() throws Exception {
        List<EvaluationResult> list = new ArrayList<>();
        run(list, "信息熵互信息分词");
        return list;
    }
    private void run(List<EvaluationResult> list, String type) throws Exception{
        System.out.println("开始评估 HanLP分词器  "+type);
        list.add(run(type));
        Evaluation.generateReport(list, "互信息与左右邻接信息熵分词器分词效果评估报告.txt");
    }

    @Override
    public Map<String, String> segMore(String text) {
        return null;
    }

    private EvaluationResult run(String type) throws Exception{
        //对文本进行分词
        String resultText = "temp/result-text-ShanJW-"+type+".txt";
        float rate = 0;
        rate = segFile(testText, resultText, text -> segment.segmentToString(text));
        //对分词结果进行评估
        EvaluationResult evaluationResult = evaluate(resultText, standardText);
        evaluationResult.setAnalyzer("互信息与左右邻接信息熵分词器 " + type);
        evaluationResult.setSegSpeed(rate);
        return evaluationResult;
    }

    public static void main(String[] args) throws Exception{
        new MiLeReValuation().run();
    }
}