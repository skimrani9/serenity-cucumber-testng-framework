# Lead Management — Robot parity notes: docs/JUSTO_ROBOT_TC_REFERENCE.md
# Automated: OTP login + LeadManagementPage / LeadManagementActions.

@lead
Feature: Lead Management

  @automated @smoke @TC_03
  Scenario: TC_03 Add Lead opens the lead creation form
    Given admin is logged in via OTP for lead scenario "TC_03"
    When user navigates to Lead Management
    And user opens the Add Lead form
    Then the lead creation form should be displayed

  @automated @TC_04
  Scenario: TC_04 Submit stays disabled when required lead fields are missing
    Given admin is logged in via OTP for lead scenario "TC_04"
    When user navigates to Lead Management
    And user opens the Add Lead form
    And user fills only personal information with random data on Add Lead
    Then the Add Lead submit control should remain disabled

  @automated @TC_05
  Scenario: TC_05 Success notification after adding a lead with required fields
    Given admin is logged in via OTP for lead scenario "TC_05"
    When user navigates to Lead Management
    And user submits a lead using required fields from scenario "TC_05"
    Then the Lead created successfully message should appear

  @automated @TC_06
  Scenario: TC_06 Lead creation succeeds when optional fields are left blank
    Given admin is logged in via OTP for lead scenario "TC_06"
    When user navigates to Lead Management
    And user submits a lead using required fields from scenario "TC_06"
    Then the Lead created successfully message should appear

  @automated @TC_10
  Scenario: TC_10 Lead list sort by Full Name and Owner
    Given admin is logged in via OTP for lead scenario "TC_10"
    When user navigates to Lead Management
    Then Full Name column should be alphabetically sorted after sort
    And Owner column should be alphabetically sorted after sort

  @automated @TC_11
  Scenario: TC_11 Email validation for missing @ and multiple @
    Given admin is logged in via OTP for lead scenario "TC_11"
    When user navigates to Lead Management
    And user exercises invalid email formats on Add Lead email field

  @automated @TC_12
  Scenario: TC_12 Email validation without domain
    Given admin is logged in via OTP for lead scenario "TC_12"
    When user navigates to Lead Management
    And user enters email without domain on Add Lead

  @automated @TC_13
  Scenario: TC_13 Email validation for invalid special-character placement
    Given admin is logged in via OTP for lead scenario "TC_13"
    When user navigates to Lead Management
    And user enters email with invalid special-character placement on Add Lead

  @automated @TC_14
  Scenario: TC_14 Phone validation for alphabetic and special characters
    Given admin is logged in via OTP for lead scenario "TC_14"
    When user navigates to Lead Management
    And user enters invalid phone characters on Add Lead

  @automated @TC_15
  Scenario: TC_15 Duplicate contact number blocked for same project
    Given admin is logged in via OTP for lead scenario "TC_15"
    When user navigates to Lead Management
    And user performs duplicate phone same project flow for scenario "TC_15"
    Then duplicate phone same project error should be displayed
