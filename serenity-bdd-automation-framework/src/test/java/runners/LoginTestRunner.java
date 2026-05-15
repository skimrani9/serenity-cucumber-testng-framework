package runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * JUnit entry point that runs Cucumber with Serenity reporting/instrumentation.
 * <p>
 * Execute with Maven: {@code mvn clean verify}
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "classpath:features",
        glue = {"stepdefinitions"}
)
public class LoginTestRunner {
}
