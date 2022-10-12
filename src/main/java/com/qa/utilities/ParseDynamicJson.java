package com.qa.utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class ParseDynamicJson {
    public static String parseObject(JSONObject json, String key) {
        try {
            return json.get(key).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getKey(JSONObject json, String key) {
        boolean exists = json.has(key);
        Iterator<?> keys;
        String nextKeys;
        String value = "";

        if (!exists) {
            keys = json.keys();
            while (keys.hasNext()) {
                nextKeys = (String) keys.next();
                try {
                    if (json.get(nextKeys) instanceof JSONObject) {
                        String getValue = getKey(json.getJSONObject(nextKeys), key);
                        if (!getValue.isEmpty()) {
                            value = getValue;
                            break;
                        }
                    } else if (json.get(nextKeys) instanceof JSONArray) {
                        JSONArray jsonarray = json.getJSONArray(nextKeys);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            String jsonArrayString = jsonarray.get(i).toString();
                            JSONObject innerJSOn = new JSONObject(jsonArrayString);
                            String getValue = getKey(innerJSOn, key);
                            if (!getValue.isEmpty()) {
                                value = getValue;
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Key doesn't exist in response - " + key);
                }
            }
        } else {
            value = json.get(key).toString();
        }
        return value;
    }
}
