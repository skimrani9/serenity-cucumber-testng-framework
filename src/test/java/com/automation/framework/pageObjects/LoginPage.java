package com.automation.framework.pageObjects;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;
import com.automation.framework.helpers.ReusableWebUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Combined locators for password demos (legacy) and QA OTP screens where links appear together.
 */
@DefaultUrl("/auth/login")
public class LoginPage extends BasePage {

    private static final String EMAIL_LOCATOR = "//input[@type='email'] | "
            + "//input[@placeholder='Email' or contains(@placeholder,'mail') or contains(@placeholder,'Mail')] "
            + "| //input[@name='email']";

    private static final String PASSWORD_LOCATOR = "//input[@placeholder='Enter your password'] "
            + "| //input[@type='password' or @name='password']";

    private static final String[] LOGIN_FEEDBACK_CANDIDATES = {
            "//*[@role='alert']",
            "//*[@role='status']",
            "//*[contains(@data-testid,'toast')]",
            "//*[contains(@class,'toast')]",
            "//*[contains(@class,'sonner')]",
            "//*[self::div or self::p or self::span]["
                    + "contains(.,'required') or contains(.,'Required') "
                    + "or contains(.,'Email not found') or contains(.,'Invalid') "
                    + "or contains(.,'invalid') or contains(.,'password') "
                    + "or contains(.,'email') or contains(.,'Email')"
                    + "]",
    };

    private static final String SEND_OTP_BTN = "//button[contains(.,'OTP') or contains(.,'otp') "
            + "or contains(translate(.,'SEND','send'),'send')]";

    /** Optional pre-login screen — clicked only when visible (Proceed / Continue → login). */
    private static final String LANDING_GATE_CTA =
            "//*[self::button or self::a][" + "contains(translate(normalize-space(.),'LOGIN','login'),'login') "
                    + "and (contains(translate(normalize-space(.),'PROCEED','proceed'),'proceed') "
                    + "or contains(translate(normalize-space(.),'CONTINUE','continue'),'continue') "
                    + "or contains(translate(normalize-space(.),'PROCESS','process'),'process'))]"
                    + " | //*[self::button or self::a][contains(.,'Proceed')][contains(.,'login')]"
                    + " | //*[self::button or self::a][contains(.,'Proceed')][contains(.,'Login')]";

    /** Prefer {@code role="tab"}; fallback to labeled toggle matching QA copy (handles entity/decoration differences). */
    private static final String EMAIL_PASSWORD_TAB =
            "//*[@role='tab'][contains(.,'Email')][contains(.,'Password')]"
                    + "| //div[contains(normalize-space(.),'Email & Password')]"
                    + "| //div[contains(normalize-space(.),'Email &amp; Password')]"
                    + "| //button[contains(normalize-space(.),'Email')][contains(normalize-space(.),'Password')]";

    /** Password-tab email — excludes OTP combined \"email or phone\" placeholder. */
    private static final String JUSTO_EMAIL_INPUT =
            "//input[@type='email']"
                    + "|//input[@placeholder='Enter your email']"
                    + "|//input[contains(@placeholder,'Enter your email') "
                    + "and not(contains(translate(@placeholder,'PHONE','phone'),'phone'))]"
                    + "|//input[contains(translate(@placeholder,'MAIL','mail'),'mail') "
                    + "and not(contains(translate(@placeholder,'PHONE','phone'),'phone'))]";

    private static final String JUSTO_PASSWORD_INPUT =
            "//input[@placeholder='Enter your password']"
                    + "|//input[@type='password'][contains(translate(@placeholder,'PASSWORD','password'),'password')]"
                    + "|//input[@type='password' and not(@placeholder)]";

    private static final String JUSTO_LOGIN_BUTTON = "//button[contains(normalize-space(.),'Login')]";

    private static final String FORGOT_LINK = "//a[contains(@href,'forgot') or contains(@href,'reset') "
            + "or contains(@href,'recover') or contains(@href,'password')]"
            + " | //a[contains(normalize-space(.),'Forgot') or contains(normalize-space(.),'forgot')]"
            + " | //button[contains(.,'Forgot') or contains(.,'forgot')]"
            + " | //*[@role='link' and (contains(.,'Forgot') or contains(.,'forgot'))]"
            + " | //*[contains(@class,'link')][contains(.,'Forgot') or contains(.,'forgot')]";

    private static final String CREATE_ACCOUNT_LINK = "//a[contains(@href,'sign') or contains(@href,'register') "
            + "or contains(@href,'signup') or contains(@href,'sign-up')]"
            + " | //a[contains(.,'Create an account') or contains(.,'Create Account')]"
            + " | //a[contains(.,'Sign up') or contains(.,'Sign Up') or contains(.,'Register')]"
            + " | //a[contains(.,'Sign') and contains(.,'Up')]"
            + " | //button[contains(.,'Create an account') or contains(.,'Sign up') or contains(.,'Register')]";

    private WebElementFacade emailInput() {
        return $(EMAIL_LOCATOR).withTimeoutOf(20, TimeUnit.SECONDS);
    }

    private WebElementFacade passwordInput() {
        return $(PASSWORD_LOCATOR);
    }

    private WebElementFacade signInButton() {
        return $("//button[contains(.,'Sign In') or contains(.,'sign in')]");
    }

    private WebElementFacade forgotLink() {
        return $(FORGOT_LINK).withTimeoutOf(15, TimeUnit.SECONDS);
    }

    private WebElementFacade createAccountLink() {
        return $(CREATE_ACCOUNT_LINK).withTimeoutOf(15, TimeUnit.SECONDS);
    }

    private WebElementFacade dashboardMarker() {
        return $("//h2[contains(.,'Project')] | //button[contains(.,'Logout')] "
                + "| //div[contains(text(),'Projects')] | //span[contains(text(),'Logout')] "
                + "| //a[@href='/lead-management'] | //button[@aria-label='More menus']");
    }

    private WebElementFacade sendOtpButton() {
        return $(SEND_OTP_BTN).withTimeoutOf(15, TimeUnit.SECONDS);
    }

    /**
     * Some QA builds show an interim CTA before /auth/login content; skip silently if absent.
     */
    public void dismissLandingGateIfPresent() {
        List<WebElement> candidates = getDriver().findElements(By.xpath(LANDING_GATE_CTA));
        for (WebElement el : candidates) {
            try {
                if (el.isDisplayed()) {
                    ReusableWebUtils.scrollIntoView(getDriver(), el);
                    try {
                        el.click();
                    } catch (Exception ignored) {
                        ReusableWebUtils.jsClick(getDriver(), el);
                    }
                    waitABit(1200);
                    return;
                }
            } catch (Exception ignored) {
                // next candidate
            }
        }
    }

    /** Opens Email &amp; Password tab so QA placeholder inputs are visible. */
    public void switchToEmailPasswordTabAndWaitForFields() {
        boolean clicked = clickNormalizedExactEmailPasswordLabel()
                || clickRoleTabMatchingEmailAndPassword();
        if (!clicked) {
            List<WebElement> tabs = getDriver().findElements(By.xpath(EMAIL_PASSWORD_TAB));
            for (WebElement candidate : tabs) {
                try {
                    if (candidate.isDisplayed()) {
                        ReusableWebUtils.scrollIntoView(getDriver(), candidate);
                        try {
                            candidate.click();
                        } catch (Exception ignored) {
                            ReusableWebUtils.jsClick(getDriver(), candidate);
                        }
                        clicked = true;
                        break;
                    }
                } catch (Exception ignored) {
                    // try next match
                }
            }
        }
        if (!clicked) {
            WebElementFacade tab = $(EMAIL_PASSWORD_TAB).withTimeoutOf(15, TimeUnit.SECONDS);
            tab.waitUntilVisible();
            WebElement raw = tab.getWrappedElement();
            ReusableWebUtils.scrollIntoView(getDriver(), raw);
            try {
                tab.click();
            } catch (Exception ignored) {
                ReusableWebUtils.jsClick(getDriver(), raw);
            }
        }
        waitABit(2000);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(25));
        wait.until(driver -> driver.findElements(By.xpath("//input[@type='password']")).stream()
                .anyMatch(WebElement::isDisplayed));
    }

    /** Matches visible label exactly as rendered on QA toggle (user XPath parity). */
    private boolean clickNormalizedExactEmailPasswordLabel() {
        List<WebElement> els = getDriver().findElements(By.xpath("//*[normalize-space(.)='Email & Password']"));
        for (WebElement el : els) {
            try {
                if (el.isDisplayed()) {
                    ReusableWebUtils.scrollIntoView(getDriver(), el);
                    try {
                        el.click();
                    } catch (Exception ignored) {
                        ReusableWebUtils.jsClick(getDriver(), el);
                    }
                    return true;
                }
            } catch (Exception ignored) {
                // next
            }
        }
        return false;
    }

    /** Prefer explicit tab widgets (Radix / WAI-ARIA) over loose div text. */
    private boolean clickRoleTabMatchingEmailAndPassword() {
        List<WebElement> tabs = getDriver().findElements(By.xpath("//*[@role='tab']"));
        for (WebElement tab : tabs) {
            try {
                if (!tab.isDisplayed()) {
                    continue;
                }
                String label = tab.getText();
                if (label == null) {
                    continue;
                }
                String compact = label.replace('\n', ' ');
                if (compact.contains("Email") && compact.contains("Password")) {
                    ReusableWebUtils.scrollIntoView(getDriver(), tab);
                    try {
                        tab.click();
                    } catch (Exception ignored) {
                        ReusableWebUtils.jsClick(getDriver(), tab);
                    }
                    return true;
                }
            } catch (Exception ignored) {
                // next tab
            }
        }
        return false;
    }

    public void enterJustoEmailAndPassword(String email, String password) {
        WebElementFacade emailField = $(JUSTO_EMAIL_INPUT).withTimeoutOf(15, TimeUnit.SECONDS);
        try {
            emailField.waitUntilVisible();
        } catch (Exception ignored) {
            emailField = $(EMAIL_LOCATOR).withTimeoutOf(15, TimeUnit.SECONDS);
            emailField.waitUntilVisible();
        }
        emailField.clear();
        emailField.type(email == null ? "" : email);
        WebElementFacade pwdField = $(JUSTO_PASSWORD_INPUT).withTimeoutOf(15, TimeUnit.SECONDS);
        pwdField.waitUntilVisible();
        pwdField.clear();
        pwdField.type(password == null ? "" : password);
    }

    public void clickJustoLoginButton() {
        WebElementFacade btn = $(JUSTO_LOGIN_BUTTON).withTimeoutOf(15, TimeUnit.SECONDS);
        btn.waitUntilClickable().click();
    }

    public void enterUsername(String username) {
        WebElementFacade field = emailInput();
        field.waitUntilVisible();
        field.clear();
        field.type(username == null ? "" : username);
    }

    public void enterPassword(String password) {
        WebElementFacade field = passwordInput();
        field.waitUntilVisible();
        field.clear();
        field.type(password == null ? "" : password);
    }

    public void clickSignInButton() {
        signInButton().waitUntilClickable().click();
    }

    public void clickForgotPasswordLink() {
        clickWithScrollAndJsFallback(forgotLink());
    }

    public void clickCreateAccountButton() {
        clickWithScrollAndJsFallback(createAccountLink());
    }

    private void clickWithScrollAndJsFallback(WebElementFacade link) {
        link.waitUntilPresent();
        WebElement raw = link.getWrappedElement();
        ReusableWebUtils.scrollIntoView(getDriver(), raw);
        try {
            link.withTimeoutOf(8, TimeUnit.SECONDS).waitUntilClickable().click();
        } catch (Exception ignored) {
            ReusableWebUtils.jsClick(getDriver(), raw);
        }
    }

    public void clearUsernameAndPassword() {
        enterUsername("");
        try {
            passwordInput().clear();
        } catch (Exception ignored) {
            // OTP-only screens may omit password input.
        }
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignInButton();
    }

    public boolean isLoginSuccessful() {
        dashboardMarker().withTimeoutOf(25, TimeUnit.SECONDS).waitUntilVisible();
        return dashboardMarker().isCurrentlyVisible();
    }

    public String getLoginFeedbackVisibleText() {
        for (String xpath : LOGIN_FEEDBACK_CANDIDATES) {
            try {
                WebElementFacade feedback = $(xpath);
                feedback.withTimeoutOf(4, TimeUnit.SECONDS).waitUntilVisible();
                String text = feedback.getText().trim();
                if (!text.isBlank()) {
                    return text;
                }
            } catch (Exception ignored) {
                // try next candidate
            }
        }
        throw new org.openqa.selenium.NoSuchElementException("No login feedback / toast matched known patterns.");
    }

    /** Legacy alias used by older steps — maps to combined OTP/password banners. */
    public String getErrorMessageText() {
        return getLoginFeedbackVisibleText();
    }

    /** Legacy alias — validation copy on OTP/email flows. */
    public String getValidationMessageText() {
        return getLoginFeedbackVisibleText();
    }

    public boolean isPasswordFieldMasked() {
        return passwordInput().waitUntilVisible().getAttribute("type") != null
                && "password".equalsIgnoreCase(passwordInput().getAttribute("type"));
    }

    public boolean isOnResetPasswordPage() {
        String url = getDriver().getCurrentUrl();
        return url.matches("(?i).*(reset|forgot|recover).*");
    }

    public boolean isOnSignUpPage() {
        String url = getDriver().getCurrentUrl();
        return url.matches("(?i).*(signup|sign-up|register|sign_up).*");
    }

    public void clickSendOtpButton() {
        sendOtpButton().waitUntilClickable().click();
    }

    public boolean isSendOtpButtonDisabled() {
        WebElementFacade btn = sendOtpButton();
        btn.waitUntilPresent();
        String dis = btn.getAttribute("disabled");
        String aria = btn.getAttribute("aria-disabled");
        return dis != null || "true".equalsIgnoreCase(aria) || !btn.isEnabled();
    }

    /** Used when validating errors without navigating via separate OTP page object. */
    public boolean hasPasswordFieldPresent() {
        return !getDriver().findElements(By.xpath(PASSWORD_LOCATOR)).isEmpty();
    }
}
