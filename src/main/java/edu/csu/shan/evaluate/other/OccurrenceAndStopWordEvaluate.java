package edu.csu.shan.evaluate.other;

import edu.csu.shan.evaluate.Evaluation;
import edu.csu.shan.evaluate.EvaluationResult;
import edu.csu.shan.seg.WordSegmenter;
import main.edu.csu.shan.main.SegmentByWordOccurrenceAndStopWord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  �ʹ��ֺ�ͣ�ô� ����
 */
public class OccurrenceAndStopWordEvaluate extends Evaluation implements WordSegmenter {
    public static final SegmentByWordOccurrenceAndStopWord segment = new SegmentByWordOccurrenceAndStopWord();

    @Override
    public List<EvaluationResult> run() throws Exception {
        List<EvaluationResult> list = new ArrayList<>();
        run(list, "�ʹ��ּ���ͣ�ôʹ��� �ִ�");
        return list;
    }
    private void run(List<EvaluationResult> list, String type) throws Exception{
        System.out.println("��ʼ���� �ʹ��ּ���ͣ�ôʹ��� �ִ���  "+type);
        list.add(run(type));
        Evaluation.generateReport(list, "�ʹ��ּ���ͣ�ôʹ��� �ִ����ִ�Ч����������.txt");
    }

    @Override
    public Map<String, String> segMore(String text) {
        return null;
    }

    private EvaluationResult run(String type) throws Exception{
        //���ı����зִ�
        String resultText = "temp/result-text-ShanJW-"+type+".txt";
        float rate = 0;
        rate = segFile(testText, resultText, text -> segment.segmentToString(text));
        //�Էִʽ����������
        EvaluationResult evaluationResult = evaluate(resultText, standardText);
        evaluationResult.setAnalyzer("�ʹ��ּ���ͣ�ôʹ��� �ִ��� " + type);
        evaluationResult.setSegSpeed(rate);
        return evaluationResult;
    }

    public static void main(String[] args) throws Exception{
        new OccurrenceAndStopWordEvaluate().run();
    }
}
