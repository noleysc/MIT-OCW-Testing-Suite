package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ResponsiveDesignTest extends BaseTest {

    @Test(description = "TC-6.1: Mobile menu toggle")
    public void testMobileMenu() {
        navigateHome();
        driver.manage().window().setSize(new Dimension(375, 812));
        WebElement toggler = waitUpTo(Duration.ofSeconds(15)).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("#mobile-header .navbar-toggler")));
        toggler.click();
        WebElement collapse = driver.findElement(By.cssSelector("#mobile-header #navbarSupportedContent"));
        waitUpTo(Duration.ofSeconds(5)).until(
                d -> collapse.getAttribute("class") != null && collapse.getAttribute("class").contains("show"));
        Assert.assertTrue(collapse.isDisplayed(), "Mobile navigation should expand after toggling the menu");
    }

    @Test(description = "TC-6.2: Tablet layout grid")
    public void testTabletLayout() {
        navigateHome();
        driver.manage().window().setSize(new Dimension(900, 900));
        waitUpTo(Duration.ofSeconds(15)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".course-cards .course-card")));
        int cards = driver.findElements(By.cssSelector(".course-cards .course-card")).size();
        Assert.assertTrue(cards >= 2, "Homepage should show multiple course cards");
        Boolean twoInFirstRow = (Boolean) runScript(
                "var cards = document.querySelectorAll('.course-cards .course-card');"
                        + "if (cards.length < 2) return false;"
                        + "var top = cards[0].getBoundingClientRect().top;"
                        + "var count = 0;"
                        + "for (var i = 0; i < cards.length; i++) {"
                        + "  if (Math.abs(cards[i].getBoundingClientRect().top - top) < 10) count++;"
                        + "}"
                        + "return count >= 2;");
        Assert.assertTrue(
                Boolean.TRUE.equals(twoInFirstRow),
                "Course grid should show at least two cards in the same row at medium widths");
    }

    @Test(description = "TC-6.3: Search bar visibility on mobile")
    public void testMobileSearch() {
        navigateHome();
        driver.manage().window().setSize(new Dimension(375, 812));
        WebElement search = waitUpTo(Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='q']")));
        Assert.assertTrue(search.isDisplayed() && search.isEnabled(),
                "Homepage search field should remain usable on small viewports");
    }

    @Test(description = "TC-6.4: Image scaling")
    public void testImageScaling() {
        navigateHome();
        driver.manage().window().setSize(new Dimension(375, 812));
        WebElement heroImg = waitUpTo(Duration.ofSeconds(10)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#home-header img")));
        int imgWidth = heroImg.getRect().getWidth();
        int viewport = driver.manage().window().getSize().getWidth();
        Assert.assertTrue(
                imgWidth <= viewport + 4,
                "Hero image should not overflow the viewport on a narrow screen");
    }

    @Test(description = "TC-6.5: Desktop-to-Mobile transition")
    public void testResizeTransition() {
        navigateHome();
        driver.manage().window().maximize();
        Boolean desktopOk = (Boolean) runScript(
                "return document.documentElement.scrollWidth <= document.documentElement.clientWidth + 2");
        driver.manage().window().setSize(new Dimension(375, 812));
        Boolean mobileOk = (Boolean) runScript(
                "return document.documentElement.scrollWidth <= document.documentElement.clientWidth + 2");
        Assert.assertTrue(Boolean.TRUE.equals(desktopOk) && Boolean.TRUE.equals(mobileOk),
                "Page should not introduce horizontal overflow after resizing");
    }

    @AfterMethod
    public void resetWindow() {
        driver.manage().window().maximize();
    }
}
