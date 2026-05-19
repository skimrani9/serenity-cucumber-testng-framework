package com.automation.framework.steps;

import net.serenitybdd.annotations.Step;
import com.automation.framework.helpers.LeadTestDataReader;
import com.automation.framework.helpers.RandomLeadHelper;
import com.automation.framework.pageObjects.LeadManagementPage;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class LeadManagementActions {

    LeadManagementPage leadManagementPage;

    private String lastGeneratedLeadName;

    @Step("Navigate to Lead Management from main navigation")
    public void navigateToLeadManagement() {
        leadManagementPage.openLeadManagementFromNav();
    }

    @Step("Open Add Lead form")
    public void openAddLeadForm() {
        leadManagementPage.clickCreateNewLead();
    }

    @Step("Fill personal section only with generated name and phone")
    public void fillPersonalInformationOnlyWithRandomData() {
        leadManagementPage.clickMrSalutation();
        lastGeneratedLeadName = RandomLeadHelper.alphabetic(10);
        leadManagementPage.enterLeadFullName(lastGeneratedLeadName);
        leadManagementPage.enterLeadPhone(RandomLeadHelper.indianMobile());
    }

    @Step("Assert Add submit button stays disabled")
    public void assertAddLeadSubmitDisabled() {
        assertTrue("Expected Add button disabled while required fields missing",
                leadManagementPage.isSubmitLeadButtonDisabled());
    }

    @Step("Assert lead creation form visible")
    public void assertLeadCreationFormOpened() {
        leadManagementPage.assertLeadCreationFormVisible();
    }

    @Step("Fill required lead fields from scenario {0} and submit")
    public void createLeadWithRequiredFieldsOnly(String testCaseId) {
        LeadTestDataReader.LeadScenario data = LeadTestDataReader.readScenario(testCaseId);
        if (!data.hasLeadFormDetails()) {
            throw new IllegalStateException("Scenario " + testCaseId + " has no lead form fields in JSON");
        }
        leadManagementPage.clickCreateNewLead();
        leadManagementPage.clickMrSalutation();
        lastGeneratedLeadName = RandomLeadHelper.alphabetic(10);
        leadManagementPage.enterLeadFullName(lastGeneratedLeadName);
        leadManagementPage.enterLeadPhone(RandomLeadHelper.indianMobile());
        leadManagementPage.fillRequiredLeadFieldsFromScenario(data);
        leadManagementPage.submitLeadCreationForm();
    }

    @Step("Assert success toast Lead created successfully")
    public void assertLeadCreatedSuccessVisible() {
        leadManagementPage.waitForLeadCreatedToast();
    }

    @Step("Assert empty list / no results message on Lead Management")
    public void assertNoResultsMessageShown() {
        leadManagementPage.waitForNoResultsMessage();
    }

    @Step("Assert Add Lead button is not available to user")
    public void assertAddLeadButtonNotShown() {
        assertTrue(leadManagementPage.addLeadButtonHiddenOrAbsent());
    }

    @Step("Sort by Full Name and assert column is alphabetically sorted")
    public void assertLeadListSortedByFullNameAscending() {
        List<String> col = leadManagementPage.sortByLeadListHeaderAscendingColumnValues("Full Name");
        long nonBlankRows = col.stream().filter(s -> !s.trim().isEmpty()).count();
        assertTrue("Expected at least one Full Name cell in the grid", nonBlankRows >= 1);
        assertTrue("Sorting requires at least two rows (TC_10); grid has insufficient data", nonBlankRows >= 2);
        assertTrue(String.format("Full Name column not ascending after sort (first 15 cells): %s",
                        col.subList(0, Math.min(15, col.size()))),
                leadManagementPage.isSortedAlphabetically(col));
    }

    @Step("Sort by Owner and assert column is alphabetically sorted")
    public void assertLeadListSortedByOwnerAscending() {
        List<String> col = leadManagementPage.sortByLeadListHeaderAscendingColumnValues("Owner");
        long nonBlankRows = col.stream().filter(s -> !s.trim().isEmpty()).count();
        assertTrue("Expected at least one Owner cell in the grid", nonBlankRows >= 1);
        assertTrue("Sorting requires at least two rows (TC_10); grid has insufficient data", nonBlankRows >= 2);
        assertTrue(String.format("Owner column not ascending after sort (first 15 cells): %s",
                        col.subList(0, Math.min(15, col.size()))),
                leadManagementPage.isSortedAlphabetically(col));
    }

    @Step("Exercise invalid email formats and assert field validation")
    public void assertEmailFieldValidationErrors() {
        leadManagementPage.typeEmailForValidationRound("testtest.com");
        leadManagementPage.waitForEmailFieldValidationVisible();
        leadManagementPage.clearEmailField();
        leadManagementPage.typeEmailForValidationRound("test@test.com@test.com");
        leadManagementPage.waitForEmailFieldValidationVisible();
    }

    @Step("Assert email missing domain validation")
    public void assertEmailMissingDomainValidation() {
        leadManagementPage.typeEmailForValidationRound("test@test");
        leadManagementPage.waitForEmailFieldValidationVisible();
    }

    @Step("Assert email special-character validation (invalid positions)")
    public void assertEmailSpecialCharsValidation() {
        leadManagementPage.typeEmailForValidationRound("test@test.com@test.com");
        leadManagementPage.waitForEmailFieldValidationVisible();
    }

    @Step("Assert phone validation for alphabetic and special characters")
    public void assertPhoneValidationInvalidChars() {
        leadManagementPage.typePhoneForValidation("test123");
        leadManagementPage.waitForPhoneValidationVisible();
        leadManagementPage.typePhoneForValidation("test123@");
        leadManagementPage.waitForPhoneValidationVisible();
    }

    @Step("Create baseline lead then attempt duplicate phone for same project {0}")
    public void duplicatePhoneSameProjectFlow(String testCaseId) {
        LeadTestDataReader.LeadScenario data = LeadTestDataReader.readScenario(testCaseId);
        if (data.contactNumber() == null) {
            throw new IllegalStateException("TC_15 JSON needs contact_number");
        }
        leadManagementPage.clickCreateNewLead();
        leadManagementPage.clickMrSalutation();
        leadManagementPage.enterLeadFullName(RandomLeadHelper.alphabetic(8));
        leadManagementPage.enterLeadPhone(data.contactNumber());
        leadManagementPage.fillRequiredLeadFieldsFromScenario(data);
        leadManagementPage.submitLeadCreationForm();
        leadManagementPage.waitForLeadCreatedToast();
        leadManagementPage.pauseAfterToast();
        leadManagementPage.openLeadManagementFromNav();
        leadManagementPage.clickCreateNewLead();
        leadManagementPage.clickMrSalutation();
        leadManagementPage.enterLeadFullName(RandomLeadHelper.alphabetic(8));
        leadManagementPage.enterLeadPhone(data.contactNumber());
        leadManagementPage.fillRequiredLeadFieldsFromScenario(data);
        leadManagementPage.submitLeadCreationForm();
    }

    @Step("Assert duplicate phone + same project server message")
    public void assertDuplicatePhoneProjectMessage() {
        leadManagementPage.waitForDuplicatePhoneSameProjectMessage();
    }

    public String getLastGeneratedLeadName() {
        return lastGeneratedLeadName;
    }
}
