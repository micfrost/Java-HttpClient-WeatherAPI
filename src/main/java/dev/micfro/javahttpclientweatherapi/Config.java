package dev.micfro.javahttpclientweatherapi;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;

public class Config {
    public static String getApiKey() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            prop.load(input);
            return prop.getProperty("api_key");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
