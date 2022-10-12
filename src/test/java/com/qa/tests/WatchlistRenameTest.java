package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.constants.Constants;
import com.qa.utilities.*;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Map;

public class WatchlistRenameTest extends BaseTest {
    @Test(description = "Verify that user is able to rename watchlist for valid cuserid", priority = 12, dependsOnGroups = "getKeys")
    public void verify_valid_cuserid_watchlist_rename() {
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);
        Response watchListNamesResponse = InputRequestHelper.createGetRequestSingleQueryParam("cuserid", cuserid, requestSpecification, captor,
                Constants.WATCHLIST_NAMES_PATH, requestHeaders, "qa/schema/watchlistname/watchlist_name.json", writer);

        String watchList1Name = CommonRestHelper.getArrayNodeValue(watchListNamesResponse, "data.watchListNames", 0);
        String newWatchListName = "RenameWatch";

        /**
         * Watchlist rename request and response
         */
        if (watchList1Name.equalsIgnoreCase("RenameWatch"))
            newWatchListName = watchList1Name + RandomStringUtils.randomAlphabetic(3);

        Map<String, String> inputRequestBody = InputRequestHelper.createInputBody("cuserId", cuserid, "watchListName",
                watchList1Name, "newWatchListName", newWatchListName);
        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor, inputRequestBody,
                Constants.WATCHLIST_RENAME_PATH, requestHeaders, "qa/schema/watchlistscripdelete/watchlist_scrip_delete.json", writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "Result"), "success", "Error_Code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "stat"), "Ok", "Error_Mesg");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "message"), "Ok", "Success");

        /**
         * Rename back to original
         */
        Map<String, String> inputRequestBody1 = InputRequestHelper.createInputBody("cuserId", cuserid, "watchListName",
                newWatchListName, "newWatchListName", watchList1Name);
        Response response1 = InputRequestHelper.createPostRequest(requestSpecification, captor, inputRequestBody1,
                Constants.WATCHLIST_RENAME_PATH, requestHeaders, "qa/schema/watchlistscripdelete/watchlist_scrip_delete.json", writer);
        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response1.prettyPrint());
        JSONObject jsonObject1 = new JSONObject(response.getBody().asString());

        AssertionHelper.assertTime(response1, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject1, "Result"), "success", "Error_Code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject1, "stat"), "Ok", "Error_Mesg");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject1, "message"), "Ok", "Success");
    }

    @Test(description = "Verify that user is not able to rename watchlist for invalid cuserid", priority = 30, dependsOnGroups = "getKeys",
            dataProvider = "invalidCuserid", dataProviderClass = com.qa.dataprovider.DataProvider.class)
    public void verify_invalid_cuserid_watchlist_rename(String invalidCuserId) {
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);
        Response watchListNamesResponse = InputRequestHelper.createGetRequestSingleQueryParam("cuserid", cuserid, requestSpecification, captor,
                Constants.WATCHLIST_NAMES_PATH, requestHeaders, writer);
        CommonHelper.writeRequestAndResponseInReport(writer.toString(), watchListNamesResponse.prettyPrint());
        String watchList1Name = CommonRestHelper.getArrayNodeValue(watchListNamesResponse, "data.watchListNames", 0);
        String newWatchListName = "RenameWatch";

        /**
         * Watchlist rename request and response
         */
        if (watchList1Name.equalsIgnoreCase("RenameWatch"))
            newWatchListName = watchList1Name + RandomStringUtils.randomAlphabetic(3);

        Map<String, String> inputRequestBody = InputRequestHelper.createInputBody("cuserId", invalidCuserId, "watchListName",
                watchList1Name, "newWatchListName", newWatchListName);
        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor, inputRequestBody,
                Constants.WATCHLIST_RENAME_PATH, requestHeaders, "qa/schema/invalidcredentials/invalid_credentials.json", writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        AssertionHelper.assertTime(response, responseTime, "responseTime");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "code"), "11095", "code");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(jsonObject, "data"), "null", "data");
        AssertionHelper.assertContains(ParseDynamicJson.getKey(jsonObject, "message"), "Session Expired", "message");
    }
}
