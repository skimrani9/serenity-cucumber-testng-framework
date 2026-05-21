package com.automation.framework.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Primary Cucumber + Serenity entry point (Maven Surefire).
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "classpath:features",
        glue = {"com.automation.framework.hooks", "com.automation.framework.stepdefinitions"}
)
public class DotcomRunner {
}
