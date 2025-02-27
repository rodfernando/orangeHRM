package com.orangehrm.actiondriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class ActionDriver {
    private WebDriver driver;
    private WebDriverWait wait;

    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void click(By by) {
        try {
            waitForElementToBeClickable(by);
            driver.findElement(by).click();
        } catch (Exception e) {
            System.out.println("Unable to click on element: " + e.getMessage());
        }
    }

    public void enterText(By by, String value) {
        try {
            waitForElementToBeClickable(by);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            System.out.println("Unable to fulfill the text field: " + e.getMessage());
        }
    }

    public String getText(By by) {
        try {
            waitElementToBeVisible(by);
            return driver.findElement(by).getText();
        } catch (Exception e) {
            System.out.println("Unable to get the element text: " + e.getMessage());
            return "";
        }

    }

    private void waitForElementToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            System.out.println("Unable to click on element: " + e.getMessage());
        }
    }

    private void waitElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            System.out.println("Element is not visible: " + e.getMessage());
        }
    }

    public void compareText(By by, String expectedText) {
        waitElementToBeVisible(by);
        String actualText = getText(by);
        try {
            if (expectedText.equals(actualText)) {
                System.out.println("The assertion is matching: \nActual Text:" + actualText + "\n\nExpected Text: " + expectedText);
            } else {
                System.out.println("The assertion is not matching: \nActual Text:" + actualText + "\n\nExpected Text: " + expectedText);
            }
        } catch (Exception e) {
            System.out.println("Unable to compare texts: " + e.getMessage());
        }
    }

    public boolean isDisplayed(By by) {
        try {
            waitElementToBeVisible(by);
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            System.out.println("Element is not displayed: " + e.getMessage());
            return false;
        }
    }

    public void waitForPageLoad(int timeOutInSec) {
        try {
            this.wait.withTimeout(Duration.ofSeconds(timeOutInSec))
                    .until(driver -> Objects.equals(((JavascriptExecutor) driver)
                            .executeScript("return document.readyState"), "complete"));
            System.out.println("Page loaded successfully.");
        } catch (Exception e) {
            System.out.println("Page did not load within " + timeOutInSec + "seconds.\nException: " + e.getMessage());
        }
    }

    public void scrollToElement(By by) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println("Unable to scroll to element: " + e.getMessage());
        }
    }

}
