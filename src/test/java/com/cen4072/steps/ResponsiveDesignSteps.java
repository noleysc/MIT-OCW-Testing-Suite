package com.cen4072.steps;

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

    WebDriver driver = CommonSteps.getDriver();

    @When("I resize the browser to {string}")
    public void iResizeTheBrowserTo(String dimensions) {
        String[] parts = dimensions.split("x");
        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);
        driver.manage().window().setSize(new Dimension(width, height));
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    @Then("the mobile navigation menu should be visible")
    public void theMobileNavigationMenuShouldBeVisible() {
        WebElement mobileMenuBtn = driver.findElement(By.cssSelector("button.navbar-toggler"));
        Assert.assertTrue(mobileMenuBtn.isDisplayed());
    }

    @Then("the site should adapt to tablet layout")
    public void theSiteShouldAdaptToTabletLayout() {
        WebElement desktopHeader = driver.findElement(By.id("desktop-header"));
        Assert.assertFalse(desktopHeader.isDisplayed());
    }

    @Then("the full desktop header should be visible")
    public void theFullDesktopHeaderShouldBeVisible() {
        WebElement desktopHeader = driver.findElement(By.id("desktop-header"));
        Assert.assertTrue(desktopHeader.isDisplayed());
    }

    @Given("I have resized the browser to {string}")
    public void iHaveResizedTheBrowserTo(String dimensions) {
        iResizeTheBrowserTo(dimensions);
        driver.get(CommonSteps.BASE_URL);
    }

    @When("I search for {string}")
    public void iSearchFor(String keyword) {
        WebElement searchInput = driver.findElement(By.name("q"));
        searchInput.sendKeys(keyword);
        searchInput.sendKeys(Keys.ENTER);
    }

    @Then("the search results should be readable on mobile")
    public void theSearchResultsShouldBeReadableOnMobile() {
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-page a[href*='/courses/']")));
        Assert.assertTrue(driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size() > 0);
    }

    @Then("the course cards should stack in a single column")
    public void theCourseCardsShouldStackInASingleColumn() {
        iSearchFor("Python");
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".course-card")));
        WebElement firstCard = driver.findElement(By.cssSelector(".course-card"));
        int width = firstCard.getSize().getWidth();
        int browserWidth = driver.manage().window().getSize().getWidth();
        Assert.assertTrue(width > browserWidth * 0.8, "Card should take up most of mobile width");
    }
}
