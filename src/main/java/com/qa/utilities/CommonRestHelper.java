package com.qa.utilities;

import com.qa.constants.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CommonRestHelper {
    private static RequestSpecification requestSpecification;
    private static String jsonData = "";

    public static RequestSpecification setRequestSpecification(String baseUrl) {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri(baseUrl);
        requestSpecification.header("Content-Type", "application/json");
        //requestSpecification.cookies(setCookies());
        return requestSpecification;
    }

    public static <T> T jsonToObject(String json, TypeReference<T> type) {
        T target = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            target = objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return target;
    }

    public static String convertFileToJson(String filePath) {
        try {
            File jsonDataInFile = new File(filePath);
            jsonData = FileUtils.readFileToString(jsonDataInFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static JSONObject convertResponseToJson(Response response) {
        return new JSONObject(response.getBody().asString());
    }

    public static String getNodeValue(Response response, String node) {
        String value = "";
        try {
            value = response.jsonPath().get(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getArrayNodeValue(Response response, String node, int index) {
        String value = "";
        try {
            value = response.jsonPath().getString(node + "[" + index + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Object getArrayNodeValue(JSONArray jsonArray, int index, String node) {
        Object value = "";
        try {
            JSONObject object = jsonArray.getJSONObject(index);
            value = object.get(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Map<String, String> setCookies() {
        Map<String, String> cookie = new HashMap<>();
        cookie.put("auth-api-key", "kpmjxogfrsvafihxyoupfzzjd6uzuinx");
        cookie.put("ipaddress", "192.168.0.101");
        cookie.put("appVersion", "3.5.2");
        cookie.put("deviceType", "mobile");
        cookie.put("screenSize", "1080*2217");
        cookie.put("mobileNumber", "8800514767");
        cookie.put("platformVersion", "10");
        cookie.put("channel", "M");
        cookie.put("modelNo", "Redmi Note 7 Pro");
        cookie.put("primaryDeviceId", "d840eb76-bb66-459a-8de9-c246276dc304");
        cookie.put("networkType", "4g");
        cookie.put("platform", "Android");
        cookie.put("hasBiometrics", "true");
        return cookie;
    }

    public static JSONArray getExpectedJsonArray(String filePath, String node) {
        String jsonData = convertFileToJson(filePath);
        JSONObject json = new JSONObject(jsonData);
        JSONArray getArray = json.getJSONArray("data");
        return getArray;
    }

    public static JSONArray getActualJsonArray(JSONObject jsonObject, String node1, String node2) {
        JSONArray jsonArray = jsonObject.getJSONObject(node1).getJSONArray(node2);
        return jsonArray;
    }

    public static String getExpectedJsonNodeValue(String filePath, String node) {
        String jsonData = convertFileToJson(filePath);
        JSONObject json = new JSONObject(jsonData);
        return json.getString(node);
    }

    public static RequestSpecification setReportRequestSpecification(RequestSpecification requestSpecification, String testEnvironment) {
        if (testEnvironment.equalsIgnoreCase("uat"))
            requestSpecification.baseUri(Constants.REPORTS_BASEURL_UAT);
        else if (testEnvironment.equalsIgnoreCase("prod"))
            requestSpecification.baseUri(Constants.REPORTS_BASEURL_PROD);
        return requestSpecification;
    }
}
