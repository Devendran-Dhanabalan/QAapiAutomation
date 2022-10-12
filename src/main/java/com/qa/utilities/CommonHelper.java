package com.qa.utilities;

import com.qa.constants.Constants;
import com.qa.reports.LogStatus;
import org.testng.ISuite;
import org.testng.ITestContext;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class CommonHelper {

    public static String getBaseUrl(String environment) {
        if (environment.equalsIgnoreCase("prod"))
            return Constants.BASEURL_PROD;
        else if (environment.equalsIgnoreCase("uat"))
            return Constants.BASEURL_UAT;
        else if (environment.equalsIgnoreCase("aws_uat"))
            return Constants.BASEURL_AWS_UAT;
        return null;
    }

    public static String getEnvrionmentFile(String environment) {
        if (environment.toLowerCase().contains("prod"))
            return Constants.TEST_DATA_FILE_PROD;
        else if (environment.toLowerCase().contains("uat"))
            return Constants.TEST_DATA_FILE_UAT;
        return null;
    }

    /*
     * Format the api string and log in Extent Report
     */
    protected static void formatAPIAndLogInReport(String content) {
        String prettyPrint = content.replace("\n", "<br>");
        LogStatus.pass("<pre>" + prettyPrint + "</pre>");
    }

    /*
     * Write the request and response in report
     */
    public static void writeRequestAndResponseInReport(String request, String response) {
        LogStatus.pass("---- Request ---");
        formatAPIAndLogInReport(request);
        LogStatus.pass("---- Response ---");
        formatAPIAndLogInReport(response);
    }

    public static String bold(String text) {
        return new StringBuffer().append("<b>").append(text).append("</b>").toString();
    }

    public static String urlDecode(String url) {
        try {
            String decodeURL = "";
            decodeURL = URLDecoder.decode(url, "UTF-8");
            return decodeURL;
        } catch (UnsupportedEncodingException e) {
            return "Issue while decoding" + e.getMessage();
        }
    }

    public static boolean isMarketOpen() {
        long marketOpen = 9 * 60 + 15;
        long marketClosed = 15 * 60 + 30;

        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfWeek().getValue();

        if (day == 0 || day == 6) {
            return false;
        } else {
            long currentTimeMinutes = LocalTime.now().getHour() * 60 + LocalTime.now().getMinute();
            return currentTimeMinutes > marketOpen && currentTimeMinutes < marketClosed;
        }
    }

    public static String getCurrentDate() {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Calendar calendar = Calendar.getInstance();
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getCalculatedDate(int month) {
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static void setTestContextValue(ITestContext context, String varName, String varValue) {
        ISuite suite = context.getSuite();
        suite.setAttribute(varName, varValue);
    }

    public static String getTestContextValue(ITestContext context, String varName) {
        ISuite suite = context.getSuite();
        return (String) suite.getAttribute(varName);
    }
}
