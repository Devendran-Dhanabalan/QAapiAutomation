package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.constants.Constants;
import com.qa.request.pojo.SimpleLoginRequestPojo;
import com.qa.utilities.AssertionHelper;
import com.qa.utilities.CommonRestHelper;
import com.qa.utilities.InputRequestHelper;
import com.qa.utilities.ParseDynamicJson;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class SimpleLoginTest extends BaseTest {

    @Test(description = "Verify that user is able to login with valid credentials",
            groups = {"getKeys"})
    public void verify_valid_credentials_simple_login() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor, loginRequestPojo,
                Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/simple_login.json", writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);
        appKey = ParseDynamicJson.getKey(jsonObject, "appkey");
        token = ParseDynamicJson.getKey(jsonObject, "token");
        jsessionid = ParseDynamicJson.getKey(jsonObject, "jSessionId");

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertContains(ParseDynamicJson.getKey(jsonObject, "clientIds"), cuserid, "client_id");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "S", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Successful", "message");
    }

    @Test(description = "Verify that user is not able to login with invalid credentials", priority = 30)
    public void verify_invalid_credentials_simple_login() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword("orbis@1");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/invalid_cred_login.json", writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Password is required & must be 6 character alphanumeric string", "message");
    }

    @Test(description = "Verify that user is able to login with ip as null", priority = 30)
    public void verify_null_ip_simple_login() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        loginRequestPojo.setIpaddress(null);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // modifying the script as per security changes done in https://dhaniservices.atlassian.net/browse/DIS-1147
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "code"), "0", "code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "message"), "Ok", "message");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "'ip_address' is required.", "message");
    }

    @Test(description = "Verify that user is able to login with network type as wifi", priority = 30)
    public void verify_network_wifi_simple_login() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        loginRequestPojo.setNetworkType("wifi");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertContains(ParseDynamicJson.getKey(jsonObject, "clientIds"), cuserid, "client_id");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "S", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Successful", "message");
    }

    @Test(description = "Verify that user is not able to login with network type as null", priority = 15)
    public void verify_network_null_simple_login() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        loginRequestPojo.setNetworkType(null);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/invalid_cred_login.json", writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "network_type' is required.", "message");
    }

    @Test(description = "Verify simple-login api with empty password", priority = 30)
    public void verify_simple_login_with_empty_password_in_body_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword("");
        //loginRequestPojo.setNetworkType(null);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/simple_login_invalid_password_errors.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "'password_value' is required.", "message");
    }

    @Test(description = "Verify simple-login with invalid password", priority = 30)
    public void verify_simple_login_with_invalid_password_in_body_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword("");
        //loginRequestPojo.setNetworkType(null);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/simple_login_invalid_password_errors.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "'password_value' is required.", "message");
    }

    @Test(description = "Verify that user is not able to login with invalid mobileNumber", priority = 30)
    public void verify_invalid_mobile_number_simple_login() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber("12398123"); //Invlaid Mobile Numbmer other then 10 digit
        loginRequestPojo.setPassword("ANK1");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/invalid_cred_login.json", writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // modifyin the changes done w.r.t security in Bug-https://dhaniservices.atlassian.net/browse/DIS-1147
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "F", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Incorrect Client ID #Please check and try again", "message");
    }

    @Test(description = "Verify that user is not able to login with empty mobileNumber", priority = 30)
    public void verify_empty_mobile_number_simple_login() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(""); //empty Mobile Numbmer
        loginRequestPojo.setPassword("orbis@1");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/invalid_cred_login.json", writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "'mobile_number' is required.", "message");
    }

    @Test(description = "Verify simple-login with empty networkType", priority = 30)
    public void verify_simple_login_with_empty_NetworkType_in_body_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setNetworkType("");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "'network_type' is required.", "message");
    }

    @Test(description = "Verify simple-login with empty Pagecode"+"Bug-https://dhaniservices.atlassian.net/browse/DSMA-3524", priority = 30)
    public void verify_simple_login_with_empty_Pagecode_in_body_test(Method method) {
        //Skipping this test as we have already created the bug on jira,valid bug.
        Test test = method.getAnnotation(Test.class);
        if (test.description().contains("Bug"))
            throw new SkipException("Test Skipped: " + test.description());

        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setPagecode("");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "pagecode is required.", "message");
    }

    @Test(description = "Verify simple-login with empty primaryDeviceId", priority = 30)
    public void verify_simple_login_with_empty_primaryDeviceId_in_body_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setPrimaryDeviceId("");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "'primary_device_id' is required.", "message");
    }

    @Test(description = "Verify simple-login with empty deviceType", priority = 30)
    public void verify_simple_login_with_empty_deviceType_in_body_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setDeviceType("");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "device_type' is required.", "message");
    }

    @Test(description = "Verify simple-login with empty platform", priority = 30)
    public void verify_simple_login_with_empty_platform_in_body_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setPlatform("");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "'platform' is required.", "message");
    }

    @Test(description = "Verify simple-login with invalid networkType"+"Bug-https://dhaniservices.atlassian.net/browse/DSMA-3524", priority = 30)
    public void verify_simple_login_with_invalid_NetworkType_in_body_test(Method method) {
        //Skipping this test as we have already created the bug on jira,valid bug.
        Test test = method.getAnnotation(Test.class);
        if (test.description().contains("Bug"))
            throw new SkipException("Test Skipped: " + test.description());

        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setNetworkType("Testing");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Invalid Network Type", "message");
    }

    @Test(description = "Verify simple-login with invalid Pagecode"+"Bug-https://dhaniservices.atlassian.net/browse/DSMA-3524", priority = 30)
    public void verify_simple_login_with_invalid_Pagecode_in_body_test(Method method) {
        //Skipping this test as we have already created the bug on jira,valid bug.
        Test test = method.getAnnotation(Test.class);
        if (test.description().contains("Bug"))
            throw new SkipException("Test Skipped: " + test.description());

        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setPagecode("`!@#$^^%$Testing");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Invalid Pagecode", "message");
    }

    @Test(description = "Verify simple-login with invalid primaryDeviceId"+"Bug-https://dhaniservices.atlassian.net/browse/DSMA-3524", priority = 30)
    public void verify_simple_login_with_invalid_primaryDeviceId_in_body_test(Method method) {
        //Skipping this test as we have already created the bug on jira,valid bug.
        Test test = method.getAnnotation(Test.class);
        if (test.description().contains("Bug"))
            throw new SkipException("Test Skipped: " + test.description());

        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setPrimaryDeviceId("!@#$&*^%$Testing");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Invalid primaryDeviceId", "message");
    }

    @Test(description = "Verify simple-login with invalid deviceType"+"Bug-https://dhaniservices.atlassian.net/browse/DSMA-3524", priority = 30)
    public void verify_simple_login_with_invalid_deviceType_in_body_test(Method method) {
        //Skipping this test as we have already created the bug on jira,valid bug.
        Test test = method.getAnnotation(Test.class);
        if (test.description().contains("Bug"))
            throw new SkipException("Test Skipped: " + test.description());

        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setDeviceType("`!@#$%^Testing");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Invalid deviceType", "message");
    }

    @Test(description = "Verify simple-login with invalid platform"+"Bug-https://dhaniservices.atlassian.net/browse/DSMA-3524", priority = 30)
    public void verify_simple_login_with_invalid_platform_in_body_test(Method method) {
        //Skipping this test as we have already created the bug on jira,valid bug.
        Test test = method.getAnnotation(Test.class);
        if (test.description().contains("Bug"))
            throw new SkipException("Test Skipped: " + test.description());

        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);

        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);
        //for invalid pagecode,primaryDeviceId,networkType,deviceType,platform, we are getting valid resposne with result "S" in simple login api
        loginRequestPojo.setPlatform("testing");

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/null_ip_login.json", writer,200);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "version"), "version");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "appkey"), "appkey");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "action"), "action");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "customerFullName"), "customerFullName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "firstName"), "firstName");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "lastLogin"), "lastLogin");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "jSessionId"), "jSessionId");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "token"), "token");
        AssertionHelper.assertEmpty(ParseDynamicJson.getKey(jsonObject, "userSessionId"), "userSessionId");
        AssertionHelper.assertNull(CommonRestHelper.getArrayNodeValue(response, "data.clientIds", 0), "clientIds");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "result"), "E", "result");
        AssertionHelper.assertContains(CommonRestHelper.getNodeValue(response, "data.message"), "Invalid platform", "message");
    }

    @Test(description = "Verify simple-login api with the invalid url end point and with invalid method test", priority = 30)
    public void verify_simple_login_with_invalid_url_end_point_and_invalid_method_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);

        // with invaid method as get for simple login api
        Response response = InputRequestHelper.createGetRequestSingleQueryParam("mobileNumber", mobileNumber,
                requestSpecification, captor, Constants.Simple_LOGIN_PATH+"invalid", "qa/schema/simplelogin/simple_login_error_4XX_code.json", writer,404);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "code"), "404", "code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "message"), "Not found", "message");
    }

    @Test(description = "Verify simple-login api with the valid url end point and with invalid method test", priority = 30)
    public void verify_simple_login_with_valid_url_end_point_and_invalid_method_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);

        // with invaid method as get for simple login api
        Response response = InputRequestHelper.createGetRequestSingleQueryParam("mobileNumber", mobileNumber,
                requestSpecification, captor, Constants.Simple_LOGIN_PATH, "qa/schema/simplelogin/simple_login_error_4XX_code.json", writer,400);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "code"), "400", "code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "message"), "Bad Request", "message");
    }

    @Test(description = "Verify simple-login api with the invalid url end point showing status code 404 test", priority = 30)
    public void verify_simple_login_with_invalid_url_end_point_test() {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.LOGIN_JSON_FILE_PATH);
        SimpleLoginRequestPojo loginRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<SimpleLoginRequestPojo>() {
        });
        loginRequestPojo.setMobileNumber(mobileNumber);
        loginRequestPojo.setPassword(password);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor,
                loginRequestPojo, Constants.Simple_LOGIN_PATH+"invalid", "qa/schema/simplelogin/simple_login_error_4XX_code.json", writer,404);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        // Single code needed when market is open/close, uat/prod below code for all cases
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "code"), "404", "code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "message"), "Not found", "message");
    }

}
