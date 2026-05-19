package com.automation.framework.driver;

import org.openqa.selenium.WebDriver;

/**
 * Optional {@link ThreadLocal} holder for advanced suites.
 * Serenity manages WebDriver for Cucumber scenarios; use this only when extending with plain Selenium tests.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void register(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver currentDriver() {
        return DRIVER.get();
    }

    public static void clear() {
        DRIVER.remove();
    }
}
