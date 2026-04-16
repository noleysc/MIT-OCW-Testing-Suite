package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Dimension;

@DisplayName("TS-06: Responsive Design")
public class ResponsiveDesignTest extends BaseTest {

    @Test
    @DisplayName("TC-6.1: Mobile menu toggle")
    public void testMobileMenu() {
        driver.manage().window().setSize(new Dimension(375, 812));
        // TODO: Verify hamburger menu appears and opens
    }

    @Test
    @DisplayName("TC-6.2: Tablet layout grid")
    public void testTabletLayout() {
        driver.manage().window().setSize(new Dimension(768, 1024));
        // TODO: Verify course grid adjusts to 2 columns
    }

    @Test
    @DisplayName("TC-6.3: Search bar visibility on mobile")
    public void testMobileSearch() {
        driver.manage().window().setSize(new Dimension(375, 812));
        // TODO: Verify search icon/input is accessible
    }

    @Test
    @DisplayName("TC-6.4: Image scaling")
    public void testImageScaling() {
        // TODO: Check if hero images have 'max-width: 100%' or similar
    }

    @Test
    @DisplayName("TC-6.5: Desktop-to-Mobile transition")
    public void testResizeTransition() {
        driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(375, 812));
        // TODO: Verify page elements don't overlap
    }

    @AfterEach
    public void resetWindow() {
        driver.manage().window().maximize();
    }
}
