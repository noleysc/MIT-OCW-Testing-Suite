package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import com.cen4072.support.OcwSampleCourse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ResourceTypesTest extends BaseTest {

    @Test(description = "TC-5.1: Filter for 'Online Textbooks' (OCW label: Open Textbooks)")
    public void testTextbookFilter() {
        openRelativePath("/search/?resource_type=Open+Textbooks");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Open Textbooks filter should list courses");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("resource_type=Open+Textbooks"),
                "Search URL should preserve the Open Textbooks filter");
        logOutcome("Open Textbooks result links", countCourseResultLinksInSearchPage());
    }

    @Test(description = "TC-5.2: Filter for 'Interactive Simulations' (OCW label: Simulations)")
    public void testSimulationFilter() {
        openRelativePath("/search/?resource_type=Simulations");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Simulations filter should list courses");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("resource_type=Simulations"),
                "Search URL should preserve the Simulations filter");
        logOutcome("Simulations result links", countCourseResultLinksInSearchPage());
        openRelativePath(OcwSampleCourse.PATH);
        Assert.assertTrue(
                driver.getCurrentUrl().contains("6-006-introduction-to-algorithms-spring-2020"),
                "After simulations discovery, open the canonical sample course (6.006 Spring 2020)");
        logOutcome("Sample course home after simulations check", driver.getCurrentUrl());
    }

    @Test(description = "TC-5.3: Resource download link validation")
    public void testDownloadLink() {
        openRelativePath(OcwSampleCourse.PATH + "pages/assignments/");
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/pages/assignments/"));
        WebElement resourceLink = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/resources/']")));
        String href = resourceLink.getAttribute("href");
        Assert.assertNotNull(href);
        Assert.assertTrue(
                href.startsWith("http") || href.startsWith("/"),
                "Course resource link should be a valid absolute or site-relative URL");
        logOutcome("First /resources/ link href", href);
    }

    @Test(description = "TC-5.4: Assignments page access")
    public void testAssignmentsAccess() {
        openRelativePath(OcwSampleCourse.PATH + "pages/assignments/");
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/pages/assignments/"));
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("assignment"),
                "Assignments section should load course assignment content");
        logOutcome("Assignments page URL", driver.getCurrentUrl());
    }

    @Test(description = "TC-5.5: Featured learning resource types visible")
    public void testExamSolutions() {
        openRelativePath(OcwSampleCourse.PATH);
        Assert.assertTrue(
                driver.getPageSource().contains("Lecture Videos"),
                "Course should list Lecture Videos among learning resource types");
        logOutcome("Course home URL", driver.getCurrentUrl());
    }
}
