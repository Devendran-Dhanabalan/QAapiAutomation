package com.qa.request.helpers;

import java.util.HashMap;
import java.util.Map;

public class WatchListScripHelper {
    public static Map<String, String> setQueryParams(String cuserid, String scripName, String testEnvironment, String predefined) {
        String brokerName = "";
        if (testEnvironment.toLowerCase().contains("uat")) {
            brokerName = "INDIABULL";
        } else if (testEnvironment.toLowerCase().contains("prod")) {
            brokerName = "INDIABULLS";
        }

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("cuserid", cuserid);
        queryParams.put("watchListName", scripName);
        queryParams.put("predefined", predefined);
        queryParams.put("brokerName", brokerName);
        queryParams.put("productAlias", "NRML:NRML%7C%7CCNC:CNC%7C%7CSHORTSELL:SHORTSELL%7C%7CMIS:MIS%7C%7CCO:CO%7C%7CBO:BO");
        return queryParams;
    }
}
