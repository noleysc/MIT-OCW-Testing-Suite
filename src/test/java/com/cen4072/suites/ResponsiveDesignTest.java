package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.Dimension;

@Epic("MIT OCW Automation")
@Feature("TS-06: Responsive Design")
public class ResponsiveDesignTest extends BaseTest {

    @Test(description = "TC-6.1: Mobile menu toggle")
    public void testMobileMenu() {
        driver.manage().window().setSize(new Dimension(375, 812));
        // TODO: Verify hamburger menu appears and opens
    }

    @Test(description = "TC-6.2: Tablet layout grid")
    public void testTabletLayout() {
        driver.manage().window().setSize(new Dimension(768, 1024));
        // TODO: Verify course grid adjusts to 2 columns
    }

    @Test(description = "TC-6.3: Search bar visibility on mobile")
    public void testMobileSearch() {
        driver.manage().window().setSize(new Dimension(375, 812));
        // TODO: Verify search icon/input is accessible
    }

    @Test(description = "TC-6.4: Image scaling")
    public void testImageScaling() {
        // TODO: Check if hero images have 'max-width: 100%' or similar
    }

    @Test(description = "TC-6.5: Desktop-to-Mobile transition")
    public void testResizeTransition() {
        driver.manage().window().maximize();
        driver.manage().window().setSize(new Dimension(375, 812));
        // TODO: Verify page elements don't overlap
    }

    @AfterMethod
    public void resetWindow() {
        driver.manage().window().maximize();
    }
}
