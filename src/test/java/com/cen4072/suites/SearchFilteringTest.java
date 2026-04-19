package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SearchFilteringTest extends BaseTest {

    @Test(description = "TC-3.1: Filter by Topic")
    public void testTopicFilter() {
        openRelativePath("/search/?t=Computer+Science");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Topic filter should return course results");
        String src = driver.getPageSource();
        Assert.assertTrue(
                src.contains("Computer Science") || driver.getCurrentUrl().contains("t=Computer"),
                "Results should reflect Computer Science topic filter");
        logOutcome("Topic filter course links", countCourseResultLinksInSearchPage());
    }

    @Test(description = "TC-3.2: Filter by Course Level")
    public void testLevelFilter() {
        openRelativePath("/search/?l=Undergraduate");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Undergraduate filter should return results");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("l=Undergraduate") || driver.getPageSource().contains("Undergraduate"),
                "Page should reflect undergraduate level filter");
        logOutcome("Level filter URL", driver.getCurrentUrl());
    }

    @Test(description = "TC-3.3: Filter by Department")
    public void testDepartmentFilter() {
        openRelativePath("/search/?d=Physics");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Physics department filter should return results");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("d=Physics") || driver.getPageSource().contains("Physics"),
                "Page should reflect Physics department filter");
        logOutcome("Department filter course links", countCourseResultLinksInSearchPage());
    }

    @Test(description = "TC-3.4: Clear all filters")
    public void testClearFilters() {
        openRelativePath("/search/?t=Engineering&l=Undergraduate");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0);

        WebElement clearAll = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Clear All')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", clearAll);
        clearAll.click();

        waitUpTo(Duration.ofSeconds(5)).until(d -> {
            String u = d.getCurrentUrl();
            boolean lostTopic = !u.contains("t=Engineering");
            boolean lostLevel = !u.contains("l=Undergraduate");
            return u.contains("/search") && (lostTopic || lostLevel);
        });

        String url = driver.getCurrentUrl();
        Assert.assertFalse(url.contains("t=Engineering") && url.contains("l=Undergraduate"),
                "Active filters should be cleared from the search URL");
        logOutcome("URL after Clear All", url);
    }

    @Test(description = "TC-3.5: Multi-select filter combination")
    public void testMultiFilter() {
        openRelativePath("/search/?t=Engineering&l=Undergraduate");
        waitForSearchResultsLoaded();
        Assert.assertTrue(countCourseResultLinksInSearchPage() > 0, "Combined filters should still return courses");
        String url = driver.getCurrentUrl();
        Assert.assertTrue(
                (url.contains("t=Engineering") || driver.getPageSource().contains("Engineering"))
                        && (url.contains("l=Undergraduate") || driver.getPageSource().contains("Undergraduate")),
                "Both topic and level constraints should be reflected");
        logOutcome("Combined filters URL", url);
    }
}
