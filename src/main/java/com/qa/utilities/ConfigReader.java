package com.qa.utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    public ConfigReader(String filePath) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            prop = new Properties();
            try {
                prop.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("properties file not found at -" + filePath);
        }
    }

    public static String getPropertyValue(String propertyName) {
        String value = prop.getProperty(propertyName);
        if (value != null)
            return value;
        else
            throw new RuntimeException(
                    propertyName + " - is not specified in the Configuration.properties file");
    }
}
