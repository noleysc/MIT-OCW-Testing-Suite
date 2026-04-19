package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class CourseSearchTest extends BaseTest {

    @Test(description = "TC-2.1: Keyword search (Python)")
    public void testKeywordSearch() {
        navigateHome();
        WebElement searchInput = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='q']")));
        searchInput.sendKeys("Python");
        searchInput.sendKeys(Keys.ENTER);

        waitForSearchResultsLoaded();
        Assert.assertTrue(
                countCourseResultLinksInSearchPage() > 0,
                "Keyword search should return at least one course result");
        Assert.assertTrue(
                driver.getCurrentUrl().contains("q=Python") || driver.getPageSource().contains("Python"),
                "Search results should reflect the queried keyword");
        logOutcome("Python search course links", countCourseResultLinksInSearchPage());
        logOutcome("Search results URL", driver.getCurrentUrl());
    }

    @Test(description = "TC-2.2: Empty search handling")
    public void testEmptySearch() {
        openRelativePath("/search/?q=");
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/search/"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/search"), "Empty search should remain in search flow");
        Assert.assertFalse(
                driver.getTitle() == null || driver.getTitle().isBlank(),
                "Search page should still render correctly for empty query input");
        logOutcome("Empty search page title", driver.getTitle());
    }

    @Test(description = "TC-2.3: Special character search")
    public void testSpecialCharSearch() {
        openRelativePath("/search/?q=%23%24%25");
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/search/"));
        Assert.assertTrue(
                driver.getCurrentUrl().contains("q=%23%24%25") || driver.getCurrentUrl().contains("q=#$%"),
                "Search URL should preserve encoded special-character query");
        Assert.assertFalse(
                driver.findElements(By.id("search-page")).isEmpty(),
                "Special-character input should still render the search page shell");
        logOutcome("Special-char search URL", driver.getCurrentUrl());
    }

    @Test(description = "TC-2.4: Partial-term search (Phys)")
    public void testSearchSuggestions() {
        navigateHome();
        WebElement searchInput = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='q']")));
        searchInput.sendKeys("Phys");
        searchInput.sendKeys(Keys.ENTER);
        waitForSearchResultsLoaded();
        Assert.assertTrue(
                driver.getPageSource().contains("Phys")
                        || driver.getCurrentUrl().toLowerCase().contains("q=phys"),
                "Search interaction should preserve and submit partial query text");
        logOutcome("Partial query search URL", driver.getCurrentUrl());
    }

    @Test(description = "TC-2.5: 'No results found' UI")
    public void testNoResultsUI() {
        openRelativePath("/search/?q=zzzzzzzzzzzzzzzzzzzz");
        waitUpTo(Duration.ofSeconds(5)).until(ExpectedConditions.urlContains("/search/"));
        Assert.assertTrue(
                driver.getPageSource().contains("No results")
                        || countCourseResultLinksInSearchPage() == 0,
                "Gibberish query should either show a no-results message or zero course result cards");
        logOutcome("No-results course link count", countCourseResultLinksInSearchPage());
    }
}
