package com.cen4072.base;

import io.qameta.allure.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;
    protected static final String BASE_URL = "https://ocw.mit.edu/";

    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        driver.get(BASE_URL);
    }

    @Step("Navigate to MIT OCW Home Page")
    public void navigateHome() {
        driver.get(BASE_URL);
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
