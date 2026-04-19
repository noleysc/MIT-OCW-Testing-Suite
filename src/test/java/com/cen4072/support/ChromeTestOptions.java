package com.cen4072.support;

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Stable Chrome defaults for OCW: full page load (not {@code EAGER}) and images enabled
 * so layouts, heroes, and lazy content match what users see.
 */
public final class ChromeTestOptions {

    private ChromeTestOptions() {
    }

    public static ChromeOptions forAutomation() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--start-maximized",
                "--disable-extensions",
                "--disable-dev-shm-usage");
        return options;
    }
}
