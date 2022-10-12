package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.constants.Constants;
import com.qa.utilities.AssertionHelper;
import com.qa.utilities.CommonRestHelper;
import com.qa.utilities.InputRequestHelper;
import com.qa.utilities.ParseDynamicJson;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Map;

public class SecurityInfoTest extends BaseTest {
    @Test(description = "Verify that user is able to get security info with valid cuserid"+"-https://dhaniservices.atlassian.net/browse/DSMA-3667", priority = 16, dependsOnGroups = "getKeys")
    public void verify_valid_cuserid_security_info(Method method) {
        Test test = method.getAnnotation(Test.class);
        if (test.description().contains("Bug"))
            throw new SkipException("Test Skipped: " + test.description());

        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);
        Map<String, String> requestParams = InputRequestHelper.setQueryParams("cuserid", cuserid, "scripSymbol", "15083", "exchange", "NSE", "appkey", appKey);
        Response response = InputRequestHelper.createGetRequestMultipleQueryParam(requestParams, requestSpecification, captor,
                Constants.SECURITY_INFO_PATH, requestHeaders, "qa/schema/securityinfo/security_info.json", writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        String outputFilePath = Constants.SECURITY_INFO_JSON_FILE_PATH;
        String expExchangeSymbol = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "ExchangeSymbol");
        String expISINCode = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "ISINCode");
        String expCategory = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "category");
        String expCompanyName = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "companyName");
        String expIntradayTrading = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "intradayTrading");
        String expMarginTrading = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "marginTrading");
        String expScripCode = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "scripCode");
        String expSeries = CommonRestHelper.getExpectedJsonNodeValue(outputFilePath, "series");

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "ExchangeSymbol"), expExchangeSymbol, "ExchangeSymbol");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "ISINCode"), expISINCode, "ISINCode");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "category"), expCategory, "category");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "companyName"), expCompanyName, "companyName");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "intradayTrading"), expIntradayTrading, "intradayTrading");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "marginTrading"), expMarginTrading, "marginTrading");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "scripCode"), expScripCode, "scripCode");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "series"), expSeries, "series");
    }

    @Test(description = "Verify that user is not able to get security info with invalid cuserid", priority = 30, dependsOnGroups = "getKeys",
            dataProvider = "invalidCuserid", dataProviderClass = com.qa.dataprovider.DataProvider.class)
    public void verify_invalid_cuserid_security_info(String invalidCuserId) {
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);
        Map<String, String> requestParams = InputRequestHelper.setQueryParams("cuserid", invalidCuserId, "scripSymbol", "15083", "exchange", "NSE", "appkey", appKey);
        Response response = InputRequestHelper.createGetRequestMultipleQueryParam(requestParams, requestSpecification, captor,
                Constants.SECURITY_INFO_PATH, requestHeaders, "qa/schema/invalidcredentials/invalid_credentials.json", writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "code"), "11095", "code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "data"), "null", "data");
        AssertionHelper.assertContains(ParseDynamicJson.getKey(jsonObject, "message"), "Session Expired", "message");
    }
}
