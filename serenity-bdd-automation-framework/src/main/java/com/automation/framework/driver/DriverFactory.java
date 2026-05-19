package com.automation.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Browser/driver bootstrap for non-Serenity contexts or shared setup hooks.
 */
public final class DriverFactory {

    private DriverFactory() {
    }

    public static void bootstrapChromeDriver() {
        WebDriverManager.chromedriver().setup();
    }
}
