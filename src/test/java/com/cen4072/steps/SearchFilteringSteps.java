package com.cen4072.steps;

import com.cen4072.support.TestOutput;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class SearchFilteringSteps {

    WebDriver driver = CommonSteps.getDriver();

    @Given("I am on the OCW search page")
    public void iAmOnTheOCWSearchPage() {
        String url = CommonSteps.BASE_URL + "search/";
        TestOutput.step("Open search: " + url);
        driver.get(url);
    }

    @When("I apply the {string} topic filter")
    public void iApplyTheTopicFilter(String topic) {
        String urlTopic = topic.replace(" ", "+");
        TestOutput.step("Apply topic filter URL t=" + urlTopic);
        driver.get(CommonSteps.BASE_URL + "search/?t=" + urlTopic);
        waitForSearchResultsLoaded();
        TestOutput.outcome("Topic filter course links", countCourseResultLinks());
    }

    @Then("I should see course results for {string}")
    public void iShouldSeeCourseResultsFor(String topic) {
        Assert.assertTrue(countCourseResultLinks() > 0, "Topic filter should return course results");
        Assert.assertTrue(driver.getPageSource().contains(topic) || driver.getCurrentUrl().contains(topic.replace(" ", "+")));
        TestOutput.outcome("Results URL", driver.getCurrentUrl());
    }

    @When("I apply the {string} level filter")
    public void iApplyTheLevelFilter(String level) {
        TestOutput.step("Apply level filter l=" + level);
        driver.get(CommonSteps.BASE_URL + "search/?l=" + level);
        waitForSearchResultsLoaded();
        TestOutput.outcome("Level filter course links", countCourseResultLinks());
    }

    @When("I apply the {string} department filter")
    public void iApplyTheDepartmentFilter(String dept) {
        TestOutput.step("Apply department filter d=" + dept);
        driver.get(CommonSteps.BASE_URL + "search/?d=" + dept);
        waitForSearchResultsLoaded();
        TestOutput.outcome("Department filter course links", countCourseResultLinks());
    }

    @Given("I have applied {string} topic and {string} level filters")
    public void iHaveAppliedTopicAndLevelFilters(String topic, String level) {
        TestOutput.step("Combined filters t=" + topic + " l=" + level);
        driver.get(CommonSteps.BASE_URL + "search/?t=" + topic + "&l=" + level);
        waitForSearchResultsLoaded();
        TestOutput.outcome("Combined filter course links", countCourseResultLinks());
    }

    @When("I click the \"Clear All\" button")
    public void iClickTheClearAllButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement clearAll = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Clear All')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", clearAll);
        clearAll.click();
        TestOutput.step("Clicked Clear All");
    }

    @Then("the active filters should be removed from the search")
    public void theActiveFiltersShouldBeRemovedFromTheSearch() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> {
            String u = d.getCurrentUrl();
            return !(u.contains("t=Engineering") && u.contains("l=Undergraduate"));
        });
        TestOutput.outcome("URL after filter clear", driver.getCurrentUrl());
    }

    @When("I apply {string} topic and {string} level filters")
    public void iApplyTopicAndLevelFilters(String topic, String level) {
        iHaveAppliedTopicAndLevelFilters(topic, level);
    }

    @Then("I should see results matching both {string} and {string}")
    public void iShouldSeeResultsMatchingBothAnd(String topic, String level) {
        Assert.assertTrue(countCourseResultLinks() > 0);
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains(topic) && url.contains(level));
        TestOutput.outcome("Multi-filter URL", url);
    }

    private void waitForSearchResultsLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-page a[href*='/courses/']")));
    }

    private int countCourseResultLinks() {
        return driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size();
    }
}
