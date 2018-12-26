/**
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, ���д�, yang-shangchuan@qq.com
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.csu.shan.evaluate;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * ͨ�������߼�
 */
public abstract class Evaluation {
    protected String output = "data/seg_result_new.txt";
    protected String testText = "data/test-text.txt";
    protected String standardText = "data/standard-text.txt";

    public abstract List<EvaluationResult> run() throws Exception;

    public void setTestText(String testText) {
        this.testText = testText;
    }

    public void setStandardText(String standardText) {
        this.standardText = standardText;
    }

    /**
     * ������������
     *
     * @param list
     * @throws IOException
     */
    public static void generateReport(List<EvaluationResult> list) throws IOException {
        generateReport(list, "�ִ�Ч����������.txt");
    }

    /**
     * ������������
     *
     * @param list
     * @param reportName ���汨����ļ�����
     * @throws IOException
     */
    public static void generateReport(List<EvaluationResult> list, String reportName) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        Path report = Paths.get("report/" + reportName);
        if (Files.notExists(report.getParent())) {
            report.getParent().toFile().mkdir();
        }
        List<String> result = new ArrayList<>();
        if (list.get(0).getPerfectLineCount() > 0) {
            result.add("����������������");
            Collections.sort(list);
            result.addAll(toText(list));
        }
        result.add("���ִ��ٶ�����");
        Collections.sort(list, (a, b) -> new Float(b.getSegSpeed()).compareTo(a.getSegSpeed()));
        result.addAll(toText(list));
        Files.write(report, result, Charset.forName("utf-8"));
    }

    private static List<String> toText(List<EvaluationResult> list) {
        List<String> result = new ArrayList<>();
        int i = 1;
        for (EvaluationResult item : list) {
            result.add("");
            result.add("    " + (i++) + "��" + item.toString());
        }
        for (String item : result) {
            System.out.println(item);
        }
        return result;
    }

    /**
     * �ִ�Ч������
     *
     * @param resultText   ʵ�ʷִʽ���ļ�·��
     * @param standardText ��׼�ִʽ���ļ�·��
     * @return �������
     * @throws Exception
     */
    protected EvaluationResult evaluate(String resultText, String standardText) throws Exception {
        if (standardText == null) {
            System.out.println("û��ָ����׼�ı����������ִ��ٶȣ����Էִ�Ч����������");
            return new EvaluationResult();
        }
        int perfectLineCount = 0;
        int wrongLineCount = 0;
        int perfectSegCount = 0;
        int wrongSegCount = 0;   // �����ִʸ��� ȡ��ԭ���Ĵ���ַ�����ԭ����ָ�겻����ϸ
        // int wrongCharCount=0;
        //      int perfectCharCount=0;
        try (BufferedReader resultReader = new BufferedReader(new InputStreamReader(new FileInputStream(resultText), "utf-8"));
             BufferedReader standardReader = new BufferedReader(new InputStreamReader(new FileInputStream(standardText), "utf-8"));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                     new FileOutputStream(output), "utf-8"))) {
            String result;
            while ((result = resultReader.readLine()) != null) {
                result = result.trim();
                String standard = standardReader.readLine().trim();
                if (result.equals("")) {
                    continue;
                }
                writer.write(result +"    XX������>");
                if (result.equals(standard)) {
                    //�ִʽ���ͱ�׼һģһ��
                    perfectLineCount++;
                    perfectSegCount += result.split(" ").length;
                    // perfectCharCount+=standard.replaceAll("\\s+", "").length();
                } else {
                    //�ִʽ���ͱ�׼��һ��
                    Set<String> resultSet = new LinkedHashSet<>(Arrays.asList(result.split(" ")));
                    Set<String> standardSet = new HashSet<>(Arrays.asList(standard.split(" ")));
                    int wrongCount = 0;
                    for (String seg : resultSet) {
                        if (!standardSet.contains(seg)) {
                            wrongCount++;
                            wrongSegCount++;   // ��ָ�����һ
                            writer.write(""+seg+" ");
                            //writer.write(result.replaceAll("\\s+",""));   // �ִʷִ������
                        } else {
                            perfectSegCount++;
                        }
                    }
                    if (wrongCount == 1) {
                        perfectLineCount++;  // ��һ��Ҳ��Ϊ������
                    } else {
                        wrongLineCount++;    //wrongCharCount+=standard.replaceAll("\\s+", "").length();
                    }
                }
                writer.write("\n");
            }
        }
        int totalLineCount = perfectLineCount + wrongLineCount;
        //  int totalCharCount = perfectCharCount+wrongCharCount;
        int totalSegCount = perfectSegCount + wrongSegCount;
        EvaluationResult er = new EvaluationResult();
        er.setPerfectCharCount(perfectSegCount);
        er.setPerfectLineCount(perfectLineCount);
        er.setTotalCharCount(totalSegCount);
        er.setTotalLineCount(totalLineCount);
        er.setWrongCharCount(wrongSegCount);
        er.setWrongLineCount(wrongLineCount);
        return er;
    }
    /**
     * ���ļ����зִ�
     *
     * @param input     �����ļ�
     * @param output    ����ļ�
     * @param segmenter ���ı����зִʵ��߼�
     * @return �ִ�����
     * @throws Exception
     */
    protected float segFile(final String input, final String output, final Segmenter segmenter) throws Exception {
        //����ִʽ���ļ����Ŀ¼�����ڣ��򴴽�
        if (!Files.exists(Paths.get(output).getParent())) {
            Files.createDirectory(Paths.get(output).getParent());
        }
        float rate = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(input), "utf-8"));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                     new FileOutputStream(output), "utf-8"))) {
            long size = Files.size(Paths.get(input));
            System.out.println("size:" + size);
            System.out.println("�ļ���С��" + (float) size / 1024 / 1024 + " MB");
            int textLength = 0;
            int progress = 0;
            long start = System.currentTimeMillis();
            String line = null;
            while ((line = reader.readLine()) != null) {
                if ("".equals(line.trim())) {
                    writer.write("\n");
                    continue;
                }
                try {
                    writer.write(segmenter.seg(line));
                    writer.write("\n");
                    textLength += line.length();
                    progress += line.length();
                    if (progress > 500000) {
                        progress = 0;
                        System.out.println("�ִʽ��ȣ�" + (int) (textLength * 2.99 / size * 100) + "%");
                        if ((int) (textLength * 2.99 / size * 100) > 48) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("�ִ�ʧ�ܣ�" + line);
                    e.printStackTrace();
                }
            }
            long cost = System.currentTimeMillis() - start;
            rate = textLength / (float) cost;
            System.out.println("�ַ���Ŀ��" + textLength);
            System.out.println("�ִʺ�ʱ��" + cost + " ����");
            System.out.println("�ִ��ٶȣ�" + rate + " �ַ�/����");
        }
        return rate;
    }

    public static String getTimeDes(Long ms) {
        //�������ΪNULL�����
        if (ms == null) {
            return "";
        }
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuilder str = new StringBuilder();
        if (day > 0) {
            str.append(day).append("��,");
        }
        if (hour > 0) {
            str.append(hour).append("Сʱ,");
        }
        if (minute > 0) {
            str.append(minute).append("����,");
        }
        if (second > 0) {
            str.append(second).append("��,");
        }
        if (milliSecond > 0) {
            str.append(milliSecond).append("����,");
        }
        if (str.length() > 0) {
            str = str.deleteCharAt(str.length() - 1);
        }

        return str.toString();
    }
}
