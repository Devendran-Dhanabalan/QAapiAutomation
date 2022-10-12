package com.qa.request.helpers;

import com.qa.constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class WatchListScripAddHelper {

    public static Map<String, String> inputBody(String cuserId, String watchListName, String exchange
            , String scripSymbol, String testEnvironment) {
        String brokerName = "";
        if (testEnvironment.toLowerCase().contains("uat")) {
            brokerName = "INDIABULL";
        } else if (testEnvironment.toLowerCase().contains("prod")) {
            brokerName = "INDIABULLS";
        }

        Map<String, String> body = new HashMap<>();
        body.put("cuserId", cuserId);
        body.put("watchListName", watchListName);
        body.put("exchange", exchange);
        body.put("brokerName", brokerName);
        body.put("scripSymbol", scripSymbol);
        body.put("productAlias", Constants.PRODUCT_ALIAS);
        return body;
    }
}
