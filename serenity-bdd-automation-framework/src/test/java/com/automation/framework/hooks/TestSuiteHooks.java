package com.automation.framework.hooks;

import com.automation.framework.driver.DriverFactory;
import io.cucumber.java.Before;

/**
 * Global Cucumber hooks (runs before each scenario unless refined with tags).
 */
public class TestSuiteHooks {

    @Before(order = 0)
    public void bootstrapChromeDriver() {
        DriverFactory.bootstrapChromeDriver();
    }
}
