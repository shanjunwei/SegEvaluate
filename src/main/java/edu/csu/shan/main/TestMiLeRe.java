package edu.csu.shan.main;

import edu.csu.shan.evaluate.other.MiLeReValuation;

public class TestMiLeRe {

    public static void main(String[] args) {

        MiLeReValuation evaluation = new MiLeReValuation();
        try {
            evaluation.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
