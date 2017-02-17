package ru.artsok.util.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class FileHelper {

    public static String getFilePathToSave() {

        Properties prop = new Properties();
        String filePath = "";

        try {

            InputStream inputStream =
                    FileHelper.class.getClassLoader().getResourceAsStream("src/ru/artsok/resources/properties/test.txt");

            prop.load(inputStream);
            filePath = prop.getProperty("json.filepath");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePath;

    }

}


