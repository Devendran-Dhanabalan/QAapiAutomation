package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.constants.Constants;
import com.qa.utilities.AssertionHelper;
import com.qa.utilities.CommonRestHelper;
import com.qa.utilities.InputRequestHelper;
import com.qa.utilities.ParseDynamicJson;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Map;

public class WatchListNameTest extends BaseTest {
    @Test(description = "Verify that user is able to get watchlist name with valid cuserid", priority = 6, dependsOnGroups = "getKeys")
    public void verify_valid_cuserid_watchlist_name() {
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);
        Response response = InputRequestHelper.createGetRequestSingleQueryParam("cuserid", cuserid, requestSpecification, captor,
                Constants.WATCHLIST_NAMES_PATH, requestHeaders, "qa/schema/watchlistname/watchlist_name.json", writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(CommonRestHelper.getArrayNodeValue(response, "data.predefinedwatchListNames", 0), "Nifty", "predefinedwatchListNames");
        AssertionHelper.assertFieldValue(CommonRestHelper.getArrayNodeValue(response, "data.predefinedwatchListNames", 1), "Sensex", "predefinedwatchListNames");

        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("watchListNames");
        AssertionHelper.assertNotNull(jsonArray.toString(), "data.watchListNames");
    }

    @Test(description = "Verify that user is not able to get watchlist name with invalid cuserid", priority = 30,
            dependsOnGroups = "getKeys", dataProvider = "invalidCuserid", dataProviderClass = com.qa.dataprovider.DataProvider.class)
    public void verify_invalid_cuserid_watchlist_name(String invalidCuserId) {
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);

        Response response = InputRequestHelper.createGetRequestSingleQueryParam("cuserid", invalidCuserId, requestSpecification,
                captor, Constants.WATCHLIST_NAMES_PATH, requestHeaders, "qa/schema/invalidcredentials/invalid_credentials.json", writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "code"), "11095", "code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "data"), "null", "data");
        AssertionHelper.assertContains(ParseDynamicJson.getKey(jsonObject, "message"), "Session Expired", "message");
    }
}
