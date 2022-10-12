package com.qa.reports;

import com.qa.constants.Constants;
import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;

public class ExtentReport {
    public static ExtentReports report = null;
    public static String extentReportPath = "";

    private ExtentReport() {
        extentReportPath = Constants.EXTENT_REPORT_PATH;
        report = new ExtentReports(extentReportPath);
        report.loadConfig(new File(Constants.EXTENT_CONFIG_FILE_PATH));
    }

    public static void initialize() {
        new ExtentReport();
    }
}
