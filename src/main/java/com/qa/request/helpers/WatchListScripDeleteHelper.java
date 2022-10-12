package com.qa.request.helpers;

import java.util.HashMap;
import java.util.Map;

public class WatchListScripDeleteHelper {
    public static Map<String, String> inputBody(String cuserId, String watchListName, String exchange, String scripSymbol) {
        Map<String, String> body = new HashMap<>();
        body.put("cuserId", cuserId);
        body.put("watchListName", watchListName);
        body.put("exchange", exchange);
        body.put("scripSymbol", scripSymbol);
        return body;
    }
}
