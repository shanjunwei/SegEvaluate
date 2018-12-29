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
 * �Լ�д�� Mi+Le+Re �ִ���Ч������
 *
 * @author ����ά
 */
public class MiLeReValuation extends Evaluation implements WordSegmenter {
    public static final Segment segment = new Segment();

    @Override
    public List<EvaluationResult> run() throws Exception {
        List<EvaluationResult> list = new ArrayList<>();
        run(list, "��Ϣ�ػ���Ϣ�ִ�");
        return list;
    }

    private void run(List<EvaluationResult> list, String type) throws Exception {
        System.out.println("��ʼ���� ��Ϣ���뻥��Ϣ �ִ���-->" + type);
        list.add(run(type));
        Evaluation.generateReport(list, "����Ϣ�������ڽ���Ϣ�طִ����ִ�Ч����������.txt");
    }

    @Override
    public Map<String, String> segMore(String text) {
        return null;
    }

    private EvaluationResult run(String type) throws Exception {
        String resultText = "temp/result-text-ShanJW-" + type + ".txt";
        //���ı����зִ�
/*        float rate = 0;
        rate = segFile(testText, resultText, text -> segment.extractWords(text));*/

        concurrentSegment(resultText);
        //�Էִʽ����������
        EvaluationResult evaluationResult = evaluate(resultText, standardText);
        evaluationResult.setAnalyzer("����Ϣ�������ڽ���Ϣ�طִ��� " + type);
        //evaluationResult.setSegSpeed(rate);
        return evaluationResult;
    }

    public static void concurrentSegment(String resultText) {
        Producer.produce("data/test-text.txt");
        long t1 = System.currentTimeMillis();
        Consumer.consumer();
        try {
            countDownLatch.await();
            System.out.println("����" + THREAD_NUM + "�����߳�ȫ�����");
            System.out.println(Constans.EXTRACT_WORD_MAP.size());
            executorService.shutdown();
            pool.destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("�����ִ��ܼƺ�ʱ:  " + (System.currentTimeMillis() - t1) + " ms");
        //  ��map  �����д����
        List<Map.Entry<Integer, String>> list = new ArrayList<>(Constans.EXTRACT_WORD_MAP.entrySet());
        //��������
        Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
        FileUtil.writeMapResultToFile(resultText, list);    // �����д���ļ�
    }

    public static void main(String[] args) {
        try {
            new MiLeReValuation().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}