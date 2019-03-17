package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects;

public class TestStatusItem {
    private String testName;
    private int testImageID;

    public TestStatusItem(String testName, int testImageID) {
        this.testName = testName;
        this.testImageID = testImageID;
    }

    public String getTestName() {
        return testName;
    }

    public int getTestImageID() {
        return testImageID;
    }
}
