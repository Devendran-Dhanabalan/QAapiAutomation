package com.qa.utilities;

import com.qa.reports.LogStatus;
import io.restassured.response.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.Assert;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AssertionHelper {
    public AssertionHelper() {
    }

    public static void assertAll(String input, String output) {
        try {
            JSONAssert.assertEquals(input, output, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertAll(String input, String output, String strict) {
        try {
            JSONAssert.assertEquals(input, output, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertValue(String actual, String expected) {
        try {
            String message = "Expected value: " + expected + " not matched with actual value: " + actual;
            assertEquals(actual, expected, message);
        } catch (JSONException e) {
        }
    }

    public static void assertFieldValue(String actual, String expected, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + "\n\n" +
                    " | Expected value: '" + CommonHelper.bold(expected) + "' not matched with actual value: '"
                    + CommonHelper.bold(actual) + "'" + "\n\n";
            assertEquals(actual, expected, message);
            LogStatus.pass("Field: '" + CommonHelper.bold(field) + "'|" + "\n\n" + " Actual value: '"
                    + CommonHelper.bold(actual) + "' is equal to expected value: '"
                    + CommonHelper.bold(expected) + "'" + "\n\n");
        } catch (JSONException e) {
        }
    }

    public static void assertString(String actual, String expected, String error_message) {
        assertEquals(actual, expected, error_message);
    }

    public static void assertInt(Integer actual, Integer expected, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + " |Actual value: '" + CommonHelper.bold(actual.toString()) +
                    "' is not equal to expected value: '" + CommonHelper.bold(expected.toString()) + "' |" + "\n\n";
            assertEquals(actual, expected, message);
            LogStatus.pass("Field: '" + CommonHelper.bold(field) + "'" + " Actual value: '" + CommonHelper.bold(actual.toString()) +
                    "' is equal to expected value: '"
                    + CommonHelper.bold(expected.toString()) + "'" + "\n\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertLong(Long actual, Long expected, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + " |Actual value: '" + CommonHelper.bold(actual.toString()) +
                    "' is not equal to expected value: '" + CommonHelper.bold(expected.toString()) + "' |" + "\n\n";
            assertEquals(actual, expected, message);
            LogStatus.pass("Field: '" + CommonHelper.bold(field) + "'" + " Actual value: '" + CommonHelper.bold(actual.toString()) +
                    "' is equal to expected value: '"
                    + CommonHelper.bold(expected.toString()) + "'" + "\n\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertContains(String actual, String expected) {
        try {
            String message = "Actual value: '" + actual + "' doesn't contains expected value: '" + expected + "'";
            assertTrue(actual.contains(expected), message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertContains(String actual, String expected, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + " |Actual value: '" + CommonHelper.bold(actual) +
                    "' doesn't contains expected value: '" + CommonHelper.bold(expected) + "' |" + "\n\n";
            assertTrue(actual.contains(expected), message);
            LogStatus.pass("Field: '" + CommonHelper.bold(field) + "'|Actual value: '" + CommonHelper.bold(actual) + "' contains the expected value: '"
                    + CommonHelper.bold(expected) + "'");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertNotNull(String actual, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + "\n\n" + " |Expected not null however, Actual value: " +
                    CommonHelper.bold(actual) + " is null| " + "\n\n";
            assertTrue(!actual.isEmpty(), message);
            LogStatus.pass("Field: " + CommonHelper.bold(field) + " value is '" + CommonHelper.bold("not null") + "' as expected");
        } catch (JSONException e) {
        }
    }

    public static void assertNull(String actual, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + "\n\n" + " Expected " +
                    CommonHelper.bold("null") + " however, Actual value: " +
                    CommonHelper.bold(actual);
            Assert.assertNull(actual, message + "\n\n" + new Exception().getMessage());
            LogStatus.pass("Field:" + CommonHelper.bold(field) + " value is null as expected");
        } catch (JSONException e) {
        }
    }

    public static void assertValue(String input, String key, String value) {
        try {
            JSONObject inputJson = new JSONObject(input);
            String Key = inputJson.get(key).toString();
            assertEquals(Key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertNullArray(String[] array, String field) {
        try {
            String message = "Field: '" + field + "'" + " Expected null however, Actual value is not null " + "\n\n";
            assertTrue(ArrayUtils.isEmpty(array), message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertEmpty(String actual, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + "\n\n" +
                    " Expected " + CommonHelper.bold("empty") + " however, Actual value: " +
                    CommonHelper.bold(actual);
            assertTrue(actual.isEmpty(), message + "\n\n" + new Exception().getMessage());
            LogStatus.pass("Field:" + CommonHelper.bold(field) + " value is blank as expected");
        } catch (JSONException e) {
        }
    }

    public static void assertTime(Response response, String expected, String field) {
        try {
            Long actual = response.getTime();
            String message = "Field: '" + CommonHelper.bold(field) + "'" + "\n\n" +
                    "Actual value: '" + actual + "' is more than expected value: '"
                    + CommonHelper.bold(expected) + "'" + "\n\n";
            if (actual > Long.parseLong(expected)) {
                LogStatus.warning(message);
            } else {
                LogStatus.pass("Field: '" + CommonHelper.bold(field) + "'" + "\n\n" +
                        "Actual value: '" + actual + "' is less than expected value: '"
                        + CommonHelper.bold(expected) + "' as exoected" + "\n\n");
            }
        } catch (JSONException e) {
        }
    }

    public static void assertArrayContains(String actual, String[] expected, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + " |Actual value: '" + CommonHelper.bold(actual) +
                    "' doesn't contains expected value: '" + CommonHelper.bold(expected.toString()) + "' |" + "\n\n";
            assertTrue(Arrays.asList(expected).contains(actual), message);
            LogStatus.pass("Field: '" + CommonHelper.bold(field) + "'|Actual value: '" + CommonHelper.bold(actual) + "' contains the expected value: '"
                    + CommonHelper.bold(expected.toString()) + "'");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void assertCondition(boolean condition, String field) {
        try {
            String message = "Field: '" + CommonHelper.bold(field) + "'" + " |is false";
            assertTrue(condition, message);
            LogStatus.pass("Field: '" + CommonHelper.bold(field) + "is true");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
