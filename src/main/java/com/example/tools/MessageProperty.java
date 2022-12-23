package com.example.tools;

import java.io.IOException;
import java.util.Properties;

public class MessageProperty {

    private static Properties properties;

    public static final String MESSAGE_SAVE_SUCCESS = "calculation.message.seve.success";
    public static final String MESSAGE_NOT_FOUND = "calculation.message.not.found";
    public static final String MESSAGE_ERROR_EDIT = "calculation.message.error.edit";
    public static final String MESSAGE_ERROR_TYPE_INTERNAL = "calculation.message.type.financing.internal";

    public static String getMessage(String key) {
        try {
            if (properties == null) {
                properties = new Properties();
                properties.load(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("calculation.properties"));
            }
        } catch (IOException e) {
            return "Arquivo não encontrado";
        }
        return properties.getProperty(key);
    }
}
