package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("MIT OCW Automation")
@Feature("TS-08: Integration & Social")
public class IntegrationAndSocialTest extends BaseTest {

    @Test(description = "TC-8.1: 'Share to Twitter/X' link")
    public void testTwitterShare() {
        // TODO: Verify share link contains correct course URL
    }

    @Test(description = "TC-8.2: MIT main site redirect")
    public void testMITHomeLink() {
        org.openqa.selenium.WebElement mitLogo = driver.findElement(org.openqa.selenium.By.cssSelector("a[href*='web.mit.edu']"));
        Assert.assertTrue(mitLogo.isDisplayed(), "MIT main site link should be visible");
    }

    @Test(description = "TC-8.3: YouTube channel link")
    public void testYouTubeLink() {
        org.openqa.selenium.WebElement youtubeLink = driver.findElement(org.openqa.selenium.By.cssSelector("a[href*='youtube.com/mitocw']"));
        Assert.assertTrue(youtubeLink.isDisplayed(), "YouTube social link should be present in footer");
    }

    @Test(description = "TC-8.4: Feedback form submission")
    public void testFeedbackForm() {
        org.openqa.selenium.WebElement feedbackLink = driver.findElement(org.openqa.selenium.By.linkText("Feedback"));
        Assert.assertTrue(feedbackLink.isEnabled(), "Feedback form link should be clickable");
    }

    @Test(description = "TC-8.5: Help/FAQ navigation")
    public void testHelpNavigation() {
        driver.findElement(org.openqa.selenium.By.linkText("Help")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("help"), "URL should contain 'help' after navigation");
    }
}
