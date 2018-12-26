/*
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, ���д�, yang-shangchuan@qq.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.csu.shan.evaluate.other;

import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import edu.csu.shan.evaluate.Evaluation;
import edu.csu.shan.evaluate.EvaluationResult;
import edu.csu.shan.seg.WordSegmenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HanLP�ִ����ִ�Ч������
 * @author ���д�
 */
public class HanLPEvaluation extends Evaluation implements WordSegmenter {
    private static final Segment N_SHORT_SEGMENT = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
    private static final Segment DIJKSTRA_SEGMENT = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);

    @Override
    public List<EvaluationResult> run() throws Exception {
        List<EvaluationResult> list = new ArrayList<>();

       // run(list, "��׼�ִ�");
/*        run(list, "NLP�ִ�");
        run(list, "�����ִ�");
        run(list, "N-���·���ִ�");
        run(list, "���·���ִ�");
        */
        run(list, "���ٴʵ�ִ�");
        return list;
    }
    private void run(List<EvaluationResult> list, String type) throws Exception{
        System.out.println("��ʼ���� HanLP�ִ���  "+type);
        list.add(run(type));
        Evaluation.generateReport(list, "HanLP�ִ����ִ�Ч����������.txt");
    }
    private EvaluationResult run(String type) throws Exception{
        //���ı����зִ�
        String resultText = "temp/result-text-HanLP-"+type+".txt";
        float rate = 0;
        switch (type){
            case "��׼�ִ�":rate = segFile(testText, resultText, text -> HanLPEvaluation.standard(text));break;
            case "NLP�ִ�":rate = segFile(testText, resultText, text -> HanLPEvaluation.nlp(text));break;
            case "�����ִ�":rate = segFile(testText, resultText, text -> HanLPEvaluation.index(text));break;
            case "N-���·���ִ�":rate = segFile(testText, resultText, text -> HanLPEvaluation.nShort(text));break;
            case "���·���ִ�":rate = segFile(testText, resultText, text -> HanLPEvaluation.shortest(text));break;
            case "���ٴʵ�ִ�":rate = segFile(testText, resultText, text -> HanLPEvaluation.speed(text));break;
        }
        //�Էִʽ����������
        EvaluationResult evaluationResult = evaluate(resultText, standardText);
        evaluationResult.setAnalyzer("HanLP�ִ��� " + type);
        evaluationResult.setSegSpeed(rate);
        return evaluationResult;
    }
    @Override
    public Map<String, String> segMore(String text) {
        Map<String, String> map = new HashMap<>();
        map.put("��׼�ִ�", standard(text));
        map.put("NLP�ִ�", nlp(text));
        map.put("�����ִ�", index(text));
        map.put("N-���·���ִ�", nShort(text));
        map.put("���·���ִ�", shortest(text));
        map.put("���ٴʵ�ִ�", speed(text));
        return map;
    }
    private static String standard(String text) {
        StringBuilder result = new StringBuilder();
        StandardTokenizer.segment(text).forEach(term->result.append(term.word).append(" "));
        return result.toString();
    }
    private static String nlp(String text) {
        StringBuilder result = new StringBuilder();
        NLPTokenizer.segment(text).forEach(term->result.append(term.word).append(" "));
        return result.toString();
    }
    private static String index(String text) {
        StringBuilder result = new StringBuilder();
        IndexTokenizer.segment(text).forEach(term->result.append(term.word).append(" "));
        return result.toString();
    }
    private static String speed(String text) {
        StringBuilder result = new StringBuilder();
        SpeedTokenizer.segment(text).forEach(term->result.append(term.word).append(" "));
        return result.toString();
    }
    private static String nShort(String text) {
        StringBuilder result = new StringBuilder();
        N_SHORT_SEGMENT.seg(text).forEach(term->result.append(term.word).append(" "));
        return result.toString();
    }
    private static String shortest(String text) {
        StringBuilder result = new StringBuilder();
        DIJKSTRA_SEGMENT.seg(text).forEach(term->result.append(term.word).append(" "));
        return result.toString();
    }

//    public static void main(String[] args) throws Exception{
//        new HanLPEvaluation().run();
//    }
}