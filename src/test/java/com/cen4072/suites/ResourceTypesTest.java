package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ResourceTypesTest extends BaseTest {

    private static final String SAMPLE_COURSE = "/courses/6-006-introduction-to-algorithms-fall-2011/";

    @Test(description = "TC-5.1: Filter for 'Online Textbooks' (OCW label: Open Textbooks)")
    public void testTextbookFilter() {
        openRelativePath("/search/?resource_type=Open+Textbooks");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Open Textbooks filter should list courses");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("resource_type=Open+Textbooks"),
                "Search URL should preserve the Open Textbooks filter");
    }

    @Test(description = "TC-5.2: Filter for 'Interactive Simulations' (OCW label: Simulations)")
    public void testSimulationFilter() {
        openRelativePath("/search/?resource_type=Simulations");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Simulations filter should list courses");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("resource_type=Simulations"),
                "Search URL should preserve the Simulations filter");
    }

    @Test(description = "TC-5.3: Resource download link validation")
    public void testDownloadLink() {
        openRelativePath(SAMPLE_COURSE + "download/");
        WebElement pdfLink = waitUpTo(Duration.ofSeconds(20)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='.pdf']")));
        String href = pdfLink.getAttribute("href");
        Assert.assertNotNull(href);
        Assert.assertTrue(
                href.startsWith("http") || href.startsWith("/"),
                "PDF resource link should be a valid absolute or site-relative URL");
    }

    @Test(description = "TC-5.4: Assignments page access")
    public void testAssignmentsAccess() {
        openRelativePath(SAMPLE_COURSE + "pages/assignments/");
        waitUpTo(Duration.ofSeconds(15)).until(ExpectedConditions.urlContains("/pages/assignments/"));
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains("assignment"),
                "Assignments section should load course assignment content");
    }

    @Test(description = "TC-5.5: Exam solution accessibility")
    public void testExamSolutions() {
        openRelativePath(SAMPLE_COURSE);
        Assert.assertTrue(
                driver.getPageSource().contains("Exam Solutions"),
                "Course should list Exam Solutions among learning resource types");
    }
}
