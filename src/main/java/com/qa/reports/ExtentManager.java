package com.qa.reports;

import com.relevantcodes.extentreports.ExtentTest;

public class ExtentManager {
    public static final ThreadLocal<ExtentTest> exTest = new ThreadLocal<ExtentTest>();

    private ExtentManager() {
    }

    public static ExtentTest getExtTest() {
        return exTest.get();
    }

    public static void setExtentTest(ExtentTest test) {
        exTest.set(test);
    }
}
