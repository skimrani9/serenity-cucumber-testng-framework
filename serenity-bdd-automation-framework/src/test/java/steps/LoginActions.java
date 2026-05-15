package steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.annotations.Step;
import pages.LoginPage;
import utils.TestDataReader;

import static org.junit.Assert.assertTrue;

/**
 * Business-level login steps (instrumented for Serenity reports).
 */
public class LoginActions {

    LoginPage loginPage;

    @Step("Set up WebDriverManager for Chrome")
    public void setupWebDriverManagerForChrome() {
        WebDriverManager.chromedriver().setup();
    }

    @Step("Navigate to the login page")
    public void navigateToLoginPage() {
        loginPage.open();
    }

    @Step("Enter credentials from test data key '{0}'")
    public void enterCredentialsFromTestData(String testDataKey) {
        TestDataReader.CredentialPair creds = TestDataReader.readCredentials(testDataKey);
        loginPage.enterUsername(creds.username());
        loginPage.enterPassword(creds.password());
    }

    @Step("Click the Sign In button")
    public void clickSignInButton() {
        loginPage.clickSignInButton();
    }

    @Step("Assert user is on dashboard after successful login")
    public void assertLoggedInOnDashboard() {
        assertTrue("Expected dashboard element after login", loginPage.isLoginSuccessful());
    }

    @Step("Assert error message: {0}")
    public void assertErrorMessageContains(String expected) {
        String actual = loginPage.getErrorMessageText();
        assertTrue("Expected error to contain \"" + expected + "\" but was: " + actual,
                actual.contains(expected));
    }

    @Step("Leave username and password blank")
    public void leaveFieldsBlank() {
        loginPage.clearUsernameAndPassword();
    }

    @Step("Assert validation message: {0}")
    public void assertValidationMessageContains(String expected) {
        String actual = loginPage.getValidationMessageText();
        assertTrue("Expected validation to contain \"" + expected + "\" but was: " + actual,
                actual.contains(expected));
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

    @Step("Assert password input is masked (type=password)")
    public void assertPasswordFieldMasked() {
        assertTrue("Password field should be type=password", loginPage.isPasswordFieldMasked());
    }
}
