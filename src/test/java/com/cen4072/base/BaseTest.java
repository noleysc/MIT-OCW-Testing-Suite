package com.cen4072.base;

import com.cen4072.support.TestOutput;
import com.cen4072.support.TestPacing;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.cen4072.support.ChromeTestOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;
    protected static final String BASE_URL = "https://ocw.mit.edu/";
    /** Avoid a full home reload before every TestNG class (removes long gaps between class groups). */
    private static boolean initialHomeLoaded;

    @BeforeClass
    public static void setUp() {
        if (driver == null) {
            TestOutput.step("Starting Chrome WebDriver session (shared across suites)");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(ChromeTestOptions.forAutomation());
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
            // Must stay 0 when using WebDriverWait — a non-zero implicit wait slows every poll and feels "random".
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            Runtime.getRuntime().addShutdownHook(new Thread(BaseTest::quitDriver, "ocw-driver-shutdown"));
            initialHomeLoaded = false;
        }
        if (!initialHomeLoaded) {
            initialHomeLoaded = true;
            if (TestOutput.traceNav()) {
                TestOutput.step("Loading base URL (once per suite): " + BASE_URL);
            }
            driver.get(BASE_URL);
            if (TestOutput.traceNav()) {
                TestOutput.outcome("Initial page URL", driver.getCurrentUrl());
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void pauseBetweenTestMethods() {
        TestPacing.pauseBetweenTests();
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

    public void navigateHome() {
        if (TestOutput.traceNav()) {
            TestOutput.step("Navigate home: " + BASE_URL);
        }
        driver.get(BASE_URL);
        if (TestOutput.traceNav()) {
            TestOutput.outcome("Current URL", driver.getCurrentUrl());
        }
    }

    protected WebDriverWait waitUpTo(Duration duration) {
        return new WebDriverWait(driver, duration);
    }

    protected void openRelativePath(String pathStartingWithSlash) {
        String root = BASE_URL.endsWith("/") ? BASE_URL.substring(0, BASE_URL.length() - 1) : BASE_URL;
        String fullUrl = root + pathStartingWithSlash;
        if (TestOutput.traceNav()) {
            TestOutput.step("Navigate to: " + fullUrl);
        }
        driver.get(fullUrl);
        if (TestOutput.traceNav()) {
            TestOutput.outcome("Current URL", driver.getCurrentUrl());
        }
    }

    protected void waitForSearchResultsLoaded() {
        waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-page a[href*='/courses/']")));
    }

    protected int countCourseResultLinksInSearchPage() {
        return driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size();
    }

    protected Object runScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    protected void logStep(String message) {
        TestOutput.step(message);
    }

    protected void logOutcome(String label, Object value) {
        TestOutput.outcome(label, value);
    }
}
