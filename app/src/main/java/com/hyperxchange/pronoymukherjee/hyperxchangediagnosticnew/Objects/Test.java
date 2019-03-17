package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects;

public class Test {
    private String testName;
    private int score;
    private int testIconID;

    public Test(String testName, int score, int testIconID) {
        this.testName = testName;
        this.score = score;
        this.testIconID = testIconID;
    }

    public String getTestName() {
        return testName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTestIconID() {
        return testIconID;
    }
}
