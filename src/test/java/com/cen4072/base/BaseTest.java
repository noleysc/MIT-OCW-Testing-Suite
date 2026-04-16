package com.cen4072.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;
    protected static final String BASE_URL = "https://ocw.mit.edu/";

    @BeforeClass
    public static void setUp() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        driver.get(BASE_URL);
    }

    public void navigateHome() {
        driver.get(BASE_URL);
    }

    protected WebDriverWait waitUpTo(Duration duration) {
        return new WebDriverWait(driver, duration);
    }

    protected void openRelativePath(String pathStartingWithSlash) {
        String root = BASE_URL.endsWith("/") ? BASE_URL.substring(0, BASE_URL.length() - 1) : BASE_URL;
        driver.get(root + pathStartingWithSlash);
    }

    protected void waitForSearchResultsLoaded() {
        waitUpTo(Duration.ofSeconds(30)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-page a[href*='/courses/']")));
    }

    protected int countCourseResultLinksInSearchPage() {
        return driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size();
    }

    protected Object runScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
