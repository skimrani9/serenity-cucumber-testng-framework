package com.automation.framework.steps;

import io.cucumber.java.PendingException;
import net.serenitybdd.annotations.Step;
import com.automation.framework.helpers.TestDataReader;
import com.automation.framework.pageObjects.JustoOtpLoginPage;
import com.automation.framework.pageObjects.LoginPage;

import static org.junit.Assert.assertTrue;

public class LoginActions {

    LoginPage loginPage;
    JustoOtpLoginPage justoOtpLoginPage;

    @Step("Navigate to login page")
    public void navigateToLoginPage() {
        loginPage.open();
        loginPage.dismissLandingGateIfPresent();
    }

    /**
     * Password in {@code users.json} → Email &amp; Password tab + Login button;
     * otherwise OTP tab + Send OTP + Verify flow.
     */
    @Step("Complete OTP authentication using test data '{0}'")
    public void completeOtpAuthenticationUsingTestData(String testDataKey) {
        TestDataReader.LoginCredentials creds = TestDataReader.readLoginCredentials(testDataKey);
        loginPage.dismissLandingGateIfPresent();
        if (creds.usesPassword()) {
            loginPage.switchToEmailPasswordTabAndWaitForFields();
            loginPage.enterJustoEmailAndPassword(creds.username(), creds.password());
            loginPage.clickJustoLoginButton();
            return;
        }
        if (!creds.usesOtp()) {
            throw new IllegalStateException(testDataKey + " needs password or otp_code (six digits) for QA login.");
        }
        loginPage.enterUsername(creds.username());
        loginPage.clickSendOtpButton();
        justoOtpLoginPage.pauseAfterSendOtpPrompt();
        justoOtpLoginPage.enterOtpDigits(creds.otpDigitsSix());
        justoOtpLoginPage.clickVerifyLogin();
    }

    @Step("Open Email & Password login tab using test data '{0}' (for UI assertions)")
    public void openEmailPasswordLoginTabUsingTestData(String testDataKey) {
        TestDataReader.readLoginCredentials(testDataKey);
        loginPage.dismissLandingGateIfPresent();
        loginPage.switchToEmailPasswordTabAndWaitForFields();
    }

    @Step("Submit invalid email for OTP using test data '{0}'")
    public void submitInvalidEmailForOtpUsingTestData(String testDataKey) {
        TestDataReader.LoginCredentials creds = TestDataReader.readLoginCredentials(testDataKey);
        loginPage.enterUsername(creds.username());
        loginPage.clickSendOtpButton();
    }

    @Step("Submit OTP request with empty email using test data '{0}'")
    public void submitEmptyEmailForOtpUsingTestData(String testDataKey) {
        TestDataReader.readLoginCredentials(testDataKey);
        loginPage.clearUsernameAndPassword();
    }

    @Step("Assert Send OTP button is disabled")
    public void assertSendOtpButtonDisabled() {
        assertTrue("Expected Send OTP to be disabled when email is empty", loginPage.isSendOtpButtonDisabled());
    }

    @Step("Assert authenticated shell visible")
    public void assertLoggedInOnDashboard() {
        justoOtpLoginPage.waitUntilLoggedInShellVisible();
    }

    @Step("Login feedback contains '{0}'")
    public void assertLoginFeedbackContains(String fragment) {
        String actual = loginPage.getLoginFeedbackVisibleText().toLowerCase();
        assertTrue("Expected login feedback to contain \"" + fragment + "\" but was: " + actual,
                actual.contains(fragment.toLowerCase()));
    }

    @Step("Legacy error assertion contains '{0}'")
    public void assertErrorMessageContains(String expected) {
        assertLoginFeedbackContains(expected);
    }

    @Step("Legacy validation assertion contains '{0}'")
    public void assertValidationMessageContains(String expected) {
        assertLoginFeedbackContains(expected);
    }

    @Step("Click Forgot password link")
    public void clickForgotPasswordLink() {
        loginPage.clickForgotPasswordLink();
    }

    @Step("Assert navigated to Reset Password page")
    public void assertOnResetPasswordPage() {
        assertTrue("Expected URL to indicate reset/forgot password flow", loginPage.isOnResetPasswordPage());
    }

    @Step("Click Create an account")
    public void clickCreateAccountButton() {
        loginPage.clickCreateAccountButton();
    }

    @Step("Assert navigated to Sign Up page")
    public void assertOnSignUpPage() {
        assertTrue("Expected URL to indicate sign-up/register flow", loginPage.isOnSignUpPage());
    }

    @Step("Assert password masking on Email & Password tab")
    public void assertPasswordFieldMaskedOrSkipOnOtpUi() {
        if (!loginPage.hasPasswordFieldPresent()) {
            throw new PendingException("Password field not visible — open Email & Password tab first.");
        }
        assertTrue("Password field should be type=password", loginPage.isPasswordFieldMasked());
    }
}
