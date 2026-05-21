package com.automation.framework.runner.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Lightweight TestNG smoke referenced from {@code testng.xml}.
 * Cucumber scenarios execute via {@link com.automation.framework.runner.DotcomRunner}.
 */
public class FrameworkToolkitSanityTest {

    @Test
    public void frameworkToolkitLoads() {
        Assert.assertTrue(true, "Placeholder bootstrap — extend with TestNG suites if needed.");
    }
}
