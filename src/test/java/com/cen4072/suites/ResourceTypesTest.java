package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.junit.jupiter.api.*;

@DisplayName("TS-05: Resource Types")
public class ResourceTypesTest extends BaseTest {

    @Test
    @DisplayName("TC-5.1: Filter for 'Online Textbooks'")
    public void testTextbookFilter() {
        // TODO: Use 'Resource Type' filter for textbooks
    }

    @Test
    @DisplayName("TC-5.2: Filter for 'Interactive Simulations'")
    public void testSimulationFilter() {
        // TODO: Use 'Resource Type' filter for simulations
    }

    @Test
    @DisplayName("TC-5.3: Resource download link validation")
    public void testDownloadLink() {
        // TODO: Check if individual resource PDFs have valid hrefs
    }

    @Test
    @DisplayName("TC-5.4: Assignments page access")
    public void testAssignmentsAccess() {
        // TODO: Navigate to 'Assignments' section of a course
    }

    @Test
    @DisplayName("TC-5.5: Exam solution accessibility")
    public void testExamSolutions() {
        // TODO: Verify 'Exam' resources are listed where applicable
    }
}
