package com.tibco.omsui.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

public class Utility {
    public static WebDriver driver;
    private static Workbook workbook;

    public WebDriver launchBrowser(String browserType, String url) {
        if (browserType.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--headless");
            driver = new ChromeDriver(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }
        initializeDriver(url);
        return driver;
    }

    private void initializeDriver(String url) {
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.get(url);
        }
    }

    public static void loadExcelFile(String filePath){
        try{
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCellData(String sheetName, int rowNum, int cellNum){
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        return row.getCell(cellNum).getStringCellValue();
    }

    /*public void sendText(String locatorType, String inputValue, String locatorValue) {
        WebElement element = findElement(locatorType, locatorValue);
        if (element != null) {
            element.sendKeys(inputValue);
        }
    }*/

    /*public void clickButton(String locatorType, String locatorValue) {
        WebElement element = findElement(locatorType, locatorValue);
        if (element != null) {
            element.click();
        }
    }*/

    public void waitForElementVisibility(WebElement element) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.visibilityOf(element));
    }

    public String validateTitleOfThePage() {
        String title = driver.getTitle();
        System.out.println(title);
        return title;
    }

    public void waitForPageTitle(String expectedTitle) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.titleIs(expectedTitle));
    }

    /*private WebElement findElement(String locatorType, String locatorValue) {
        return switch (locatorType.toLowerCase()) {
            case "id" -> driver.findElement(By.id(locatorValue));
            case "type" -> driver.findElement(By.xpath("//button[@type='" + locatorValue + "']"));
            case "name" -> driver.findElement(By.name(locatorValue));
            case "classname" -> driver.findElement(By.className(locatorValue));
            case "tagname" -> driver.findElement(By.tagName(locatorValue));
            default -> throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        };
    }*/
}
