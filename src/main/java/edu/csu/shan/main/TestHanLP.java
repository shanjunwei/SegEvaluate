package edu.csu.shan.main;

import edu.csu.shan.evaluate.other.HanLPEvaluation;

/**
 *   ����HanLP �ķ���Ч��
 */
public class TestHanLP {

    public static void main(String[] args) {
        HanLPEvaluation  evaluation = new HanLPEvaluation();

        try {
            evaluation.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
