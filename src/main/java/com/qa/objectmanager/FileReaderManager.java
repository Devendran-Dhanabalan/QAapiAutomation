package com.qa.objectmanager;

import com.qa.utilities.ConfigReader;

public class FileReaderManager {
    private static FileReaderManager fileReaderManager = new FileReaderManager();
    private static ConfigReader configReader;

    public FileReaderManager() {
    }

    public static FileReaderManager getInstance() {
        return fileReaderManager;
    }

    public ConfigReader getConfigReader(String filePath) {
        return (configReader == null) ? new ConfigReader(filePath) : configReader;
    }
}
