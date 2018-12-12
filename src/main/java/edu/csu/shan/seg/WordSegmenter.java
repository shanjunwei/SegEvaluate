/**
 * 
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, ���д�, yang-shangchuan@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package edu.csu.shan.seg;

import edu.csu.shan.evaluate.other.HanLPEvaluation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * ��ȡ�ı������зִʽ��, �ԱȲ�ͬ�ִ������
 * seg��segMore�����������������ڷ���ֵ
 * ÿһ���ִ����������ж��ִַ�ģʽ��ÿ��ģʽ�ķִʽ�������ܲ���ͬ
 * ��һ���������Էִ���ģʽ����������ģʽ�����в��ظ��ִʽ��
 * �ڶ�����������ÿһ�ִַ���ģʽ�����Ӧ�ķִʽ��
 * @author ���д�
 */
public interface WordSegmenter {
    /**
     * ��ȡ�ı������зִʽ��
     * @param text �ı�
     * @return ���еķִʽ����ȥ���ظ�
     */
    default public Set<String> seg(String text) {
        return segMore(text).values().stream().collect(Collectors.toSet());
    }
    /**
     * ��ȡ�ı������зִʽ��
     * @param text �ı�
     * @return ���еķִʽ����KEY Ϊ�ִ���ģʽ��VALUE Ϊ�ִ������
     */
    public Map<String, String> segMore(String text);
    
    public static Map<String, Set<String>> contrast(String text){
        Map<String, Set<String>> map = new LinkedHashMap<>();
        map.put("HanLP�ִ���", new HanLPEvaluation().seg(text));

/*        map.put("word�ִ���", new WordEvaluation().seg(text));
        map.put("Stanford�ִ���", new StanfordEvaluation().seg(text));
        map.put("Ansj�ִ���", new AnsjEvaluation().seg(text));
        map.put("smartcn�ִ���", new SmartCNEvaluation().seg(text));
        map.put("FudanNLP�ִ���", new FudanNLPEvaluation().seg(text));
        map.put("Jieba�ִ���", new JiebaEvaluation().seg(text));
        map.put("Jcseg�ִ���", new JcsegEvaluation().seg(text));
        map.put("MMSeg4j�ִ���", new MMSeg4jEvaluation().seg(text));
        map.put("IKAnalyzer�ִ���", new IKAnalyzerEvaluation().seg(text));*/
        return map;
    }
    public static Map<String, Map<String, String>> contrastMore(String text){
        Map<String, Map<String, String>> map = new LinkedHashMap<>();
/*        map.put("word�ִ���", new WordEvaluation().segMore(text));
        map.put("Stanford�ִ���", new StanfordEvaluation().segMore(text));
        map.put("Ansj�ִ���", new AnsjEvaluation().segMore(text));*/
        map.put("HanLP�ִ���", new HanLPEvaluation().segMore(text));
/*        map.put("smartcn�ִ���", new SmartCNEvaluation().segMore(text));
        map.put("FudanNLP�ִ���", new FudanNLPEvaluation().segMore(text));
        map.put("Jieba�ִ���", new JiebaEvaluation().segMore(text));
        map.put("Jcseg�ִ���", new JcsegEvaluation().segMore(text));
        map.put("MMSeg4j�ִ���", new MMSeg4jEvaluation().segMore(text));
        map.put("IKAnalyzer�ִ���", new IKAnalyzerEvaluation().segMore(text));*/
        return map;
    }
    public static void show(Map<String, Set<String>> map){
        map.keySet().forEach(k -> {
            System.out.println(k + " �ķִʽ����");
            AtomicInteger i = new AtomicInteger();
            map.get(k).forEach(v -> {
                System.out.println("\t" + i.incrementAndGet() + " ��" + v);
            });
        });
    }
    public static void showMore(Map<String, Map<String, String>> map){
        map.keySet().forEach(k->{
            System.out.println(k + " �ķִʽ����");
            AtomicInteger i = new AtomicInteger();
            map.get(k).keySet().forEach(a -> {
                System.out.println("\t" + i.incrementAndGet()+ " ����"   + a + "��\t" + map.get(k).get(a));
            });
        });
    }
    public static void run(String encoding) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding))){
            String line = null;
            while((line = reader.readLine()) != null){
                if("exit".equals(line)){
                    System.exit(0);
                    return;
                }
                if(line.trim().equals("")){
                    continue;
                }
                process(line);
                showUsage();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void process(String text){
        System.out.println("********************************************");
        show(contrast(text));
        System.out.println("********************************************");
        showMore(contrastMore(text));
        System.out.println("********************************************");
    }
    public static void showUsage(){
        System.out.println("����exit�˳�����");
        System.out.println("����Ҫ�ִʵ��ı���س�ȷ�ϣ�");
    }
    public static void main(String[] args) {
        process("�Ұ�����İ");
        process("��ϳɷ���");
        String encoding = "utf-8";
        if(args==null || args.length == 0){
            showUsage();
            run(encoding);
        }else if(Charset.isSupported(args[0])){
            showUsage();
            run(args[0]);
        }
    }
}
