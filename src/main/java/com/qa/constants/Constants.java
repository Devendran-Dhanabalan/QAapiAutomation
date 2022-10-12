package com.qa.constants;

public class Constants {
    /**
     * properties file path
     */
    public static final String CONFIG_FILE_PATH = "src/test/resources/qa/config/config.properties";
    public static final String TEST_DATA_FILE_UAT = "src/test/resources/qa/config/uat.properties";
    public static final String TEST_DATA_FILE_PROD = "src/test/resources/qa/config/prod.properties";

    /**
     * Input Json File
     */
    public static final String LOGIN_JSON_FILE_PATH = "src/main/resources/input/json/login.json";

    /**
     * Output Json File
     */
    public static final String SECURITY_INFO_JSON_FILE_PATH = "src/main/resources/output/json/securityInfo.json";
    public static final String SECURITY_INFO_PATH = "";
    /**
     * Report paths
     */
    public static final String EXTENT_REPORT_PATH = "target/ExtentReports/AutomationReport.html";
    public static final String EXTENT_CONFIG_FILE_PATH = "src/test/resources/qa/reports/extentreport.xml";

    /**
     * Constant Values
     */
    public static final String PRODUCT_ALIAS = " ";

    /**
     * constant urls
     */
    public static final String BASEURL_UAT = "https://dsmobileuat.dhanistocks.com/";
    public static final String BASEURL_PROD = "https://prod.website.com";
    public static final String BASEURL_AWS_UAT = "https://prod.website.com";
    public static final String REPORTS_BASEURL_UAT = "";
    public static final String REPORTS_BASEURL_PROD = "";


    /**
     *  API's
     */
    public static final String Simple_LOGIN_PATH = "v2/simple-login";

    /**
     * watch list
     */
    public static final String WATCHLIST_NAMES_PATH ="" ;
    public static final String WATCHLIST_RENAME_PATH ="" ;
}
