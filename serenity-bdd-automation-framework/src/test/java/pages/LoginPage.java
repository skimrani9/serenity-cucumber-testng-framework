package pages;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

/**
 * Serenity Page Object for the app login screen (localhost:5173).
 * Locators match your UI; error/validation XPaths allow common wording variants.
 */
@DefaultUrl("/")
public class LoginPage extends PageObject {

    @FindBy(xpath = "//input[@name='email']")
    private WebElementFacade usernameField;

    @FindBy(xpath = "//input[@name='password']")
    private WebElementFacade passwordField;

    @FindBy(xpath = "//button[contains(text(),'Sign In')]")
    private WebElementFacade signInButton;

    /**
     * Invalid-credentials banner (supports "Invalid email or password" or "Invalid username or password").
     */
    @FindBy(xpath = "//*[self::div or self::p][contains(.,'Invalid') and contains(.,'password')]")
    private WebElementFacade errorMessage;

    /**
     * Empty-field / format validation (adjust if your copy differs).
     */
    @FindBy(xpath = "//p[contains(.,'Please enter a valid email address') or contains(.,'Username is required') or contains(.,'required')]")
    private WebElementFacade validationMessage;

    @FindBy(xpath = "//button[contains(text(),'Forgot?')]")
    private WebElementFacade forgotPasswordLink;

    @FindBy(xpath = "//a[contains(text(),'Create an account')]")
    private WebElementFacade createAccountButton;

    @FindBy(xpath = "//h2[text()='Projects'] | //button[contains(.,'Logout')] | //div[contains(text(),'Projects')] | //span[contains(text(),'Logout')]")
    private WebElementFacade dashboardElement;

    public void enterUsername(String username) {
        usernameField.waitUntilVisible();
        usernameField.clear();
        usernameField.type(username);
    }

    public void enterPassword(String password) {
        passwordField.waitUntilVisible();
        passwordField.clear();
        passwordField.type(password);
    }

    public void clickSignInButton() {
        signInButton.waitUntilClickable().click();
    }

    public void clickForgotPasswordLink() {
        forgotPasswordLink.waitUntilClickable().click();
    }

    public void clickCreateAccountButton() {
        createAccountButton.waitUntilClickable().click();
    }

    /** Clears both fields (used for empty-credentials scenarios). */
    public void clearUsernameAndPassword() {
        usernameField.waitUntilVisible();
        usernameField.clear();
        passwordField.clear();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignInButton();
    }

    public boolean isLoginSuccessful() {
        dashboardElement.withTimeoutOf(15, TimeUnit.SECONDS).waitUntilVisible();
        return dashboardElement.isCurrentlyVisible();
    }

    public String getErrorMessageText() {
        errorMessage.waitUntilVisible();
        return errorMessage.getText().trim();
    }

    public String getValidationMessageText() {
        validationMessage.waitUntilVisible();
        return validationMessage.getText().trim();
    }

    public boolean isPasswordFieldMasked() {
        passwordField.waitUntilVisible();
        return "password".equalsIgnoreCase(passwordField.getAttribute("type"));
    }

    public boolean isOnResetPasswordPage() {
        String url = getDriver().getCurrentUrl();
        return url.matches("(?i).*(reset|forgot|recover).*");
    }

    public boolean isOnSignUpPage() {
        String url = getDriver().getCurrentUrl();
        return url.matches("(?i).*(signup|sign-up|register).*");
    }
}
