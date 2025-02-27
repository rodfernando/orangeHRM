package com.orangehrm.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseClass {

    //Protected -> used in the same package or in another class that extends this class
    protected static Properties prop;
    protected static WebDriver driver;


    @BeforeSuite
    //Load file's configuration
    public void loadConfig() throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        prop.load(fis);
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j2.xml");
        System.out.println("Log4j2 Configuration File: " + System.getProperty("log4j.configurationFile"));
        System.out.println("Classpath: " + System.getProperty("java.class.path"));
    }

    @BeforeMethod
    public void setup() {
        System.out.println("Setting up WebDriver: " + this.getClass().getSimpleName());
        launchBrowser();
        configBrowser();
        staticWait(2);
    }

    //Initialize the WebDriver based on browser defined in config.properties file
    private void launchBrowser() {
        String browser = prop.getProperty("browser");
        switch (browser.toLowerCase()) {
            case "chrome" -> driver = new ChromeDriver();
            case "firefox" -> driver = new FirefoxDriver();
            default -> throw new IllegalArgumentException("Browser Not Supported: " + browser);
        }
    }

    private void configBrowser() {
        //Global implicit wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().window().maximize();
        try {
            driver.get(prop.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Failed to navigate to the URL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Unable to quit the driver: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    //Static wait for pause - it seems like Thread sleep, but more flexible
    public void staticWait(int seconds){
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }

}
