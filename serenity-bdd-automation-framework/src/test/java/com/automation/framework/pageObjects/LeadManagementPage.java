package com.automation.framework.pageObjects;

import com.automation.framework.helpers.LeadTestDataReader;
import com.automation.framework.helpers.ReusableWebUtils;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Lead Management UI (Robot locator parity documented under {@code docs/JUSTO_ROBOT_TC_REFERENCE.md}).
 */
public class LeadManagementPage extends BasePage {

    /** Dashboard nav — QA path {@code /lead-management}; union covers prefixed SPA {@code href} values. */
    public static final By LEAD_MANAGEMENT_LINK = By.xpath(
            "//a[@href='/lead-management']|//a[contains(@href,'/lead-management')]");
    private static final By ADD_LEAD_CANDIDATES = By.xpath(
            "//button[@data-testid='create-new-lead-button']"
                    + "|//button[contains(normalize-space(.),'Add Lead')]"
                    + "|//button[contains(.,'Add Lead')]"
                    + "|//*[@role='button'][contains(normalize-space(.),'Add Lead')]");
    private static final By MR_SALUTATION = By.xpath("//button[@value='Mr']");
    private static final By LEAD_NAME_FIELD = By.xpath("//input[@placeholder='Enter Full Name']");
    private static final By CONTACT_NUMBER = By.xpath("//input[@placeholder='Enter Phone Number']");
    private static final By EMAIL_FIELD = By.xpath("//input[@placeholder='Enter Email']");
    private static final By ADD_FORM_SUBMIT = By.xpath("//button[contains(text(),'Add')]");
    private static final By SUCCESS_CREATED = By.xpath("//div[contains(text(),'Lead created successfully')]");
    private static final By NO_RESULT = By.xpath("//h3[contains(text(),'No Result found.')]");
    private static final By ERROR_EMAIL = By.xpath("//p[contains(text(),'Please enter a valid email address')]");
    private static final By ERROR_PHONE = By.xpath("//p[contains(text(),'Invalid phone number format. Must be 10 digits starting with 6-9')]");
    private static final By ERROR_DUP_PHONE_PROJECT = By.xpath("//div[contains(text(),'Lead with same phone number and same project already exists')]");

    private static final By PROJECT_SEARCH_INPUT = By.xpath("//input[@placeholder='Search projects...']");

    public void openLeadManagementFromNav() {
        waitABit(1500);
        ensureLeadMgmtNavVisibleInSidebar();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(25));
        wait.until(driver -> firstDisplayedLeadManagementAnchor() != null);
        WebElement anchor = firstDisplayedLeadManagementAnchor();
        ReusableWebUtils.scrollIntoView(getDriver(), anchor);
        try {
            anchor.click();
        } catch (Exception ignored) {
            ReusableWebUtils.jsClick(getDriver(), anchor);
        }
        waitForSpinnerGone();
    }

    /** Expand overflow menu when the sidebar only shows collapsed icons after login. */
    private void ensureLeadMgmtNavVisibleInSidebar() {
        if (firstDisplayedLeadManagementAnchor() != null) {
            return;
        }
        try {
            WebElementFacade more = $(By.xpath("//button[@aria-label='More menus']"));
            more.withTimeoutOf(12, TimeUnit.SECONDS).waitUntilVisible();
            more.click();
            waitABit(900);
        } catch (Exception ignored) {
            // Full sidebar — link may still be mounting.
        }
    }

    /** First anchor matching {@link #LEAD_MANAGEMENT_LINK} that is shown (Serenity $(...) may pick a hidden twin). */
    private WebElement firstDisplayedLeadManagementAnchor() {
        for (WebElement a : getDriver().findElements(LEAD_MANAGEMENT_LINK)) {
            try {
                if (a.isDisplayed()) {
                    return a;
                }
            } catch (Exception ignored) {
                // stale or detached
            }
        }
        return null;
    }

    public void clickCreateNewLead() {
        WebDriverWait urlWait = new WebDriverWait(getDriver(), Duration.ofSeconds(25));
        urlWait.until(driver -> driver.getCurrentUrl().contains("/lead-management"));
        waitForSpinnerGone();
        WebDriverWait waitBtn = new WebDriverWait(getDriver(), Duration.ofSeconds(25));
        waitBtn.until(driver -> firstDisplayedAddLeadButton() != null);
        WebElement btn = firstDisplayedAddLeadButton();
        ReusableWebUtils.scrollIntoView(getDriver(), btn);
        try {
            btn.click();
        } catch (Exception ignored) {
            ReusableWebUtils.jsClick(getDriver(), btn);
        }
        waitABit(400);
        $(MR_SALUTATION).withTimeoutOf(20, TimeUnit.SECONDS).waitUntilVisible();
    }

    /** Prefer {@code data-testid}, then label text; {@code text()} alone misses nested spans inside the button. */
    private WebElement firstDisplayedAddLeadButton() {
        for (WebElement el : getDriver().findElements(ADD_LEAD_CANDIDATES)) {
            try {
                if (el.isDisplayed() && el.isEnabled()) {
                    return el;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return null;
    }

    public void clickMrSalutation() {
        $(MR_SALUTATION).waitUntilClickable().click();
    }

    public void enterLeadFullName(String name) {
        WebElementFacade f = $(LEAD_NAME_FIELD);
        scrollTo(f);
        f.waitUntilVisible().clear();
        f.type(name);
    }

    public void enterLeadPhone(String digits10) {
        WebElementFacade f = $(CONTACT_NUMBER);
        scrollTo(f);
        f.waitUntilVisible().clear();
        f.type(digits10);
    }

    public void scrollTo(WebElementFacade el) {
        el.waitUntilPresent();
        new Actions(getDriver()).moveToElement(el).perform();
    }

    public void selectDropdownOptionContaining(String dropdownTriggerContains, String optionText) {
        String trig = "//span[contains(text(),\"" + escapeQuotes(dropdownTriggerContains) + "\")]";
        WebElementFacade drop = $(By.xpath(trig));
        scrollTo(drop);
        drop.waitUntilClickable().click();
        String opt = "//span[contains(text(),\"" + escapeQuotes(optionText) + "\")]";
        $(By.xpath(opt)).withTimeoutOf(15, TimeUnit.SECONDS).waitUntilVisible().click();
    }

    public void selectProjectBySearch(String projectName) {
        WebElementFacade projTrigger = $(By.xpath("//label[text()='Project']/following::span[text()='Select project'][1]"));
        scrollTo(projTrigger);
        projTrigger.waitUntilClickable().click();
        WebElementFacade search = $(PROJECT_SEARCH_INPUT);
        search.waitUntilVisible();
        search.clear();
        search.type(projectName);
        waitABit(1500);
        String opt = "//div[@role='option' and contains(.,\"" + escapeQuotes(projectName) + "\")]";
        WebElementFacade choice = $(By.xpath(opt));
        choice.withTimeoutOf(15, TimeUnit.SECONDS).waitUntilVisible();
        scrollTo(choice);
        choice.click();
    }

    public void openLocationPreferenceAndPickFirstOption() {
        WebElementFacade pref = $(By.xpath("//span[contains(text(),'Select a location preference')]"));
        scrollTo(pref);
        pref.waitUntilClickable().click();
        waitABit(500);
        List<WebElement> opts = getDriver().findElements(By.xpath(
                "//div[@role='option'] | //span[contains(@class,'option')] | //li[contains(@class,'option')]"));
        if (!opts.isEmpty()) {
            opts.get(0).click();
        } else {
            WebElementFacade fb = $(By.xpath("//span[contains(@class,'option')][1]"));
            fb.waitUntilClickable().click();
        }
    }

    public void submitLeadCreationForm() {
        WebElementFacade btn = $(ADD_FORM_SUBMIT);
        scrollTo(btn);
        btn.withTimeoutOf(15, TimeUnit.SECONDS).waitUntilClickable().click();
    }

    public boolean isSubmitLeadButtonDisabled() {
        WebElementFacade btn = $(ADD_FORM_SUBMIT);
        btn.waitUntilPresent();
        String dis = btn.getAttribute("disabled");
        return dis != null || !btn.isEnabled();
    }

    public void waitForLeadCreatedToast() {
        $(SUCCESS_CREATED).withTimeoutOf(25, TimeUnit.SECONDS).waitUntilVisible();
    }

    public void waitForNoResultsMessage() {
        $(NO_RESULT).withTimeoutOf(15, TimeUnit.SECONDS).waitUntilVisible();
    }

    public void assertLeadCreationFormVisible() {
        $(LEAD_NAME_FIELD).withTimeoutOf(15, TimeUnit.SECONDS).waitUntilVisible();
        $(ADD_FORM_SUBMIT).waitUntilVisible();
    }

    public boolean addLeadButtonHiddenOrAbsent() {
        for (WebElement el : getDriver().findElements(ADD_LEAD_CANDIDATES)) {
            try {
                if (el.isDisplayed()) {
                    return false;
                }
            } catch (Exception ignored) {
                // continue
            }
        }
        return true;
    }

    public void typeEmailForValidationRound(String value) {
        WebElementFacade f = $(EMAIL_FIELD);
        scrollTo(f);
        f.waitUntilVisible().clear();
        f.type(value);
        waitABit(400);
    }

    public void clearEmailField() {
        $(EMAIL_FIELD).clear();
    }

    public void waitForEmailFieldValidationVisible() {
        $(ERROR_EMAIL).withTimeoutOf(10, TimeUnit.SECONDS).waitUntilVisible();
    }

    public void typePhoneForValidation(String value) {
        WebElementFacade f = $(CONTACT_NUMBER);
        scrollTo(f);
        f.waitUntilVisible().clear();
        f.type(value);
        waitABit(400);
    }

    public void waitForPhoneValidationVisible() {
        $(ERROR_PHONE).withTimeoutOf(10, TimeUnit.SECONDS).waitUntilVisible();
    }

    public void waitForDuplicatePhoneSameProjectMessage() {
        $(ERROR_DUP_PHONE_PROJECT).withTimeoutOf(20, TimeUnit.SECONDS).waitUntilVisible();
    }

    public void clickSortHeaderContaining(String textFragment) {
        String xpath = "//th//div[contains(text(),\"" + escapeQuotes(textFragment) + "\")]";
        WebElementFacade h = $(By.xpath(xpath));
        scrollTo(h);
        h.waitUntilClickable().click();
        waitABit(800);
    }

    public List<String> columnTextsForHeader(String headerLabel) {
        int idx = headerColumnIndex(headerLabel);
        if (idx < 1) {
            throw new IllegalStateException("Column header not found: " + headerLabel);
        }
        List<WebElementFacade> cells = findAll(By.xpath("//tbody//tr/td[" + idx + "]"));
        return cells.stream().map(WebElementFacade::getText).map(String::trim).filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int headerColumnIndex(String headerLabel) {
        List<WebElementFacade> headers = findAll(By.xpath("//thead//tr//th"));
        for (int i = 0; i < headers.size(); i++) {
            String txt = headers.get(i).getText().replace('\n', ' ').trim();
            if (txt.contains(headerLabel)) {
                return i + 1;
            }
        }
        return -1;
    }

    public boolean isSortedAlphabetically(List<String> values) {
        List<String> sorted = new ArrayList<>(values);
        sorted.sort(Comparator.comparing(String::toLowerCase));
        return values.equals(sorted);
    }

    public void fillRequiredLeadFieldsFromScenario(LeadTestDataReader.LeadScenario data) {
        selectDropdownOptionContaining("Select a source", data.leadSource());
        waitABit(300);
        selectDropdownOptionContaining("Select a budget", data.budget());
        waitABit(300);
        selectDropdownOptionContaining("Select a pipeline", data.pipeline());
        waitABit(300);
        selectDropdownOptionContaining("Select a stage", data.stage());
        waitABit(300);
        selectDropdownOptionContaining("Select a configuration", data.projectConfiguration());
        waitABit(500);
        selectProjectBySearch(data.projectName());
        waitABit(500);
        openLocationPreferenceAndPickFirstOption();
    }

    private static String escapeQuotes(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private void waitForSpinnerGone() {
        waitABit(1200);
    }

    public void pauseAfterToast() {
        waitABit(2000);
    }
}
