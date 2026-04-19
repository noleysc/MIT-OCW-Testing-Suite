package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import com.cen4072.support.OcwSampleCourse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class CoursePageContentTest extends BaseTest {

    @Test(description = "TC-4.1: Syllabus visibility")
    public void testSyllabusPresence() {
        openRelativePath(OcwSampleCourse.PATH + "pages/syllabus/");
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/pages/syllabus/"));
        Assert.assertTrue(
                driver.getPageSource().toLowerCase().contains("syllabus"),
                "Syllabus page should render syllabus-related content");
        logOutcome("Syllabus page URL", driver.getCurrentUrl());
    }

    @Test(description = "TC-4.2: Instructor info presence")
    public void testInstructorInfo() {
        openRelativePath(OcwSampleCourse.PATH);
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        Assert.assertTrue(
                driver.getPageSource().contains("Instructor")
                        || driver.getPageSource().contains("Instructors")
                        || !driver.findElements(By.xpath("//*[contains(text(),'Prof.') or contains(text(),'Professor')]")).isEmpty(),
                "Course page should provide instructor metadata");
        logOutcome("Course home H1", driver.findElement(By.tagName("h1")).getText());
    }

    @Test(description = "TC-4.3: Course Number display")
    public void testCourseNumber() {
        openRelativePath(OcwSampleCourse.PATH);
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
        String pageSource = driver.getPageSource();
        Assert.assertTrue(
                pageSource.contains("6.006")
                        || pageSource.contains("Introduction to Algorithms"),
                "Course identifier and title metadata should be visible on the course page");
        logOutcome("Course page snapshot (6.006/title present)", true);
    }

    @Test(description = "TC-4.4: PDF resource page check")
    public void testPdfLinks() {
        openRelativePath(OcwSampleCourse.PDF_RESOURCE);
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/resources/"));
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        String src = driver.getPageSource();
        Assert.assertTrue(
                src.contains("Problem Set") || src.contains("Resource Type"),
                "Resource detail page should advertise the resource (Problem Set / Resource Type label)");
        Assert.assertTrue(
                src.toLowerCase().contains("pdf")
                        || !driver.findElements(By.cssSelector("a[href*='.pdf'], iframe")).isEmpty(),
                "Resource page should reference PDF content or embed a viewer/iframe");
        logOutcome("PDF resource URL", driver.getCurrentUrl());
    }

    @Test(description = "TC-4.5: Video lecture embed loading")
    public void testVideoEmbed() {
        openRelativePath(OcwSampleCourse.LECTURE_WITH_MEDIA);
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/resources/"));
        List<WebElement> embeds = driver.findElements(By.cssSelector("iframe, video"));
        Assert.assertFalse(
                embeds.isEmpty(),
                "Video resource page should expose an iframe or video element for lecture playback");
        logOutcome("Media elements (iframe|video) count", embeds.size());
    }
}
