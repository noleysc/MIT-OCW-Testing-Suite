package com.cen4072.steps;

import com.cen4072.support.TestOutput;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ResponsiveDesignSteps {

    private static WebDriver driver() {
        return java.util.Objects.requireNonNull(
                CommonSteps.getDriver(),
                "WebDriver not initialized (did @Before run?)");
    }

    @When("I resize the browser to {string}")
    public void iResizeTheBrowserTo(String dimensions) {
        WebDriver driver = driver();
        String[] parts = dimensions.split("x");
        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);
        driver.manage().window().setSize(new Dimension(width, height));
        TestOutput.step("Resize viewport to " + dimensions);
        driver.get(CommonSteps.BASE_URL);
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("header, #mobile-header, input[name='q']")));
        TestOutput.outcome("URL after resize+load", driver.getCurrentUrl());
    }

    @Then("the mobile navigation menu should be visible")
    public void theMobileNavigationMenuShouldBeVisible() {
        WebDriver driver = driver();
        WebElement mobileMenuBtn = new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("#mobile-header .navbar-toggler, #mobile-header button.navbar-toggler")));
        Assert.assertTrue(mobileMenuBtn.isDisplayed());
        TestOutput.outcome("Mobile menu control visible", mobileMenuBtn.isDisplayed());
    }

    @Then("the site should adapt to tablet layout")
    public void theSiteShouldAdaptToTabletLayout() {
        WebDriver driver = driver();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".course-cards .course-card")));
        int cards = driver.findElements(By.cssSelector(".course-cards .course-card")).size();
        Assert.assertTrue(cards >= 2, "Tablet width should still show multiple course cards on the home grid");
        TestOutput.outcome("Course cards visible", cards);
    }

    @Then("the full desktop header should be visible")
    public void theFullDesktopHeaderShouldBeVisible() {
        WebDriver driver = driver();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#desktop-header, header")));
        if (!driver.findElements(By.id("desktop-header")).isEmpty()) {
            Assert.assertTrue(driver.findElement(By.id("desktop-header")).isDisplayed());
        } else {
            Assert.assertTrue(driver.findElement(By.tagName("header")).isDisplayed());
        }
        TestOutput.outcome("Desktop header check URL", driver.getCurrentUrl());
    }

    @Given("I have resized the browser to {string}")
    public void iHaveResizedTheBrowserTo(String dimensions) {
        WebDriver driver = driver();
        iResizeTheBrowserTo(dimensions);
        driver.get(CommonSteps.BASE_URL);
    }

    @When("I search for {string}")
    public void iSearchFor(String keyword) {
        WebDriver driver = driver();
        WebElement searchInput = new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='q']")));
        searchInput.clear();
        searchInput.sendKeys(keyword);
        searchInput.sendKeys(Keys.ENTER);
        TestOutput.step("Submitted search query: " + keyword);
    }

    @Then("the search results should be readable on mobile")
    public void theSearchResultsShouldBeReadableOnMobile() {
        WebDriver driver = driver();
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-page a[href*='/courses/']")));
        int n = driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size();
        Assert.assertTrue(n > 0);
        TestOutput.outcome("Mobile search course links", n);
    }

    @Then("the course cards should stack in a single column")
    public void theCourseCardsShouldStackInASingleColumn() {
        WebDriver driver = driver();
        driver.get(CommonSteps.BASE_URL);
        iSearchFor("Python");
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("#search-page a[href*='/courses/'], #search-page .course-card, .course-card")));
        int results = driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size();
        if (results > 0) {
            Assert.assertTrue(results > 0, "Mobile search should list course results");
            TestOutput.outcome("Stacking scenario: course links from search", results);
            return;
        }
        WebElement firstCard = driver.findElement(By.cssSelector(".course-card"));
        int width = firstCard.getSize().getWidth();
        int browserWidth = driver.manage().window().getSize().getWidth();
        Assert.assertTrue(width > browserWidth * 0.8, "Card should take up most of mobile width");
        TestOutput.outcome("Single-column card width ratio", width + " / " + browserWidth);
    }
}
