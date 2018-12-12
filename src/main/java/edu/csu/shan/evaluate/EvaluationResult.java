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

package edu.csu.shan.evaluate;

/**
 * �ִ�Ч���������
 * @author ���д�
 */
public class EvaluationResult implements Comparable{
    /**
     * �ִ���
     */
    private String analyzer;
    /**
     * �ִ��ٶ�
     */
    private float segSpeed;
    /**
     * �ִ��ı�������
     */
    private int totalLineCount;
    /**
     * �ִʽ����ȫ��ȷ������
     */
    private int perfectLineCount;
    /**
     * �ִʽ�����������
     */
    private int wrongLineCount;
    /**
     * �ִ��ı�������
     */
    private int totalCharCount;
    /**
     * �ִʽ����ȫ��ȷ������
     */
    private int perfectCharCount;
    /**
     * �ִʽ�����������
     */
    private int wrongCharCount;

    public String getAnalyzer() {
        return analyzer;
    }
    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }
    public float getSegSpeed() {
        return segSpeed;
    }
    public void setSegSpeed(float segSpeed) {
        this.segSpeed = segSpeed;
    }
    public float getLinePerfectRate(){
        return (int)(perfectLineCount/(float)totalLineCount*10000)/(float)100;
    }
    public float getLineWrongRate(){
        return (int)(wrongLineCount/(float)totalLineCount*10000)/(float)100;
    }
    public float getCharPerfectRate(){
        return (int)(perfectCharCount/(float)totalCharCount*10000)/(float)100;
    }
    public float getCharWrongRate(){
        return (int)(wrongCharCount/(float)totalCharCount*10000)/(float)100;
    }
    public int getTotalLineCount() {
        return totalLineCount;
    }
    public void setTotalLineCount(int totalLineCount) {
        this.totalLineCount = totalLineCount;
    }
    public int getPerfectLineCount() {
        return perfectLineCount;
    }
    public void setPerfectLineCount(int perfectLineCount) {
        this.perfectLineCount = perfectLineCount;
    }
    public int getWrongLineCount() {
        return wrongLineCount;
    }
    public void setWrongLineCount(int wrongLineCount) {
        this.wrongLineCount = wrongLineCount;
    }
    public int getTotalCharCount() {
        return totalCharCount;
    }
    public void setTotalCharCount(int totalCharCount) {
        this.totalCharCount = totalCharCount;
    }
    public int getPerfectCharCount() {
        return perfectCharCount;
    }
    public void setPerfectCharCount(int perfectCharCount) {
        this.perfectCharCount = perfectCharCount;
    }
    public int getWrongCharCount() {
        return wrongCharCount;
    }
    public void setWrongCharCount(int wrongCharCount) {
        this.wrongCharCount = wrongCharCount;
    }
    @Override
    public String toString(){
        if(perfectCharCount==0){
            //ֻ�����ٶ�
            return analyzer+"��"
                    +"\n"
                    +"    �ִ��ٶȣ�"+segSpeed+" �ַ�/����";
        }
        return analyzer+"��"
                +"\n"
                +"    �ִ��ٶȣ�"+segSpeed+" �ַ�/����"
                +"\n"
                +"    ���������ʣ�"+getLinePerfectRate()+"%"
                +"  ���������ʣ�"+getLineWrongRate()+"%"
                +"  �ܵ�������"+totalLineCount
                +"  ����������"+perfectLineCount
                +"  ����������"+wrongLineCount
                +"\n"
                +"    �ִ������ʣ�"+getCharPerfectRate()+"%"
                +" �ִʴ����ʣ�"+getCharWrongRate()+"%"
                +" �ܵ�������"+totalCharCount
                +" ����������"+perfectCharCount
                +" ����������"+wrongCharCount;
    }
    @Override
    public int compareTo(Object o) {
        EvaluationResult other = (EvaluationResult)o;
        if(other.getLinePerfectRate() - getLinePerfectRate() > 0){
            return 1;
        }
        if(other.getLinePerfectRate() - getLinePerfectRate() < 0){
            return -1;
        }
        return 0;
    }
    public static void main(String[] args){
        EvaluationResult r = new EvaluationResult();
        r.setAnalyzer("test");
        r.setSegSpeed(100);
        r.setTotalCharCount(28374428);
        r.setTotalLineCount(2533688);
        r.setPerfectCharCount(7152898);
        r.setWrongCharCount(21221530);
        r.setPerfectLineCount(868440);
        r.setWrongLineCount(1665248);
        System.out.println(r);
    }
}
