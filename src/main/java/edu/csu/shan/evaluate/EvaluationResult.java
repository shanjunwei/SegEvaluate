/**
 * 
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
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
 * 分词效果评估结果
 * @author 杨尚川
 */
public class EvaluationResult implements Comparable{
    /**
     * 分词器
     */
    private String analyzer;
    /**
     * 分词速度
     */
    private float segSpeed;
    /**
     * 分词文本总行数
     */
    private int totalLineCount;
    /**
     * 分词结果完全正确的行数
     */
    private int perfectLineCount;
    /**
     * 分词结果错误的行数
     */
    private int wrongLineCount;
    /**
     * 分词文本总字数
     */
    private int totalCharCount;
    /**
     * 分词结果完全正确的字数
     */
    private int perfectCharCount;
    /**
     * 分词结果错误的字数
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
            //只评估速度
            return analyzer+"："
                    +"\n"
                    +"    分词速度："+segSpeed+" 字符/毫秒";
        }
        return analyzer+"："
                +"\n"
                +"    分词速度："+segSpeed+" 字符/毫秒"
                +"\n"
             //   +"    行数完美率："+getLinePerfectRate()+"%"
             //   +"  行数错误率："+getLineWrongRate()+"%"
            //    +"  总的行数："+totalLineCount
            //    +"  完美行数："+perfectLineCount
            //    +"  错误行数："+wrongLineCount
                +"\n"
                +"    抽词完美率："+getCharPerfectRate()+"%"
                +" 抽词错误率："+getCharWrongRate()+"%"
                +" 总的词数："+totalCharCount
                +" 完美词数："+perfectCharCount
                +" 错误词数："+wrongCharCount;
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
