package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.junit.jupiter.api.*;

@DisplayName("TS-02: Course Search")
public class CourseSearchTest extends BaseTest {

    @Test
    @DisplayName("TC-06: Keyword search (Python)")
    public void testKeywordSearch() {
        // TODO: Input 'Python' and verify results
    }

    @Test
    @DisplayName("TC-07: Empty search handling")
    public void testEmptySearch() {
        // TODO: Submit empty search and verify behavior
    }

    @Test
    @DisplayName("TC-08: Special character search")
    public void testSpecialCharSearch() {
        // TODO: Search for '#$%' and verify 'no results' or handling
    }

    @Test
    @DisplayName("TC-09: Search suggestions")
    public void testSearchSuggestions() {
        // TODO: Type 'Phys' and verify dropdown suggestions
    }

    @Test
    @DisplayName("TC-10: 'No results found' UI")
    public void testNoResultsUI() {
        // TODO: Search for gibberish and verify UI message
    }
}
