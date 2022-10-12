package com.qa.base;

import com.qa.constants.Constants;
import com.qa.enums.ConfigVariables;
import com.qa.enums.Environments;
import com.qa.objectmanager.FileReaderManager;
import com.qa.reports.ExtentReport;
import com.qa.utilities.CommonHelper;
import com.qa.utilities.CommonRestHelper;
import com.qa.utilities.ConfigReader;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.PrintStream;
import java.io.StringWriter;

public class BaseTest {
    protected static RequestSpecification requestSpecification;
    protected static ConfigReader dataReader;
    protected static String mobileNumber;
    protected static String password;
    protected static String cuserid;
    protected static String appKey;
    protected static String token;
    protected static String jsessionid;
    protected static String responseTime;
    protected static String testEnvironment;
    protected static String reportAuthApiKey;
    protected static float lowerCircuit;
    protected static float upperCircuit;
    protected StringWriter writer;
    protected PrintStream captor;

    /*
     * Initializing the extent report
     * Get the test environment and set base url and request specification
     * Read the environment file
     */
    @BeforeSuite
    public void apiTestSetUp() {
        ExtentReport.initialize();
        Logger log = Logger.getLogger(BaseTest.class);

        try {
            FileReaderManager.getInstance().getConfigReader(Constants.CONFIG_FILE_PATH);
            //Evn setup
            if (System.getProperty("env") != null) {
                testEnvironment = System.getProperty("env");
                log.info("ci environment value is: " + testEnvironment);
            } else {
                testEnvironment = ConfigReader.getPropertyValue(ConfigVariables.TEST_ENVIRONMENT.toString());
            }

            responseTime = ConfigReader.getPropertyValue(ConfigVariables.RESPONSE_TIME.toString());
            String baseURL = CommonHelper.getBaseUrl(testEnvironment);
            if (baseURL != null) {
                requestSpecification = CommonRestHelper.setRequestSpecification(baseURL);
            }

            // Parameter setUp
            if (System.getProperty("mobilenumber") != null && System.getProperty("password") != null
                    && System.getProperty("cuserid") != null) {
                mobileNumber = System.getProperty("mobilenumber");
                password = System.getProperty("password");
                cuserid = System.getProperty("cuserid");
                log.info("CI Values-- mobilenumber: " + mobileNumber + " cuserid: " + cuserid);
            } else {
                String testDataSheet = CommonHelper.getEnvrionmentFile(testEnvironment);
                if (testDataSheet.contains(Environments.UAT.toString().toLowerCase())) {
                    dataReader = FileReaderManager.getInstance().getConfigReader(Constants.TEST_DATA_FILE_UAT);
                } else if (testDataSheet.contains(Environments.PROD.toString().toLowerCase())) {
                    dataReader = FileReaderManager.getInstance().getConfigReader(Constants.TEST_DATA_FILE_PROD);
                }
                mobileNumber = ConfigReader.getPropertyValue("mobilenumber");
                password = ConfigReader.getPropertyValue("password");
                cuserid = ConfigReader.getPropertyValue("cuserid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Flushing the extent report
     * Opening the extent report automatically after the test suite execution.
     */
    @AfterSuite
    public void afterSuite() {
        ExtentReport.report.flush();
        new File(Constants.EXTENT_REPORT_PATH);
    }

    /*
     * This method helps to write the request and response to the extent report
     */
    @BeforeMethod
    public void testSetUp() {
        writer = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writer, "UTF-8"), true);
    }
}
