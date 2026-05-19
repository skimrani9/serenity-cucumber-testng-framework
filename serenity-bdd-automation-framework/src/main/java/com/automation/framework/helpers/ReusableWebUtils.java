package com.automation.framework.helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Thin Selenium helpers decoupled from Serenity {@link net.serenitybdd.core.pages.PageObject}.
 */
public final class ReusableWebUtils {

    private ReusableWebUtils() {
    }

    public static void scrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
    }

    /** Click via JS when overlays intercept pointer events or Serenity marks the element non-clickable. */
    public static void jsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
