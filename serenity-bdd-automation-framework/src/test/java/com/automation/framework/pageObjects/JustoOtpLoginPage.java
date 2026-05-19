package com.automation.framework.pageObjects;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Justo OTP login screen — supports multiple OTP widget layouts used across environments.
 */
@DefaultUrl("/auth/login")
public class JustoOtpLoginPage extends BasePage {

    private static final By EMAIL_INPUT = By.xpath("//input[@placeholder='Email' or contains(@placeholder, 'email')]");
    private static final By SEND_OTP = By.xpath("//button[contains(.,'OTP') or contains(.,'otp')]");
    private static final By VERIFY_BTN = By.xpath("//button[contains(.,'Verify') or contains(.,'verify')]");
    private static final By SETTINGS_MORE_MENUS = By.xpath("//button[@aria-label='More menus']");
    private static final By LEAD_MGMT_LINK = By.xpath("//a[@href='/lead-management']");

    private static final String OTP_BOX_GENERIC = "//input[@inputmode='numeric' "
            + "or @autocomplete='one-time-code' "
            + "or (@type='text' and @maxlength='1') "
            + "or (@type='tel' and @maxlength='1') "
            + "or (@type='number' and (@maxlength='1' or @maxlength=1))]";

    /** Three slots × two digits — common PIN widget variant. */
    private static final String OTP_PAIR_BOXES = "//input[(@maxlength='2' or @maxlength=2) "
            + "and (@inputmode='numeric' or @type='tel' or @type='text')]";

    public void enterEmail(String email) {
        WebElementFacade el = $(EMAIL_INPUT);
        el.withTimeoutOf(20, TimeUnit.SECONDS).waitUntilVisible();
        el.clear();
        el.type(email);
    }

    public void clickSendOtp() {
        $(SEND_OTP).withTimeoutOf(15, TimeUnit.SECONDS).waitUntilClickable().click();
    }

    /** Brief pause after triggering OTP so the OTP widget can mount (animation / async render). */
    public void pauseAfterSendOtpPrompt() {
        waitABit(1500);
    }

    /**
     * Fills six-digit OTP across separate boxes, a single multi-digit field, or legacy {@code data-testid} slots.
     */
    public void enterOtpDigits(String sixDigits) {
        if (sixDigits == null || sixDigits.length() < 6) {
            throw new IllegalArgumentException("OTP must contain at least 6 digits");
        }
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(45));
        wait.until(this::otpUiPresent);

        List<WebElement> testIdBoxes = findDataTestIdOtpBoxes();
        if (testIdBoxes.size() == 6) {
            typeIntoBoxes(testIdBoxes, sixDigits);
            return;
        }

        List<WebElement> numericSix = visibleInputs(
                getDriver().findElements(By.xpath("//input[@inputmode='numeric']")));
        if (numericSix.size() >= 6) {
            typeIntoBoxes(numericSix.subList(0, 6), sixDigits);
            return;
        }

        List<WebElement> pairBoxes = visibleInputs(getDriver().findElements(By.xpath(OTP_PAIR_BOXES)));
        if (pairBoxes.size() == 3) {
            typeIntoPairBoxes(pairBoxes, sixDigits);
            return;
        }

        List<WebElement> genericBoxes = visibleInputs(getDriver().findElements(By.xpath(OTP_BOX_GENERIC)));
        if (genericBoxes.size() >= 6) {
            typeIntoBoxes(genericBoxes.subList(0, 6), sixDigits);
            return;
        }

        List<WebElement> discovered = discoverNonEmailInputs();
        if (discovered.size() >= 6) {
            typeIntoBoxes(discovered.subList(0, 6), sixDigits);
            return;
        }

        WebElement single = findSingleOtpField();
        if (single != null) {
            single.click();
            single.clear();
            single.sendKeys(sixDigits);
            return;
        }

        throw new IllegalStateException(
                "Could not locate OTP inputs after Send OTP (consider capturing DOM for this environment).");
    }

    /** Visible inputs excluding typical email field / hidden fields — heuristic after OTP step. */
    private List<WebElement> discoverNonEmailInputs() {
        List<WebElement> inputs = visibleInputs(getDriver().findElements(By.tagName("input")));
        List<WebElement> out = new ArrayList<>();
        for (WebElement in : inputs) {
            String type = Optional.ofNullable(in.getAttribute("type")).orElse("").toLowerCase(Locale.ROOT);
            if ("hidden".equals(type) || "checkbox".equals(type) || "radio".equals(type)) {
                continue;
            }
            String ph = Optional.ofNullable(in.getAttribute("placeholder")).orElse("").toLowerCase(Locale.ROOT);
            if (ph.contains("mail") || ph.contains("email") || ph.contains("search")) {
                continue;
            }
            String name = Optional.ofNullable(in.getAttribute("name")).orElse("").toLowerCase(Locale.ROOT);
            if (name.contains("email")) {
                continue;
            }
            out.add(in);
        }
        return out;
    }

    private boolean otpUiPresent(WebDriver driver) {
        if (!findDataTestIdOtpBoxes().isEmpty()) {
            return true;
        }
        if (visibleInputs(driver.findElements(By.xpath("//input[@inputmode='numeric']"))).size() >= 6) {
            return true;
        }
        if (visibleInputs(driver.findElements(By.xpath(OTP_PAIR_BOXES))).size() >= 3) {
            return true;
        }
        if (visibleInputs(driver.findElements(By.xpath(OTP_BOX_GENERIC))).size() >= 6) {
            return true;
        }
        if (discoverNonEmailInputs().size() >= 6) {
            return true;
        }
        if (findSingleOtpField() != null) {
            return true;
        }
        return verifyShownWithLikelyCodeInputs(driver);
    }

    /**
     * Ready when Verify is visible and at least one OTP-style input has appeared (widgets sometimes mount after CTA).
     */
    private boolean verifyShownWithLikelyCodeInputs(WebDriver driver) {
        boolean verifyShown = driver.findElements(By.xpath(
                        "//button[contains(.,'Verify') or contains(.,'verify') or contains(.,'VERIFY')]"))
                .stream()
                .anyMatch(WebElement::isDisplayed);
        if (!verifyShown) {
            return false;
        }
        int nim = visibleInputs(driver.findElements(By.xpath("//input[@inputmode='numeric']"))).size();
        int pairs = visibleInputs(driver.findElements(By.xpath(OTP_PAIR_BOXES))).size();
        int generic = visibleInputs(driver.findElements(By.xpath(OTP_BOX_GENERIC))).size();
        return nim + pairs + generic >= 1;
    }

    private List<WebElement> findDataTestIdOtpBoxes() {
        List<WebElement> boxes = new ArrayList<>(6);
        for (int i = 1; i <= 6; i++) {
            List<WebElement> found = getDriver().findElements(By.xpath("//input[@data-testid='otp-input-" + i + "']"));
            if (found.isEmpty()) {
                return List.of();
            }
            boxes.add(found.get(0));
        }
        return boxes;
    }

    private WebElement findSingleOtpField() {
        List<WebElement> candidates = getDriver().findElements(By.xpath(
                "//input[@maxlength='6' or @maxlength='8'] "
                        + "| //input[@autocomplete='one-time-code'] "
                        + "| //input[@inputmode='numeric' and (@maxlength='6' or @maxlength='8')]"));
        candidates = visibleInputs(candidates);
        return candidates.isEmpty() ? null : candidates.get(0);
    }

    private static List<WebElement> visibleInputs(List<WebElement> inputs) {
        return inputs.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
    }

    private static void typeIntoBoxes(List<WebElement> boxes, String sixDigits) {
        for (int i = 0; i < 6; i++) {
            WebElement box = boxes.get(i);
            box.click();
            box.clear();
            box.sendKeys(String.valueOf(sixDigits.charAt(i)));
        }
    }

    private static void typeIntoPairBoxes(List<WebElement> threeBoxes, String sixDigits) {
        for (int i = 0; i < 3; i++) {
            WebElement box = threeBoxes.get(i);
            box.click();
            box.clear();
            box.sendKeys(sixDigits.substring(i * 2, i * 2 + 2));
        }
    }

    public void clickVerifyLogin() {
        WebElementFacade btn = $(VERIFY_BTN);
        btn.withTimeoutOf(25, TimeUnit.SECONDS).waitUntilClickable();
        btn.click();
    }

    public void waitUntilLoggedInShellVisible() {
        WebDriverWait waitUp = new WebDriverWait(getDriver(), Duration.ofSeconds(35));
        waitUp.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(SETTINGS_MORE_MENUS),
                ExpectedConditions.visibilityOfElementLocated(LEAD_MGMT_LINK)));
    }

    public void dismissPossibleOverlayWithEscape() {
        getDriver().switchTo().activeElement().sendKeys(Keys.ESCAPE);
    }
}
