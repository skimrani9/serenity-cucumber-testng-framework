package com.automation.framework.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import com.automation.framework.steps.LoginActions;

public class LoginStepDefinitions {

    @Steps
    LoginActions loginActions;

    @Given("user navigates to the login page")
    public void user_navigates_to_the_login_page() {
        loginActions.navigateToLoginPage();
    }

    @When("user completes OTP authentication using test data {string}")
    public void user_completes_otp_authentication_using_test_data(String testDataKey) {
        loginActions.completeOtpAuthenticationUsingTestData(testDataKey);
    }

    @When("user opens Email & Password login tab using test data {string}")
    public void user_opens_email_password_login_tab_using_test_data(String testDataKey) {
        loginActions.openEmailPasswordLoginTabUsingTestData(testDataKey);
    }

    @When("user requests OTP with invalid email from test data {string}")
    public void user_requests_otp_with_invalid_email_from_test_data(String testDataKey) {
        loginActions.submitInvalidEmailForOtpUsingTestData(testDataKey);
    }

    @When("user requests OTP leaving email empty using test data {string}")
    public void user_requests_otp_leaving_email_empty_using_test_data(String testDataKey) {
        loginActions.submitEmptyEmailForOtpUsingTestData(testDataKey);
    }

    @Then("user should land on the authenticated home screen")
    public void user_should_land_on_the_authenticated_home_screen() {
        loginActions.assertLoggedInOnDashboard();
    }

    @Then("login feedback should contain text {string}")
    public void login_feedback_should_contain_text(String fragment) {
        loginActions.assertLoginFeedbackContains(fragment);
    }

    @Then("the Send OTP button should be disabled")
    public void the_send_otp_button_should_be_disabled() {
        loginActions.assertSendOtpButtonDisabled();
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
        loginActions.assertPasswordFieldMaskedOrSkipOnOtpUi();
    }
}
