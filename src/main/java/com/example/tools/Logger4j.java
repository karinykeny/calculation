package com.example.tools;

import com.example.domain.DataCalculation;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public  class Logger4j {

    private static final Properties PROPERTIES = new Properties();
    private static final String LOG_FILE = "log4j.properties";

    public static void logger() throws IOException {
        Logger LOGGER = Logger.getLogger(DataCalculation.class);
        PROPERTIES.load(new FileInputStream(LOG_FILE));
        PropertyConfigurator.configure(PROPERTIES);
    }
}
