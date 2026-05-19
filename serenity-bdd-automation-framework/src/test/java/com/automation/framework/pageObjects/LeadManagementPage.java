package com.automation.framework.pageObjects;

import com.automation.framework.helpers.LeadTestDataReader;
import com.automation.framework.helpers.ReusableWebUtils;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
    /** Intl phone widget often uses {@code type=tel}; match visible national box or fallback to placeholder-only. */
    private static final By CONTACT_NUMBER = By.xpath(
            "//label[contains(normalize-space(.),'Phone Number')]/ancestor::div[position()<=6]"
                    + "//input[@type='tel'][(@placeholder='Enter Phone Number') or contains(@placeholder,'Phone')]"
                    + "|//input[@type='tel' and (@placeholder='Enter Phone Number' or contains(@placeholder,'Phone'))]"
                    + "|//input[@placeholder='Enter Phone Number']");
    private static final By EMAIL_FIELD = By.xpath("//input[@placeholder='Enter Email']");
    private static final By ADD_FORM_SUBMIT = By.xpath("//button[contains(text(),'Add')]");
    private static final By SUCCESS_CREATED = By.xpath("//div[contains(text(),'Lead created successfully')]");
    private static final By NO_RESULT = By.xpath("//h3[contains(text(),'No Result found.')]");
    private static final By ERROR_EMAIL = By.xpath("//p[contains(text(),'Please enter a valid email address')]");
    private static final By ERROR_PHONE = By.xpath(
            "//p[contains(text(),'Invalid phone number format. Must be 10 digits starting with 6-9')]"
                    + "|//p[contains(text(),'Please enter a valid phone number')]");
    private static final By ERROR_DUP_PHONE_PROJECT = By.xpath("//div[contains(text(),'Lead with same phone number and same project already exists')]");

    private static final By PROJECT_SEARCH_INPUT = By.xpath("//input[@placeholder='Search projects...']");

    /** Add Lead — Lead Source combo (QA): span inside button toggles list; first choice is `(//span)[1]` under option. */
    private static final By LEAD_SOURCE_COMBO_BTN = By.xpath(
            "//span[contains(normalize-space(.),'Select a Lead Source')]/ancestor::button");

    /** Add Lead — first option inner label (`(//div[@role='option']//span)[1]`). */
    private static final By LEAD_SOURCE_FIRST_OPTION = By.xpath("(//div[@role='option']//span)[1]");

    private static final By PROJECT_COMBO_BTN = By.xpath(
            "//span[normalize-space(text())='Select a Project']/ancestor::button");

    private static final By CAMPAIGN_COMBO_BTN = By.xpath(
            "//span[normalize-space(text())='Select a Campaign Name']/ancestor::button");

    private static final By FIRST_LISTBOX_OPTION_DIV = By.xpath("(//div[@role='option'])[3]");

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
        replaceLeadPhoneFieldContents(normalizeIndianMobile10(digits10));
    }

    /**
     * Phone field is typically an India (+91) intl combo; keyboard select-all often corrupts the ISD (+80…) and lengths.
     * Use native value setter with E.164; if the DOM {@code value} still does not reflect the digits, retry with national digits only (no Ctrl+A fallback).
     */
    private void replaceLeadPhoneFieldContents(String national10Digits) {
        WebElementFacade f = resolveVisiblePhoneInputFacade();
        scrollTo(f);
        f.waitUntilClickable();
        f.click();
        waitABit(120);
        WebElement raw = f.getWrappedElement();
        String e164 = "+91" + national10Digits;
        applyPhoneValueViaJs(raw, e164);
        blurPhoneField(raw);
        waitABit(200);
        if (!phoneInputLooksValid(raw, national10Digits)) {
            applyPhoneValueViaJs(raw, national10Digits);
            blurPhoneField(raw);
            waitABit(200);
        }
    }

    private void applyPhoneValueViaJs(WebElement raw, String value) {
        ReusableWebUtils.setInputValueTriggeringInputEvent(getDriver(), raw, value);
    }

    private void blurPhoneField(WebElement raw) {
        try {
            raw.sendKeys(Keys.TAB);
        } catch (Exception ignored) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].blur();", raw);
        }
    }

    private boolean phoneInputLooksValid(WebElement raw, String national10Digits) {
        String after = readInputValueFromDom(raw);
        return valueLooksLikeIndianMobileEntered(after, national10Digits);
    }

    private String readInputValueFromDom(WebElement raw) {
        try {
            Object o = ((JavascriptExecutor) getDriver()).executeScript(
                    "return arguments[0].value != null ? String(arguments[0].value) : '';", raw);
            return o == null ? "" : o.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /** Pick first matching phone input that is actually shown (dialogs may duplicate hidden nodes). */
    private WebElementFacade resolveVisiblePhoneInputFacade() {
        List<WebElement> found = getDriver().findElements(CONTACT_NUMBER);
        for (WebElement el : found) {
            try {
                if (el.isDisplayed()) {
                    return $(el);
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return $(CONTACT_NUMBER).withTimeoutOf(12, TimeUnit.SECONDS);
    }

    /** True when {@code htmlValue} parses to ten Indian national digits matching {@code national10Digits}. */
    private static boolean valueLooksLikeIndianMobileEntered(String htmlValue, String national10Digits) {
        if (htmlValue == null || htmlValue.isBlank()) {
            return false;
        }
        String d = htmlValue.replaceAll("\\D", "");
        if (d.endsWith(national10Digits) && national10Digits.length() == 10) {
            return true;
        }
        return d.equals("91" + national10Digits) || d.equals(national10Digits);
    }

    /**
     * Strips spaces/dashes/+91; rejects anything that is not exactly 10 digits starting with 6–9
     * (matches UI copy: "Must be 10 digits starting with 6-9").
     */
    static String normalizeIndianMobile10(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("contact_number is blank");
        }
        String d = raw.replaceAll("\\D", "");
        if (d.startsWith("91") && d.length() == 12) {
            d = d.substring(2);
        }
        if (d.startsWith("0") && d.length() == 11) {
            d = d.substring(1);
        }
        if (d.length() == 10 && d.charAt(0) >= '6' && d.charAt(0) <= '9') {
            return d;
        }
        throw new IllegalArgumentException(
                "Invalid Indian mobile after normalizing (need 10 digits, first 6–9): " + raw);
    }

    public void scrollTo(WebElementFacade el) {
        el.waitUntilPresent();
        new Actions(getDriver()).moveToElement(el).perform();
    }

    public void selectDropdownOptionContaining(String dropdownTriggerContains, String optionText) {
        String trig = "//*[self::span or self::div or self::label][contains(normalize-space(.),\"" 
                + escapeQuotes(dropdownTriggerContains) + "\")]";
        WebElementFacade drop = $(By.xpath(trig));
        scrollTo(drop);
        drop.waitUntilClickable().click();
        waitABit(250);
        String opt =
                "//*[@role='option'][contains(normalize-space(.),\"" + escapeQuotes(optionText) + "\")]"
                + "|//*[self::span or self::div or self::li][contains(normalize-space(.),\"" 
                + escapeQuotes(optionText) + "\")]";
        $(By.xpath(opt)).withTimeoutOf(15, TimeUnit.SECONDS).waitUntilVisible().click();
    }

    /** Lead form dropdown whose trigger label mentions campaign (QA copies may vary slightly). */
    public void selectCampaignNameFromLeadForm(String campaignName) {
        if (campaignName == null || campaignName.isBlank()) {
            return;
        }
        selectDropdownOptionContaining("Select a Campaign Name", campaignName);
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
        WebElementFacade f = resolveVisiblePhoneInputFacade();
        scrollTo(f);
        f.waitUntilClickable();
        f.click();
        f.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        f.sendKeys(Keys.DELETE);
        if (value != null && !value.isEmpty()) {
            f.type(value);
        }
        waitABit(400);
    }

    public void waitForPhoneValidationVisible() {
        $(ERROR_PHONE).withTimeoutOf(10, TimeUnit.SECONDS).waitUntilVisible();
    }

    public void waitForDuplicatePhoneSameProjectMessage() {
        $(ERROR_DUP_PHONE_PROJECT).withTimeoutOf(20, TimeUnit.SECONDS).waitUntilVisible();
    }

    public void clickSortHeaderContaining(String textFragment) {
        String safe = escapeQuotes(textFragment);
        String xpath =
                "//th//*[contains(normalize-space(.),\"" + safe + "\")]|//th[contains(normalize-space(.),\"" 
                        + safe + "\")]";
        WebElementFacade h = $(By.xpath(xpath));
        scrollTo(h);
        h.waitUntilClickable().click();
        waitABit(900);
    }

    /**
     * Clicks header until the visible column reads ascending A–Z (some grids cycle neutral → desc → asc).
     */
    public List<String> sortByLeadListHeaderAscendingColumnValues(String headerLabel) {
        List<String> last = new ArrayList<>();
        for (int pass = 0; pass < 4; pass++) {
            clickSortHeaderContaining(headerLabel);
            waitABit(550);
            last = columnTextsForHeader(headerLabel);
            if (last.size() <= 1 || isSortedAlphabetically(last)) {
                return last;
            }
        }
        return last;
    }

    public List<String> columnTextsForHeader(String headerLabel) {
        int idx = resolveLeadTableTdColumnIndex(headerLabel);
        if (idx < 1) {
            throw new IllegalStateException("Column header not found: " + headerLabel);
        }
        String xp = "//tbody/tr[count(./td)>=" + idx + "]/td[" + idx + "]";
        List<WebElementFacade> cells = findAll(By.xpath(xp));
        return cells.stream().map(WebElementFacade::getText).map(LeadManagementPage::normalizeTableCellText)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Colspan-aware thead mapping, legacy Robot offsets, and content scoring when multiple columns match.
     */
    private int resolveLeadTableTdColumnIndex(String headerLabel) {
        LinkedHashSet<Integer> ordered = collectColumnIndexCandidates(headerLabel);
        LinkedHashSet<Integer> readable = new LinkedHashSet<>();
        for (Integer idx : ordered) {
            if (idx != null && idx > 0 && tdColumnHasSomeReadableText(idx)) {
                readable.add(idx);
            }
        }
        LinkedHashSet<Integer> pool = readable.isEmpty() ? ordered : readable;
        if (pool.isEmpty()) {
            return -1;
        }
        if (pool.size() == 1) {
            return pool.iterator().next();
        }
        return pickBestColumnByContent(pool, headerLabel);
    }

    private LinkedHashSet<Integer> collectColumnIndexCandidates(String headerLabel) {
        LinkedHashSet<Integer> cand = new LinkedHashSet<>();
        int trCount = theadRowCount();
        for (int tr = 1; tr <= trCount; tr++) {
            cand.addAll(columnIndicesMatchingColspanThead(tr, headerLabel));
            cand.addAll(legacyRobotOrdinalPlusTwoCandidates(tr, headerLabel));
            cand.addAll(legacyDomPhysicalThCandidates(tr, headerLabel));
        }
        int brute = bruteForceLikeliestColumnIndex(headerLabel);
        if (brute > 0) {
            cand.add(brute);
        }
        return cand;
    }

    private int theadRowCount() {
        int n = getDriver().findElements(By.xpath("//thead/tr")).size();
        return Math.max(n, 1);
    }

    private List<Integer> columnIndicesMatchingColspanThead(int theadTrOneBased, String headerLabel) {
        List<WebElementFacade> ths = findAll(By.xpath("//thead/tr[" + theadTrOneBased + "]//th"));
        List<Integer> matches = new ArrayList<>();
        int col0 = 0;
        for (WebElementFacade th : ths) {
            int span = parseColspan(th);
            String label = normalizeTableCellText(th.getText());
            if (label.contains(headerLabel)) {
                matches.add(col0 + 1);
            }
            col0 += span;
        }
        return matches;
    }

    private static int parseColspan(WebElementFacade th) {
        try {
            String c = th.getAttribute("colspan");
            if (c == null || c.isBlank()) {
                return 1;
            }
            return Math.max(1, Integer.parseInt(c.trim()));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private List<Integer> legacyRobotOrdinalPlusTwoCandidates(int theadTrOneBased, String headerLabel) {
        List<WebElementFacade> ths = findAll(By.xpath("//thead/tr[" + theadTrOneBased + "]//th"));
        List<Integer> out = new ArrayList<>();
        int ord = -1;
        for (WebElementFacade th : ths) {
            String t = normalizeTableCellText(th.getText());
            if (t.isEmpty()) {
                continue;
            }
            ord++;
            if (t.contains(headerLabel)) {
                out.add(ord + 2);
            }
        }
        return out;
    }

    private List<Integer> legacyDomPhysicalThCandidates(int theadTrOneBased, String headerLabel) {
        List<WebElementFacade> ths = findAll(By.xpath("//thead/tr[" + theadTrOneBased + "]//th"));
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < ths.size(); i++) {
            if (normalizeTableCellText(ths.get(i).getText()).contains(headerLabel)) {
                out.add(i + 1);
            }
        }
        return out;
    }

    private int bruteForceLikeliestColumnIndex(String headerLabel) {
        int max = maxTdsInAnyBodyRow();
        if (max < 1) {
            return -1;
        }
        int best = 1;
        double bestScore = -1;
        for (int c = 1; c <= max; c++) {
            double sc = scoreColumnForHeaderGuess(c, headerLabel);
            if (sc > bestScore) {
                bestScore = sc;
                best = c;
            }
        }
        return bestScore > 0.2 ? best : -1;
    }

    private int maxTdsInAnyBodyRow() {
        int max = 0;
        for (WebElement tr : getDriver().findElements(By.xpath("//tbody/tr"))) {
            max = Math.max(max, tr.findElements(By.tagName("td")).size());
        }
        return max;
    }

    private double scoreColumnForHeaderGuess(int tdIndexOneBased, String headerLabel) {
        List<String> cells = readBodyColumnRawTexts(tdIndexOneBased);
        long nonBlank = cells.stream().filter(s -> !normalizeTableCellText(s).isBlank()).count();
        if (nonBlank == 0) {
            return 0;
        }
        String hl = headerLabel.toLowerCase();
        if (hl.contains("full") && hl.contains("name")) {
            long nameLike = cells.stream().map(LeadManagementPage::normalizeTableCellText)
                    .filter(s -> !s.isBlank() && s.matches("(?s).*(\\p{L}{2,}.*\\s+.*\\p{L}{2,}|\\p{L}{3,}).*"))
                    .count();
            return nameLike * 1.0 / nonBlank;
        }
        if (hl.contains("owner")) {
            long ownerLike = cells.stream().map(LeadManagementPage::normalizeTableCellText)
                    .filter(s -> !s.isBlank() && (s.contains("@") || (s.length() >= 2 && s.length() <= 48))).count();
            return ownerLike * 1.0 / nonBlank;
        }
        return nonBlank * 1.0 / (cells.size() + 1);
    }

    private List<String> readBodyColumnRawTexts(int tdIndexOneBased) {
        String xp = "//tbody/tr[count(./td)>=" + tdIndexOneBased + "]/td[" + tdIndexOneBased + "]";
        return findAll(By.xpath(xp)).stream().map(WebElementFacade::getText).collect(Collectors.toList());
    }

    private int pickBestColumnByContent(LinkedHashSet<Integer> candidates, String headerLabel) {
        int best = candidates.iterator().next();
        double bestScore = -1;
        for (int idx : candidates) {
            double sc = scoreColumnForHeaderGuess(idx, headerLabel);
            if (sc > bestScore) {
                bestScore = sc;
                best = idx;
            }
        }
        return best;
    }

    private boolean tdColumnHasSomeReadableText(int tdIndexOneBased) {
        String xp = "//tbody/tr[position()<=12][count(./td)>=" + tdIndexOneBased + "]/td[" + tdIndexOneBased + "]";
        return findAll(By.xpath(xp)).stream().map(WebElementFacade::getText).map(LeadManagementPage::normalizeTableCellText)
                .anyMatch(s -> !s.isBlank());
    }

    /** Collapses innerText / newline noise so columns match Robot ({@code innerText} split) semantics. */
    private static String normalizeTableCellText(String raw) {
        if (raw == null || raw.isBlank()) {
            return "";
        }
        return raw.replace('\n', ' ').replaceAll("\\s+", " ").trim();
    }

    /** Typical web grids sort case-insensitive; supports blanks pinned before or after text. */
    public boolean isSortedAlphabetically(List<String> values) {
        List<String> n = values.stream().map(LeadManagementPage::normalizeTableCellText)
                .map(LeadManagementPage::stripForSortCompare)
                .collect(Collectors.toCollection(ArrayList::new));
        return isAscendingWithBlankRule(n, true) || isAscendingWithBlankRule(n, false);
    }

    private static boolean isAscendingWithBlankRule(List<String> n, boolean blanksBeforeText) {
        for (int i = 1; i < n.size(); i++) {
            if (compareSortKeys(n.get(i - 1), n.get(i), blanksBeforeText) > 0) {
                return false;
            }
        }
        return true;
    }

    private static int compareSortKeys(String a, String b, boolean blanksBeforeText) {
        boolean ae = a == null || a.isEmpty();
        boolean be = b == null || b.isEmpty();
        if (ae && be) {
            return 0;
        }
        if (ae) {
            return blanksBeforeText ? -1 : 1;
        }
        if (be) {
            return blanksBeforeText ? 1 : -1;
        }
        return String.CASE_INSENSITIVE_ORDER.compare(a, b);
    }

    private static String stripForSortCompare(String cell) {
        if (cell == null || cell.isEmpty()) {
            return "";
        }
        String s = stripLeadingSortIgnoredPrefix(cell);
        return s.replaceFirst("(?i)^(mr|mrs|ms|miss|dr)\\.?\\s+", "").trim();
    }

    /**
     * Strips Sr / row prefixes (e.g. {@code "1 "} or numeric bullet) where present so sorting matches visible name ordering.
     */
    private static String stripLeadingSortIgnoredPrefix(String cell) {
        if (cell == null || cell.isEmpty()) {
            return "";
        }
        return cell.replaceFirst("^\\s*\\d+\\s+", "").trim();
    }

    public void fillRequiredLeadFieldsFromScenario(LeadTestDataReader.LeadScenario data) {
        selectLeadSourceProjectCampaignPickFirstViaComboButtons();
        waitABit(400);
        //openLocationPreferenceAndPickFirstOption();
    }

    /**
     * QA combo flow: ancestor button toggle + first {@code div[role=option]}, with scroll between steps (user XPath parity).
     */
    private void selectLeadSourceProjectCampaignPickFirstViaComboButtons() {
        openComboClickFirstDisplayedOption(LEAD_SOURCE_COMBO_BTN, LEAD_SOURCE_FIRST_OPTION, 18);
        openComboClickFirstDisplayedOption(PROJECT_COMBO_BTN, FIRST_LISTBOX_OPTION_DIV, 18);
        openComboClickFirstDisplayedOption(CAMPAIGN_COMBO_BTN, FIRST_LISTBOX_OPTION_DIV, 18);
    }

    /** Wait for clickable trigger → scroll → click → pick first displayed option matching {@code optionBy}. */
    private void openComboClickFirstDisplayedOption(By comboButtonBy, By optionBy, int waitSec) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(waitSec));
        WebElement combo = wait.until(ExpectedConditions.elementToBeClickable(comboButtonBy));
        scrollAndClickElement(combo);
        waitABit(350);
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionBy));
        scrollAndClickElement(option);
        waitABit(300);
    }

    private void scrollAndClickElement(WebElement element) {
        ReusableWebUtils.scrollIntoView(getDriver(), element);
        waitABit(200);
        try {
            element.click();
        } catch (Exception ignored) {
            ReusableWebUtils.jsClick(getDriver(), element);
        }
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
