package com.orangehrm.base;

import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {

    protected Properties prop;
    protected WebDriver driver;

    //Protected -> used in the same package or in another class that extends this class

    public void setup() throws IOException {
        //Load file's configuration
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);

        //Initialize the WebDriver based on browser defined in config.properties file
        


    }

}
