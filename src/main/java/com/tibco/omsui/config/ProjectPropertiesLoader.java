package com.tibco.omsui.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ProjectPropertiesLoader {

    public static Properties projectProperties(){
        String propertyFileName = "C:\\Users\\MudassarDastagir.Haw\\UIAutomation\\src\\main\\resources\\application.properties";
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(propertyFileName);
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}
