package com.cen4072.steps;

import com.cen4072.support.TestOutput;
import com.cen4072.support.TestPacing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.cen4072.support.ChromeTestOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class CommonSteps {
    private static WebDriver driver;
    public static final String BASE_URL = "https://ocw.mit.edu/";

    /** Run before Serenity/Cucumber default hooks so {@link #getDriver()} is ready for the whole run. */
    @Before(order = 0)
    public void setup() {
        if (driver == null) {
            TestOutput.step("Cucumber setup: starting Chrome WebDriver (shared across scenarios)");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(ChromeTestOptions.forAutomation());
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            Runtime.getRuntime().addShutdownHook(new Thread(CommonSteps::quitDriver, "ocw-cucumber-shutdown"));
        }
    }

    @After(order = 10_000)
    public void pauseBetweenScenarios() {
        TestPacing.pauseBetweenTests();
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {
                // best-effort cleanup on JVM exit
            }
            driver = null;
        }
    }
}
