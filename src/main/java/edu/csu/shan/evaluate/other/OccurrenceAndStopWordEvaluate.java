package edu.csu.shan.evaluate.other;

import edu.csu.shan.evaluate.Evaluation;
import edu.csu.shan.evaluate.EvaluationResult;
import edu.csu.shan.seg.WordSegmenter;
import main.edu.csu.shan.main.SegmentByWordOccurrenceAndStopWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  词共现和停用词 评测
 */
public class OccurrenceAndStopWordEvaluate extends Evaluation implements WordSegmenter {
    public static final SegmentByWordOccurrenceAndStopWord segment = new SegmentByWordOccurrenceAndStopWord();

    @Override
    public List<EvaluationResult> run() throws Exception {
        List<EvaluationResult> list = new ArrayList<>();
        run(list, "词共现集合停用词过滤 分词");
        return list;
    }
    private void run(List<EvaluationResult> list, String type) throws Exception{
        System.out.println("开始评估 词共现集合停用词过滤 分词器  "+type);
        list.add(run(type));
        Evaluation.generateReport(list, "词共现集合停用词过滤 分词器分词效果评估报告.txt");
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
        evaluationResult.setAnalyzer("词共现集合停用词过滤 分词器 " + type);
        evaluationResult.setSegSpeed(rate);
        return evaluationResult;
    }

    public static void main(String[] args) throws Exception{
        new OccurrenceAndStopWordEvaluate().run();
    }
}
