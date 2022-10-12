package com.qa.dataprovider;

public class DataProvider {

    public static String invalidCuser = "9751";
    public static String invalidCpassword = "9751";

    @org.testng.annotations.DataProvider(name = "invalidCuserid")
    public static Object[][] invalidCuserid() {
        return new Object[][]{
                {invalidCuser}
        };
    }

    @org.testng.annotations.DataProvider(name = "invalidCpassword")
    public static Object[][] invalidMobileNumber() {
        return new Object[][]{
                {invalidCpassword}
        };
    }
}
