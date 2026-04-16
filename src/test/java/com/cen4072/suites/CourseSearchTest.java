package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.junit.jupiter.api.*;

@DisplayName("TS-02: Course Search")
public class CourseSearchTest extends BaseTest {

    @Test
    @DisplayName("TC-2.1: Keyword search (Python)")
    public void testKeywordSearch() throws InterruptedException {
        // Find the search input field using its placeholder
        org.openqa.selenium.WebElement searchInput = driver.findElement(org.openqa.selenium.By.cssSelector("input[placeholder*='Search']"));
        
        // Type 'Python' and press Enter
        searchInput.sendKeys("Python");
        searchInput.sendKeys(org.openqa.selenium.Keys.ENTER);
        
        // Wait a moment for results to load (professional projects use WebDriverWait, but this is for demo)
        Thread.sleep(3000);
        
        // Assert that the results page header contains 'Python'
        String pageSource = driver.getPageSource();
        org.junit.jupiter.api.Assertions.assertTrue(pageSource.contains("Python"), 
            "The results page should contain the keyword 'Python'");
    }

    @Test
    @DisplayName("TC-2.2: Empty search handling")
    public void testEmptySearch() {
        // TODO: Submit empty search and verify behavior
    }

    @Test
    @DisplayName("TC-2.3: Special character search")
    public void testSpecialCharSearch() {
        // TODO: Search for '#$%' and verify 'no results' or handling
    }

    @Test
    @DisplayName("TC-2.4: Search suggestions")
    public void testSearchSuggestions() {
        // TODO: Type 'Phys' and verify dropdown suggestions
    }

    @Test
    @DisplayName("TC-2.5: 'No results found' UI")
    public void testNoResultsUI() {
        // TODO: Search for gibberish and verify UI message
    }
}
