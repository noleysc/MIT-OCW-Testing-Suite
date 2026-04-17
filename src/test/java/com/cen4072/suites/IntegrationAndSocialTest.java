package com.cen4072.suites;

import com.cen4072.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IntegrationAndSocialTest extends BaseTest {

    @Test(description = "TC-8.1: 'Share to Twitter/X' link")
    public void testTwitterShare() {
        // TODO: Verify share link contains correct course URL
    }

    @Test(description = "TC-8.2: MIT main site redirect")
    public void testMITHomeLink() {
        org.openqa.selenium.WebElement mitLogo = driver.findElement(org.openqa.selenium.By.cssSelector("a[href*='openlearning.mit.edu']"));
        Assert.assertTrue(mitLogo.isDisplayed(), "MIT Open Learning link should be visible");
    }

    @Test(description = "TC-8.3: YouTube channel link")
    public void testYouTubeLink() {
        org.openqa.selenium.WebElement youtubeLink = driver.findElement(org.openqa.selenium.By.cssSelector("a[href*='youtube.com/mitocw']"));
        Assert.assertTrue(youtubeLink.isDisplayed(), "YouTube social link should be present in footer");
    }

    @Test(description = "TC-8.4: Feedback form submission")
    public void testFeedbackForm() {
        org.openqa.selenium.WebElement feedbackLink = driver.findElement(org.openqa.selenium.By.cssSelector("#desktop-header a[href*='requests/new']"));
        Assert.assertTrue(feedbackLink.isEnabled(), "Contact Us link should be clickable");
    }

    @Test(description = "TC-8.5: Help/FAQ navigation")
    public void testHelpNavigation() {
        String originalWindow = driver.getWindowHandle();
        driver.findElement(org.openqa.selenium.By.cssSelector("#desktop-header a[href*='zendesk.com/hc/en-us']")).click();
        
        // Wait for new window and switch
        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        
        Assert.assertTrue(driver.getCurrentUrl().contains("zendesk"), "URL should contain 'zendesk' after navigation");
        
        // Close new window and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
    }
}
