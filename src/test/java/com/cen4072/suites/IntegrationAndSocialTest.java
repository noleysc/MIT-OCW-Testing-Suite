package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import com.cen4072.support.OcwSampleCourse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class IntegrationAndSocialTest extends BaseTest {

    @Test(description = "TC-8.1: 'Share to Twitter/X' link")
    public void testTwitterShare() {
        openRelativePath(OcwSampleCourse.PATH);
        WebElement shareLink = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("a[href*='twitter.com'], a[href*='x.com'], a[href*='intent/tweet']")));
        String href = shareLink.getAttribute("href");
        Assert.assertNotNull(href, "Share-to-X/Twitter link should contain a destination URL");
        Assert.assertTrue(
                href.contains("twitter.com") || href.contains("x.com"),
                "Share link should target Twitter/X");
        logOutcome("Share link href", href);
    }

    @Test(description = "TC-8.2: MIT main site redirect")
    public void testMITHomeLink() {
        navigateHome();
        WebElement mitLogo = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='openlearning.mit.edu']")));
        Assert.assertTrue(mitLogo.isDisplayed(), "MIT Open Learning link should be visible");
        logOutcome("MIT Open Learning href", mitLogo.getAttribute("href"));
    }

    @Test(description = "TC-8.3: YouTube channel link")
    public void testYouTubeLink() {
        navigateHome();
        WebElement youtubeLink = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='youtube.com/mitocw']")));
        Assert.assertTrue(youtubeLink.isDisplayed(), "YouTube social link should be present in footer");
        logOutcome("YouTube href", youtubeLink.getAttribute("href"));
    }

    @Test(description = "TC-8.4: Feedback / contact entry point")
    public void testFeedbackForm() {
        navigateHome();
        WebElement feedbackLink = waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("#desktop-header a[href*='requests/new']")));
        Assert.assertTrue(feedbackLink.isEnabled(), "Contact Us link should be clickable");
        logOutcome("Feedback/requests link href", feedbackLink.getAttribute("href"));
    }

    @Test(description = "TC-8.5: Help/FAQ navigation")
    public void testHelpNavigation() {
        navigateHome();
        String originalWindow = driver.getWindowHandle();
        waitUpTo(Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("#desktop-header a[href*='zendesk.com/hc/en-us']")))
                .click();

        waitUpTo(Duration.ofSeconds(5)).until(d -> d.getWindowHandles().size() > 1);
        Set<String> windows = driver.getWindowHandles();
        for (String handle : windows) {
            if (!originalWindow.equals(handle)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        Assert.assertTrue(
                driver.getCurrentUrl().contains("zendesk") || driver.getCurrentUrl().contains("/hc/"),
                "Help navigation should open Zendesk/Help Center content");
        logOutcome("Help window URL", driver.getCurrentUrl());

        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
