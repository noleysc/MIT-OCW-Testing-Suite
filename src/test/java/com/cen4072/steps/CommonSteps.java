package com.cen4072.steps;

import com.cen4072.support.TestOutput;
import com.cen4072.support.TestPacing;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import com.cen4072.support.ChromeTestOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
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
            driver.get(BASE_URL);
            dismissConsentBannerIfPresent(driver);
            Runtime.getRuntime().addShutdownHook(new Thread(CommonSteps::quitDriver, "ocw-cucumber-shutdown"));
        }
    }

    @After(order = 9_999)
    public void resetViewportBetweenScenarios() {
        if (driver != null) {
            try {
                // Reset to standard desktop size to avoid leaking mobile/tablet sizes
                driver.manage().window().setSize(new Dimension(1440, 900));
            } catch (Exception ignored) {
                // If the browser was closed or is unstable, ignore the error
            }
        }
    }

    private static void dismissConsentBannerIfPresent(WebDriver d) {
        try {
            // Broad XPath for common consent/accept buttons (case-insensitive translate)
            String xpath = "//button[" +
                    "contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'accept') or " +
                    "contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'got it') or " +
                    "contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'allow')" +
                    "]";
            d.findElements(org.openqa.selenium.By.xpath(xpath)).stream()
                    .filter(org.openqa.selenium.WebElement::isDisplayed)
                    .findFirst()
                    .ifPresent(org.openqa.selenium.WebElement::click);
        } catch (Exception ignored) {
            // Silent swallow as this is a best-effort convenience
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
