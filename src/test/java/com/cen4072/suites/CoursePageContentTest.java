package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.junit.jupiter.api.*;

@DisplayName("TS-04: Course Page Content")
public class CoursePageContentTest extends BaseTest {

    @Test
    @DisplayName("TC-4.1: Syllabus visibility")
    public void testSyllabusPresence() {
        // TODO: Navigate to a course and verify Syllabus tab
    }

    @Test
    @DisplayName("TC-4.2: Instructor info presence")
    public void testInstructorInfo() {
        // TODO: Verify instructor names are displayed on course page
    }

    @Test
    @DisplayName("TC-4.3: Course Number display")
    public void testCourseNumber() {
        // TODO: Verify unique course ID is visible
    }

    @Test
    @DisplayName("TC-4.4: PDF download link check")
    public void testPdfLinks() {
        // TODO: Verify 'Download Course' button exists
    }

    @Test
    @DisplayName("TC-4.5: Video lecture embed loading")
    public void testVideoEmbed() {
        // TODO: Navigate to video course and verify iframe presence
    }
}
