package com.qa.utilities;

import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class InputRequestHelper {
    public static Response createGetRequestMultipleQueryParam(Map<String, String> queryParams,
                                                              RequestSpecification requestSpecification,
                                                              PrintStream captor, String path, Map<String, String> headers, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParams(queryParams)
                .urlEncodingEnabled(false)
                .headers(headers).filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        return response;
    }

    public static Response createGetRequestMultipleQueryParam(Map<String, String> queryParams,
                                                              RequestSpecification requestSpecification,
                                                              PrintStream captor, String path, Map<String, String> headers,
                                                              String jsonSchemaFile, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParams(queryParams)
                .urlEncodingEnabled(false)
                .headers(headers).filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    public static Response createGetRequestMultipleQueryParam(Map<String, String> queryParams,
                                                              RequestSpecification requestSpecification,
                                                              PrintStream captor, String path, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParams(queryParams)
                .urlEncodingEnabled(false)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        return response;
    }

    public static Response createGetRequestMultipleQueryParam(Map<String, String> queryParams,
                                                              RequestSpecification requestSpecification,
                                                              PrintStream captor, String path, String jsonSchemaFile, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParams(queryParams)
                .urlEncodingEnabled(false)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }
   // overriden method with header and status code added
    public static Response createGetRequestMultipleQueryParam(Map<String, String> queryParams,
                                                              RequestSpecification requestSpecification,
                                                              PrintStream captor, String path, String jsonSchemaFile,
                                                              StringWriter writer,int statusCode) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParams(queryParams)
                .urlEncodingEnabled(false)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }
    // overriden method with header and status code added
    public static Response createGetRequestMultipleQueryParam(Map<String, String> queryParams,
                                                              RequestSpecification requestSpecification,
                                                              PrintStream captor, String path,  Map<String, String> headers,
                                                              String jsonSchemaFile, StringWriter writer,int statusCode) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParams(queryParams)
                .urlEncodingEnabled(false)
                .headers(headers)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter())
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    public static Response createGetRequestSingleQueryParam(String paramKey, String paramValue, RequestSpecification requestSpecification,
                                                            PrintStream captor, String path, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParam(paramKey, paramValue)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        return response;
    }

    public static Response createGetRequestSingleQueryParam(String paramKey, String paramValue, RequestSpecification requestSpecification,
                                                            PrintStream captor, String path, String jsonSchemaFile, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParam(paramKey, paramValue)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    // Overridden method for -ve test of invalid end point in mobile exits api , by adding status code
    public static Response createGetRequestSingleQueryParam(String paramKey, String paramValue, RequestSpecification requestSpecification,
                                                            PrintStream captor, String path, String jsonSchemaFile, StringWriter writer,int statusCode ) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParam(paramKey, paramValue)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    // Overridden method for -ve test of invalid end point in mobile exits api , by adding status code
    public static Response createGetRequestSingleQueryParam(String paramKey, String paramValue, RequestSpecification requestSpecification,
                                                            PrintStream captor, String path,  Map<String, String> headers,String jsonSchemaFile,
                                                            StringWriter writer,int statusCode ) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParam(paramKey, paramValue)
                .headers(headers)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    public static Response createGetRequestSingleQueryParam(String paramKey, Object paramValue, RequestSpecification requestSpecification,
                                                            PrintStream captor, String path, Map<String, String> headers, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParam(paramKey, paramValue)
                .headers(headers)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        return response;
    }

    public static Response createGetRequestSingleQueryParam(String paramKey, Object paramValue, RequestSpecification requestSpecification,
                                                            PrintStream captor, String path, Map<String, String> headers, String jsonSchemaFile, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParam(paramKey, paramValue)
                .headers(headers)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    public static Response createGetRequestSingleQueryParam(String paramKey, Object paramValue, RequestSpecification requestSpecification,
                                                            PrintStream captor, String path, Map<String, String> headers, int statusCode, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .queryParam(paramKey, paramValue)
                .urlEncodingEnabled(false)
                .headers(headers)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        return response;
    }

    public static Response createGetRequest(RequestSpecification requestSpecification,
                                            PrintStream captor, String path, Map<String, String> headers) {
        return RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .headers(headers)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
    }

    public static Response createGetRequest(RequestSpecification requestSpecification,
                                            PrintStream captor, String path,
                                            Map<String, String> headers, String jsonSchemaFile, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .headers(headers)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .get(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    public static Response createPostRequest(RequestSpecification requestSpecification, PrintStream captor,
                                             Object requestObject, String path, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(requestObject)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .post(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());


        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        return response;
    }

    public static Response createPostRequest(RequestSpecification requestSpecification, PrintStream captor,
                                             Object requestObject, String path, String jsonSchemaFile,
                                             StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(requestObject)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .post(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    public static Response createPostRequest(RequestSpecification requestSpecification, PrintStream captor,
                                             Object requestObject, String path, Map<String, String> headers,
                                             StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestObject)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .post(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response();
        return response;
    }

    public static Response createPostRequest(RequestSpecification requestSpecification, PrintStream captor,
                                             Object requestObject, String path, Map<String, String> headers,
                                             String jsonSchemaFile, StringWriter writer) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestObject)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .post(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    // Overridden method with status code for -ve testing of simple login
    public static Response createPostRequest(RequestSpecification requestSpecification, PrintStream captor,
                                             Object requestObject, String path, String jsonSchemaFile,
                                             StringWriter writer,int statusCode) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(requestObject)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .post(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }

    // Overridden method with status code for -ve testing of simple login
    public static Response createPostRequest(RequestSpecification requestSpecification, PrintStream captor,
                                             Object requestObject, String path, Map<String, String> headers,
                                             String jsonSchemaFile, StringWriter writer,int statusCode) {
        Response response = RestAssured.given()
                .filter(new RequestLoggingFilter(captor))
                .spec(requestSpecification)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(requestObject)
                .urlEncodingEnabled(false)
                .log().all()
                .when()
                .post(path);

        CommonHelper.writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        response.then()
                .statusCode(statusCode)
                .contentType(ContentType.JSON)
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaFile))
                .extract().response();
        return response;
    }


    public static Map<String, String> setHeaders(String key1, String value1, String key2, String value2,
                                                 String key3, String value3) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(key1, value1);
        requestHeaders.put(key2, value2);
        requestHeaders.put(key3, value3);
        return requestHeaders;
    }

    public static Map<String, String> setHeaders(String key1, String value1, String key2, String value2) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(key1, value1);
        requestHeaders.put(key2, value2);
        return requestHeaders;
    }

    public static Map<String, String> setHeaders(String key1, String value1) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(key1, value1);
        return requestHeaders;
    }

    public static Map<String, String> setQueryParams(String key1, String value1, String key2,
                                                     String value2, String key3, String value3) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(key1, value1);
        queryParams.put(key2, value2);
        queryParams.put(key3, value3);
        return queryParams;
    }

    public static Map<String, String> setQueryParams(String key1, String value1, String key2,
                                                     String value2) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(key1, value1);
        queryParams.put(key2, value2);
        return queryParams;
    }

    public static Map<String, String> setQueryParams(String key1, String value1, String key2,
                                                     String value2, String key3, String value3,
                                                     String key4, String value4) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(key1, value1);
        queryParams.put(key2, value2);
        queryParams.put(key3, value3);
        queryParams.put(key4, value4);
        return queryParams;
    }

    public static Map<String, String> setQueryParams(String key1, String value1, String key2,
                                                     String value2, String key3, String value3,
                                                     String key4, String value4,
                                                     String key5, String value5) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(key1, value1);
        queryParams.put(key2, value2);
        queryParams.put(key3, value3);
        queryParams.put(key4, value4);
        queryParams.put(key5, value5);
        return queryParams;
    }

    public static Map<String, String> createInputBody(String key1, String value1, String key2, String value2) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(key1, value1);
        requestBody.put(key2, value2);
        return requestBody;
    }

    public static Map<String, String> createInputBody(String key1, String value1, String key2, String value2,
                                                      String key3, String value3) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(key1, value1);
        requestBody.put(key2, value2);
        requestBody.put(key3, value3);
        return requestBody;
    }
}