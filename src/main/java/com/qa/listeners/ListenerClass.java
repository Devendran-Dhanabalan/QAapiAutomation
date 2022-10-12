package com.qa.listeners;

import com.qa.reports.ExtentManager;
import com.qa.reports.ExtentReport;
import com.qa.reports.LogStatus;
import com.google.common.base.Throwables;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerClass implements ITestListener {
    private static String TestcaseName;
    private static String TestcaseDescription;

    public static void setTestcaseDescription(String testcaseDescription) {
        TestcaseDescription = testcaseDescription;
    }

    public static void setTestcaseName(String testcaseName) {
        TestcaseName = testcaseName;
    }

    public void onTestStart(ITestResult result) {
        TestcaseDescription = result.getMethod().getDescription();
        TestcaseName = "<em>" + result.getTestClass().getRealClass().getSimpleName() + ":" + "<em>" + "<br>"
                + result.getMethod().getMethodName();
        setTestcaseName(TestcaseName);
        setTestcaseDescription(TestcaseDescription);
        ExtentManager.setExtentTest(ExtentReport.report.startTest(TestcaseName, TestcaseDescription));
        LogStatus.pass(TestcaseDescription);
    }

    public void onTestSuccess(ITestResult result) {
        ExtentReport.report.endTest(ExtentManager.getExtTest());
    }

    public void onTestFailure(ITestResult result) {
        LogStatus.fail("<pre>" + "\n\n" + Throwables.getStackTraceAsString(result.getThrowable()) + "</pre>");
        ExtentReport.report.endTest(ExtentManager.getExtTest());
    }

    public void onTestSkipped(ITestResult result) {
        LogStatus.skip("<span class='label skip'>" + result.getThrowable() + "</span>");
        ExtentReport.report.endTest(ExtentManager.getExtTest());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ExtentReport.report.endTest(ExtentManager.getExtTest());
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
        LogStatus.pass("<span class='label success'>" + "----Suite Finished-----" + "</span>");
        ExtentReport.report.endTest(ExtentManager.getExtTest());
    }
}