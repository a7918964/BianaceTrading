package com.example;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final String CONFIG_FILE = "config.txt"; // 使用 .txt 文件擴展名
    private static final Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getApiKey() {
        return properties.getProperty("api.key");
    }

    public static String getApiSecret() {
        return properties.getProperty("api.secret");
    }
}
