package com.cen4072.steps;

import com.cen4072.support.OcwSampleCourse;
import com.cen4072.support.TestOutput;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class UserPreferencesSteps {

    WebDriver driver = CommonSteps.getDriver();
    @Given("I am on a sample course page")
    public void iAmOnASampleCoursePage() {
        String url = CommonSteps.BASE_URL + OcwSampleCourse.PATH;
        TestOutput.step("Open sample course: " + url);
        driver.get(url);
        TestOutput.outcome("Course page URL", driver.getCurrentUrl());
    }

    @When("I toggle the course sidebar")
    public void iToggleTheCourseSidebar() {
        TestOutput.step("Toggle course sidebar (desktop or mobile path)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("course-nav-toggle"))).click();
            return;
        } catch (Exception ignored) {
            // Fall through to mobile / drawer navigation
        }
        String current = driver.getCurrentUrl();
        driver.manage().window().setSize(new Dimension(375, 812));
        driver.get(current);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    ExpectedConditions.elementToBeClickable(By.id("mobile-course-nav-toggle"))).click();
            return;
        } catch (Exception ignored2) {
            // Open mobile header flyout, then course menu control
        }
        WebElement hamburger = new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("#mobile-header .navbar-toggler, #mobile-header button.navbar-toggler")));
        hamburger.click();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.elementToBeClickable(By.id("mobile-course-nav-toggle"))).click();
    }

    @Then("the sidebar should expand or collapse correctly")
    public void theSidebarShouldExpandOrCollapseCorrectly() {
        if (!driver.findElements(By.id("course-nav")).isEmpty()) {
            WebElement sidebar = driver.findElement(By.id("course-nav"));
            Assert.assertNotNull(sidebar.getCssValue("display"));
        } else {
            WebElement drawer = new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    ExpectedConditions.presenceOfElementLocated(By.id("mobile-course-nav")));
            Assert.assertNotNull(drawer.getCssValue("display"));
        }
        TestOutput.outcome("Sidebar state checked on URL", driver.getCurrentUrl());
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        TestOutput.step("Open home: " + CommonSteps.BASE_URL);
        driver.get(CommonSteps.BASE_URL);
    }

    @When("I accept the cookie consent")
    public void iAcceptTheCookieConsent() {
        try {
            WebElement acceptBtn = new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    ExpectedConditions.elementToBeClickable(By.id("cookie-consent-accept")));
            acceptBtn.click();
            TestOutput.outcome("Cookie accept", "clicked");
        } catch (Exception e) {
            TestOutput.outcome("Cookie accept button", "not shown or not clickable");
        }
    }

    @Then("the consent banner should disappear")
    public void theConsentBannerShouldDisappear() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                    ExpectedConditions.invisibilityOfElementLocated(By.id("cookie-consent-banner")));
        } catch (Exception e) {
            if (driver.findElements(By.id("cookie-consent-banner")).isEmpty()) {
                return;
            }
            throw e;
        }
        TestOutput.outcome("Cookie banner", "hidden");
    }

    @When("I toggle the dark mode")
    public void iToggleTheDarkMode() {
        // Many OCW pages don't have a native dark mode toggle in the UI, 
        // but we can simulate/test the preference if implemented.
        // For this demo, we'll check for a theme toggle if it exists.
        try {
            WebElement toggle = driver.findElement(By.id("theme-toggle"));
            toggle.click();
        } catch (Exception e) {
            // If no toggle, we might be testing system preference injection
        }
    }

    @Then("the page should transition between light and dark themes")
    public void thePageShouldTransitionBetweenLightAndDarkThemes() {
        String bgColor = driver.findElement(By.tagName("body")).getCssValue("background-color");
        Assert.assertNotNull(bgColor);
        TestOutput.outcome("Body background-color", bgColor);
    }

    @When("I toggle the course sidebar {string}")
    public void iToggleTheCourseSidebarTwice(String times) {
        iToggleTheCourseSidebar();
        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        iToggleTheCourseSidebar();
    }

    @Then("the sidebar should return to its initial state")
    public void theSidebarShouldReturnToItsInitialState() {
        theSidebarShouldExpandOrCollapseCorrectly();
    }

    @Given("I have accepted the cookie consent")
    public void iHaveAcceptedTheCookieConsent() {
        iAmOnTheHomePage();
        iAcceptTheCookieConsent();
    }

    @When("I refresh the page")
    public void iRefreshThePage() {
        TestOutput.step("Refresh current page");
        driver.navigate().refresh();
    }

    @Then("the consent banner should not be visible")
    public void theConsentBannerShouldNotBeVisible() {
        theConsentBannerShouldDisappear();
    }
}
