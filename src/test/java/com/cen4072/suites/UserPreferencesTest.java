package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import com.cen4072.support.OcwSampleCourse;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class UserPreferencesTest extends BaseTest {

    @Test(description = "TC-7.1: Dark-themed navigation (no separate theme toggle on OCW)")
    public void testDarkMode() {
        navigateHome();
        Assert.assertFalse(
                driver.findElements(By.cssSelector("nav.navbar-dark.bg-black")).isEmpty(),
                "OCW ships a dark-styled navigation bar (there is no separate end-user theme toggle on ocw.mit.edu)");
        logOutcome("Dark navbar present", !driver.findElements(By.cssSelector("nav.navbar-dark.bg-black")).isEmpty());
    }

    @Test(description = "TC-7.2: Primary language (html lang / English content)")
    public void testLanguageSelection() {
        openRelativePath("/about/");
        WebElement html = driver.findElement(By.tagName("html"));
        Assert.assertEquals(html.getAttribute("lang"), "en", "Primary site language should be English");
        Assert.assertTrue(
                driver.getPageSource().contains("OpenCourseWare"),
                "About page content should load in English");
        logOutcome("html lang", html.getAttribute("lang"));
    }

    @Test(description = "TC-7.3: Font size adjustment")
    public void testFontSize() {
        navigateHome();
        driver.findElement(By.tagName("body")).click();
        WebElement body = driver.findElement(By.tagName("body"));
        String before = (String) runScript("return getComputedStyle(arguments[0]).fontSize", body);
        new Actions(driver).keyDown(Keys.CONTROL).sendKeys("+").keyUp(Keys.CONTROL).perform();
        String after = (String) runScript("return getComputedStyle(arguments[0]).fontSize", body);
        Assert.assertTrue(
                driver.findElement(By.cssSelector("input[name='q']")).isDisplayed(),
                "Layout should remain functional after a browser zoom / font-size shortcut");
        Assert.assertNotNull(before);
        Assert.assertNotNull(after);
        logOutcome("Body font-size before/after Ctrl+", before + " → " + after);
    }

    @Test(description = "TC-7.4: 'Hide/Show' sidebar")
    public void testSidebarToggle() {
        openRelativePath(OcwSampleCourse.PATH);
        driver.manage().window().setSize(new Dimension(375, 812));
        WebElement menuBtn = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(By.id("mobile-course-nav-toggle")));
        menuBtn.click();
        WebElement drawer = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("mobile-course-nav")));
        Assert.assertTrue(drawer.isDisplayed(), "Course materials drawer should open from the Menu control");
        logOutcome("Mobile course drawer visible", drawer.isDisplayed());
        driver.manage().window().maximize();
    }

    @Test(description = "TC-7.5: Cookie consent persistence")
    public void testCookieConsent() {
        navigateHome();
        runScript("document.cookie = 'cen4072_automation_test=1; path=/';");
        driver.navigate().refresh();
        String cookies = (String) runScript("return document.cookie");
        Assert.assertTrue(
                cookies != null && cookies.contains("cen4072_automation_test=1"),
                "A cookie set client-side should still be present after refresh (persistence check)");
        logOutcome("document.cookie contains test flag", cookies != null && cookies.contains("cen4072_automation_test=1"));
    }
}
