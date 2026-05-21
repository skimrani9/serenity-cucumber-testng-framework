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

    /**
     * Sets {@code input} value in a way that React/stateful components observe (descriptor setter + events).
     * Use for masked / country-code Tel inputs where {@link WebElement#clear()} does not reliably reset.
     */
    public static void setInputValueTriggeringInputEvent(WebDriver driver, WebElement input, String value) {
        String v = value == null ? "" : value;
        ((JavascriptExecutor) driver).executeScript(
                "var el = arguments[0];\n"
                        + "var v = arguments[1];\n"
                        + "try {\n"
                        + "  var desc = Object.getOwnPropertyDescriptor("
                        + "window.HTMLInputElement.prototype, 'value');\n"
                        + "  if (desc && desc.set) { desc.set.call(el, v); } else { el.value = v; }\n"
                        + "} catch (e) {\n"
                        + "  el.value = v;\n"
                        + "}\n"
                        + "el.dispatchEvent(new Event('input', { bubbles: true }));\n"
                        + "el.dispatchEvent(new Event('change', { bubbles: true }));\n"
                        + "el.dispatchEvent(new Event('blur', { bubbles: true }));\n",
                input, v);
    }
}
