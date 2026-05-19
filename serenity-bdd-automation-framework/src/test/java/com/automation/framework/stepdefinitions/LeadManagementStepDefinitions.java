package com.automation.framework.stepdefinitions;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import com.automation.framework.steps.JustoOtpLoginActions;
import com.automation.framework.steps.LeadManagementActions;

public class LeadManagementStepDefinitions {

    @Steps
    JustoOtpLoginActions otpLoginActions;

    @Steps
    LeadManagementActions leadManagementActions;

    @Given("admin is logged in via OTP for lead scenario {string}")
    public void admin_is_logged_in_via_otp_for_lead_scenario(String testCaseId) {
        otpLoginActions.loginWithLeadScenario(testCaseId);
    }

    @When("user navigates to Lead Management")
    public void user_navigates_to_lead_management() {
        leadManagementActions.navigateToLeadManagement();
    }

    @When("user opens the Add Lead form")
    public void user_opens_the_add_lead_form() {
        leadManagementActions.openAddLeadForm();
    }

    @Then("the lead creation form should be displayed")
    public void the_lead_creation_form_should_be_displayed() {
        leadManagementActions.assertLeadCreationFormOpened();
    }

    @When("user fills only personal information with random data on Add Lead")
    public void user_fills_only_personal_information_with_random_data_on_add_lead() {
        leadManagementActions.fillPersonalInformationOnlyWithRandomData();
    }

    @Then("the Add Lead submit control should remain disabled")
    public void the_add_lead_submit_control_should_remain_disabled() {
        leadManagementActions.assertAddLeadSubmitDisabled();
    }

    @When("user submits a lead using required fields from scenario {string}")
    public void user_submits_a_lead_using_required_fields_from_scenario(String testCaseId) {
        leadManagementActions.createLeadWithRequiredFieldsOnly(testCaseId);
    }

    @Then("the Lead created successfully message should appear")
    public void the_lead_created_successfully_message_should_appear() {
        leadManagementActions.assertLeadCreatedSuccessVisible();
    }

    @Then("no results empty state should be visible on Lead Management")
    public void no_results_empty_state_should_be_visible_on_lead_management() {
        leadManagementActions.assertNoResultsMessageShown();
    }

    @Then("the Add Lead button should not be available")
    public void the_add_lead_button_should_not_be_available() {
        leadManagementActions.assertAddLeadButtonNotShown();
    }

    @Then("Full Name column should be alphabetically sorted after sort")
    public void full_name_column_should_be_sorted() {
        leadManagementActions.assertLeadListSortedByFullNameAscending();
    }

    @Then("Owner column should be alphabetically sorted after sort")
    public void owner_column_should_be_sorted() {
        leadManagementActions.assertLeadListSortedByOwnerAscending();
    }

    @When("user exercises invalid email formats on Add Lead email field")
    public void user_exercises_invalid_email_formats_on_add_lead_email_field() {
        leadManagementActions.openAddLeadForm();
        leadManagementActions.assertEmailFieldValidationErrors();
    }

    @When("user enters email without domain on Add Lead")
    public void user_enters_email_without_domain_on_add_lead() {
        leadManagementActions.openAddLeadForm();
        leadManagementActions.assertEmailMissingDomainValidation();
    }

    @When("user enters email with invalid special-character placement on Add Lead")
    public void user_enters_email_with_invalid_special_character_placement_on_add_lead() {
        leadManagementActions.openAddLeadForm();
        leadManagementActions.assertEmailSpecialCharsValidation();
    }

    @When("user enters invalid phone characters on Add Lead")
    public void user_enters_invalid_phone_characters_on_add_lead() {
        leadManagementActions.openAddLeadForm();
        leadManagementActions.fillPersonalInformationOnlyWithRandomData();
        leadManagementActions.assertPhoneValidationInvalidChars();
    }

    @When("user performs duplicate phone same project flow for scenario {string}")
    public void user_performs_duplicate_phone_same_project_flow_for_scenario(String testCaseId) {
        leadManagementActions.duplicatePhoneSameProjectFlow(testCaseId);
    }

    @Then("duplicate phone same project error should be displayed")
    public void duplicate_phone_same_project_error_should_be_displayed() {
        leadManagementActions.assertDuplicatePhoneProjectMessage();
    }

    @Given("Robot RBAC Mailinator provisioning for scenario {string} is pending port from Robot resources")
    public void robot_rbac_mailinator_provisioning_pending(String testCaseId) {
        throw new PendingException(
                "Port rbac_management_keywords.resource, add_user_keywords.resource, "
                        + "project_management_keywords.resource (and Mailinator OTP user login) for "
                        + testCaseId + ". See docs/JUSTO_ROBOT_TC_REFERENCE.md.");
    }

    @Given("Lead scenario {string} needs clarified restricted-user URL expectations")
    public void lead_scenario_tc09_pending(String testCaseId) {
        throw new PendingException(
                "TC_09: clarify restricted user + URL expectation (" + testCaseId + ").");
    }
}
