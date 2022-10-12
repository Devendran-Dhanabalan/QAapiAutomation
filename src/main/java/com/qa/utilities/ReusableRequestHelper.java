package com.qa.utilities;

import com.qa.constants.Constants;
import com.qa.request.helpers.WatchListScripHelper;
import com.qa.request.pojo.TransactionInitiatePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;

public class ReusableRequestHelper {

/*

    public static String getOrderFromOrderBook(String cuserid, String token, String jsessionid,
                                               RequestSpecification requestSpecification, PrintStream captor, StringWriter writer) {
        Map<String, String> inputRequestBody = InputRequestHelper.createInputBody("cuserid", cuserid, "productAlias",
                Constants.PRODUCT_ALIAS);
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor, inputRequestBody,
                Constants.GET_ORDER_BOOK_PATH, requestHeaders, writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        String orderNumber = "";
        JSONArray jsonArray = CommonRestHelper.getActualJsonArray(jsonObject, "data", "orderBookList");
        for (int i = 0; i < jsonArray.length(); i++) {
            String orderStatus = jsonArray.getJSONObject(i).getString("orderStatus");
            if (orderStatus.equalsIgnoreCase("open")) {
                return jsonArray.getJSONObject(i).getString("nestOrderNumber");
            }
        }
        return orderNumber;
    }

    public static String confirmOrder(String cuserid, String token, String jsessionid,
                                      RequestSpecification requestSpecification, PrintStream captor, StringWriter writer,
                                      float lowerCircuit) {
        String price = String.valueOf(lowerCircuit + .1);
        String jsonData = CommonRestHelper.convertFileToJson(Constants.CONFIRM_ORDER_JSON_FILE_PATH);
        ConfirmOrderRequestPojo confirmOrderRequestPojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<ConfirmOrderRequestPojo>() {
        });
        confirmOrderRequestPojo.setCuserid(cuserid);
        confirmOrderRequestPojo.setOrderType("L");
        confirmOrderRequestPojo.setPrice(price);
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);

        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor, confirmOrderRequestPojo,
                Constants.CONFIRM_ORDER_PATH, requestHeaders, writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);
        return ParseDynamicJson.getKey(jsonObject, "status");
    }

    public static boolean getScripFromWatchlist(String cuserid, String token, String jsessionid,
                                                RequestSpecification requestSpecification, PrintStream captor,
                                                StringWriter writer, String testEnvironment, String watchlistName, String predefined) {
        Map<String, String> queryParams = WatchListScripHelper.setQueryParams(cuserid, watchlistName, testEnvironment, predefined);
        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("token", token, "jsessionid", jsessionid);
        Response response = InputRequestHelper.createGetRequestMultipleQueryParam(queryParams, requestSpecification, captor,
                Constants.WATCHLIST_SCRIP_PATH, requestHeaders, writer);

        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        JSONArray jsonArray = CommonRestHelper.getActualJsonArray(jsonObject, "data", "scrips");
        for (int i = 0; i < jsonArray.length(); i++) {
            String scripName = jsonArray.getJSONObject(i).getString("displayScripName");
            String exchange = jsonArray.getJSONObject(i).getString("exchange");
            if (scripName.equalsIgnoreCase("idea") && exchange.equalsIgnoreCase("nse")) {
                return true;
            }
        }
        return false;
    }

    public static JSONObject transactionInitiate(String cuserid, String appKey,
                                             RequestSpecification requestSpecification, PrintStream captor, StringWriter writer) {
        String jsonData = CommonRestHelper.convertFileToJson(Constants.TRANSACTION_INITIATE_JSON_FILE_PATH);

        TransactionInitiatePojo transactionInitiatePojo = CommonRestHelper.jsonToObject(jsonData, new TypeReference<TransactionInitiatePojo>() {});
        transactionInitiatePojo.setCuserId(cuserid);
        transactionInitiatePojo.setAppkey(appKey);

        Map<String, String> requestHeaders = InputRequestHelper.setHeaders("appKey", appKey,
                "Referer","https://dsmobileuat.dhanistocks.com/market/funds/addfunds","Host","dsmobileuat.dhanistocks.com");

        //v1/funds/transaction/initiate
        Response response = InputRequestHelper.createPostRequest(requestSpecification, captor, transactionInitiatePojo,
                Constants.TRANSACTION_INITIATE_PATH, requestHeaders, writer);

        JSONObject jsonObject = CommonRestHelper.convertResponseToJson(response);

        AssertionHelper.assertNotNull(ParseDynamicJson.getKey(jsonObject, "orderNumber"), "orderNumber");

        return jsonObject ;
    }

    public static JSONObject razorpayWebhookCapture(String cuserid, String orderId,
            RequestSpecification requestSpecification, PrintStream captor, StringWriter writer) throws JsonProcessingException {

        String capturedJsonData = CommonRestHelper.convertFileToJson(Constants.PAYMENT_WEBHOOK_CAPTURED_JSON_FILE_PATH);

        //cuser ID and account Id are same here
        PaymentGatewayWebhookPojo paymentGatewayCapturedWebhookPojo = CommonRestHelper.jsonToObject(capturedJsonData, new TypeReference<PaymentGatewayWebhookPojo>() {});
        paymentGatewayCapturedWebhookPojo.getPayload().getPayment().getEntity().setOrder_id(orderId);
        paymentGatewayCapturedWebhookPojo.getPayload().getPayment().getEntity().getNotes().setClient_id(cuserid);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(paymentGatewayCapturedWebhookPojo);
        System.out.println("------------->"+json);

        WebhookDecryptor hashSecreteKey = new WebhookDecryptor();
        hashSecreteKey.setPayload(json);
        String payload = hashSecreteKey.getPayload();
        String secreteKey = hashSecreteKey.getSecret();
        String actualSignature = hashSecreteKey.getHash(payload, secreteKey);
        System.out.println("Signature = "+actualSignature);

        Map<String, String> headers = InputRequestHelper.setHeaders("x-razorpay-signature",actualSignature );

        Response capturedResponse = InputRequestHelper.createPostRequest(requestSpecification, captor, paymentGatewayCapturedWebhookPojo,
                Constants.PAYMENT_GATEWAY_WEBHOOK_PATH, headers,writer);

        JSONObject capturedJsonObject = new JSONObject(capturedResponse.getBody().asString());
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(capturedJsonObject, "message"), "Ok", "message");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(capturedJsonObject, "data"), "Success", "data");
        return capturedJsonObject;
    }


    public static JSONObject razorpayWebhookAuthorize(String cuserid, String orderId,
                                              RequestSpecification requestSpecification, PrintStream captor, StringWriter writer) throws JsonProcessingException {
        //Webhook Authorise needs to be called first
        String authorizedJsonData = CommonRestHelper.convertFileToJson(Constants.PAYMENT_WEBHOOK_AUTHORIZED_JSON_FILE_PATH);

        PaymentGatewayWebhookPojo paymentGatewayAuthorizedWebhookPojo = CommonRestHelper.jsonToObject(authorizedJsonData, new TypeReference<PaymentGatewayWebhookPojo>() {});
        paymentGatewayAuthorizedWebhookPojo.getPayload().getPayment().getEntity().setOrder_id(orderId);
        paymentGatewayAuthorizedWebhookPojo.getPayload().getPayment().getEntity().getNotes().setClient_id(cuserid);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(paymentGatewayAuthorizedWebhookPojo);
        System.out.println("------------->"+json);

        WebhookDecryptor hashSecreteKey = new WebhookDecryptor();
        hashSecreteKey.setPayload(json);
        String payload = hashSecreteKey.getPayload();
        String secreteKey = hashSecreteKey.getSecret();
        String actualSignature = hashSecreteKey.getHash(payload, secreteKey);
        System.out.println("Signature = "+actualSignature);

        Map<String, String> headers = InputRequestHelper.setHeaders("x-razorpay-signature",actualSignature );

        Response authorizedResponse = InputRequestHelper.createPostRequest(requestSpecification, captor,
                paymentGatewayAuthorizedWebhookPojo, Constants.PAYMENT_GATEWAY_WEBHOOK_PATH,headers, writer);

        JSONObject authorizedJsonObject = new JSONObject(authorizedResponse.getBody().asString());
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(authorizedJsonObject, "message"), "Ok", "message");
        AssertionHelper.assertFieldValue(ParseDynamicJson.getKey(authorizedJsonObject, "data"), "Success", "data");
        return authorizedJsonObject;
    }
*/

}
