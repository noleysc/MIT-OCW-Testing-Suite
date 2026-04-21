package com.cen4072.steps;

import com.cen4072.support.OcwSampleCourse;
import com.cen4072.support.TestOutput;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Objects;

public class ResourceTypesSteps {

    private static WebDriver driver() {
        return Objects.requireNonNull(CommonSteps.getDriver(), "WebDriver not initialized (run @Before first)");
    }

    @Given("I search for {string} resource type")
    public void iSearchForResourceType(String resourceType) {
        WebDriver driver = driver();
        String urlType = resourceType.replace(" ", "+");
        String url = CommonSteps.BASE_URL + "search/?resource_type=" + urlType;
        TestOutput.step("Resource type search: " + url);
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-page a[href*='/courses/']")));
        TestOutput.outcome("Course links for resource_type", driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size());
    }

    @Then("I should see course results listed for textbooks")
    public void iShouldSeeCourseResultsListedForTextbooks() {
        WebDriver driver = driver();
        int n = driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size();
        Assert.assertTrue(n > 0);
        TestOutput.outcome("Textbooks filter course links", n);
    }

    @Then("I should see course results listed for simulations")
    public void iShouldSeeCourseResultsListedForSimulations() {
        WebDriver driver = driver();
        int n = driver.findElements(By.cssSelector("#search-page a[href*='/courses/']")).size();
        Assert.assertTrue(n > 0, "Simulations filter should list course results");
        TestOutput.outcome("Simulations filter course links", n);
        String sampleUrl = CommonSteps.BASE_URL + OcwSampleCourse.PATH;
        TestOutput.step("Open canonical sample course (6.006 Spring 2020): " + sampleUrl);
        driver.get(sampleUrl);
        Assert.assertTrue(
                driver.getCurrentUrl().contains("6-006-introduction-to-algorithms-spring-2020"),
                "Browser should land on the same sample course used elsewhere in the suite");
        TestOutput.outcome("Sample course URL", driver.getCurrentUrl());
    }

    @Given("I am on the download page for a sample course")
    public void iAmOnTheDownloadPageForASampleCourse() {
        WebDriver driver = driver();
        String url = CommonSteps.BASE_URL + OcwSampleCourse.PDF_RESOURCE;
        TestOutput.step("Open PDF resource page: " + url);
        driver.get(url);
    }

    @Then("I should find a valid PDF resource link")
    public void iShouldFindAValidPDFResourceLink() {
        WebDriver driver = driver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/resources/"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        String src = driver.getPageSource();
        String lower = src.toLowerCase();
        boolean looksLikeResourcePage =
                lower.contains("problem set")
                        || lower.contains("resource type")
                        || lower.contains("mit6_006s20")
                        || lower.contains("ps0-questions")
                        || lower.contains("introduction to algorithms");
        Assert.assertTrue(
                looksLikeResourcePage,
                "Resource page should load recognizable course/resource content (not a blank or error shell)");
        Assert.assertTrue(
                lower.contains("pdf")
                        || driver.findElements(By.cssSelector("a[href*='.pdf'], iframe, embed")).size() > 0,
                "Resource page should reference PDF materials or embed a viewer");
        TestOutput.outcome("PDF resource URL", driver.getCurrentUrl());
    }

    @Given("I navigate to the assignments page of a sample course")
    public void iNavigateToTheAssignmentsPageOfASampleCourse() {
        WebDriver driver = driver();
        String url = CommonSteps.BASE_URL + OcwSampleCourse.PATH + "pages/assignments/";
        TestOutput.step("Open assignments: " + url);
        driver.get(url);
    }

    @Then("I should see course assignment content")
    public void iShouldSeeCourseAssignmentContent() {
        WebDriver driver = driver();
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("assignment"));
        TestOutput.outcome("Assignments URL", driver.getCurrentUrl());
    }

    @Given("I am on the main page of a sample course")
    public void iAmOnTheMainPageOfASampleCourse() {
        WebDriver driver = driver();
        String url = CommonSteps.BASE_URL + OcwSampleCourse.PATH;
        TestOutput.step("Open sample course home: " + url);
        driver.get(url);
    }

    @Then("I should see {string} listed as a resource")
    public void iShouldSeeListedAsAResource(String resourceName) {
        WebDriver driver = driver();
        Assert.assertTrue(driver.getPageSource().contains(resourceName));
        TestOutput.outcome("Resource label found on page", resourceName);
    }
}
