package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import steps.LoginActions;

/**
 * Maps Gherkin for the local login feature to {@link LoginActions}.
 */
public class LoginStepDefinitions {

    @Steps
    LoginActions loginActions;

    @Before
    public void prepareBrowserDriver() {
        loginActions.setupWebDriverManagerForChrome();
    }

    @Given("user navigates to the login page")
    public void user_navigates_to_the_login_page() {
        loginActions.navigateToLoginPage();
    }

    @When("user enters credentials using test data {string}")
    public void user_enters_credentials_using_test_data(String testDataKey) {
        loginActions.enterCredentialsFromTestData(testDataKey);
    }

    @And("user clicks the Sign In button")
    public void user_clicks_the_sign_in_button() {
        loginActions.clickSignInButton();
    }

    @Then("user should be successfully logged in and redirected to the dashboard")
    public void user_should_be_successfully_logged_in_and_redirected_to_the_dashboard() {
        loginActions.assertLoggedInOnDashboard();
    }

    @Then("system should display error message {string}")
    public void system_should_display_error_message(String expectedMessage) {
        loginActions.assertErrorMessageContains(expectedMessage);
    }

    @When("user leaves username and password fields blank")
    public void user_leaves_username_and_password_fields_blank() {
        loginActions.leaveFieldsBlank();
    }

    @Then("system should display validation message {string}")
    public void system_should_display_validation_message(String expectedMessage) {
        loginActions.assertValidationMessageContains(expectedMessage);
    }

    @When("user clicks the Forgot password link")
    public void user_clicks_the_forgot_password_link() {
        loginActions.clickForgotPasswordLink();
    }

    @Then("user should be redirected to the Reset Password page")
    public void user_should_be_redirected_to_the_reset_password_page() {
        loginActions.assertOnResetPasswordPage();
    }

    @When("user clicks the Create an account button")
    public void user_clicks_the_create_an_account_button() {
        loginActions.clickCreateAccountButton();
    }

    @Then("user should be redirected to the Sign Up page")
    public void user_should_be_redirected_to_the_sign_up_page() {
        loginActions.assertOnSignUpPage();
    }

    @Then("the password field should be masked")
    public void the_password_field_should_be_masked() {
        loginActions.assertPasswordFieldMasked();
    }
}
