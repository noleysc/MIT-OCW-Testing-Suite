package com.cen4072.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ResourceTypesSteps {

    WebDriver driver = CommonSteps.getDriver();
    private static final String SAMPLE_COURSE = "/courses/6-006-introduction-to-algorithms-fall-2011/";

    @Given("I search for {string} resource type")
    public void iSearchForResourceType(String resourceType) {
        String urlType = resourceType.replace(" ", "+");
        driver.get(CommonSteps.BASE_URL + "search/?resource_type=" + urlType);
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-page a[href*='/courses/']")));
    }

    @Then("I should see course results listed for textbooks")
    public void iShouldSeeCourseResultsListedForTextbooks() {
        Assert.assertTrue(driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size() > 0);
    }

    @Then("I should see course results listed for simulations")
    public void iShouldSeeCourseResultsListedForSimulations() {
        iShouldSeeCourseResultsListedForTextbooks();
    }

    @Given("I am on the download page for a sample course")
    public void iAmOnTheDownloadPageForASampleCourse() {
        driver.get(CommonSteps.BASE_URL + SAMPLE_COURSE + "download/");
    }

    @Then("I should find a valid PDF resource link")
    public void iShouldFindAValidPDFResourceLink() {
        WebElement pdfLink = new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='.pdf']")));
        Assert.assertNotNull(pdfLink.getAttribute("href"));
    }

    @Given("I navigate to the assignments page of a sample course")
    public void iNavigateToTheAssignmentsPageOfASampleCourse() {
        driver.get(CommonSteps.BASE_URL + SAMPLE_COURSE + "pages/assignments/");
    }

    @Then("I should see course assignment content")
    public void iShouldSeeCourseAssignmentContent() {
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("assignment"));
    }

    @Given("I am on the main page of a sample course")
    public void iAmOnTheMainPageOfASampleCourse() {
        driver.get(CommonSteps.BASE_URL + SAMPLE_COURSE);
    }

    @Then("I should see {string} listed as a resource")
    public void iShouldSeeListedAsAResource(String resourceName) {
        Assert.assertTrue(driver.getPageSource().contains(resourceName));
    }
}
