# ================================================================
# Feature: Login Functionality
# ================================================================
# TEST DATA KEYS (from users.json):
#   "TC_LOGIN_001" → Valid credentials (successful login)
#   "TC_LOGIN_002" → Invalid credentials (login failure)
#   "TC_LOGIN_003" → Empty credentials (validation messages)
#   "TC_LOGIN_004" → Forgot password link navigation (no credentials)
#   "TC_LOGIN_005" → Create account button navigation (no credentials)
#   "TC_LOGIN_006" → Password field masking (password: test@123)
# ================================================================
@login
Feature: Login Functionality
  As a user
  I want to verify all login page functionalities
  So that the login module is secure, functional, and user-friendly

  # ==============================================================
  # TC_LOGIN_001: Verify Successful Login with Valid Credentials
  # Feature       : Login
  # Test Scenario : Verify Login Functionality
  # ==============================================================

  @smoke @positive @TC_LOGIN_001
  Scenario: Verify Successful login with valid credentials
    Given user navigates to the login page
    When user enters credentials using test data "TC_LOGIN_001"
    And user clicks the Sign In button
    Then user should be successfully logged in and redirected to the dashboard

  # ==============================================================
  # TC_LOGIN_002: Verify Login Failure with Invalid Credentials
  # Feature       : Login
  # Test Scenario : Verify Login Functionality
  # ==============================================================

  @regression @negative @TC_LOGIN_002
  Scenario: Verify login failure with invalid credentials
    Given user navigates to the login page
    When user enters credentials using test data "TC_LOGIN_002"
    And user clicks the Sign In button
    Then system should display error message "Invalid username or password"

  # ==============================================================
  # TC_LOGIN_003: Verify Validation When Fields Are Left Empty
  # Feature       : Login
  # Test Scenario : Verify Login Functionality
  # ==============================================================

  @regression @negative @TC_LOGIN_003
  Scenario: Verify validation when fields are left empty
    Given user navigates to the login page
    When user leaves username and password fields blank
    And user clicks the Sign In button
    Then system should display validation message "Username is required"

  # ==============================================================
  # TC_LOGIN_004: Verify 'Forgot?' Link Navigation
  # Feature       : Login
  # Test Scenario : Verify Password Recovery
  # ==============================================================

  @smoke @navigation @TC_LOGIN_004
  Scenario: Verify Forgot password link navigation
    Given user navigates to the login page
    When user clicks the Forgot password link
    Then user should be redirected to the Reset Password page

  # ==============================================================
  # TC_LOGIN_005: Verify 'Create an account' Button Navigation
  # Feature       : Login
  # Test Scenario : Verify Account Creation
  # ==============================================================

  @smoke @navigation @TC_LOGIN_005
  Scenario: Verify Create an account button navigation
    Given user navigates to the login page
    When user clicks the Create an account button
    Then user should be redirected to the Sign Up page

  # ==============================================================
  # TC_LOGIN_006: Verify Password Field Masking
  # Feature       : Login
  # Test Scenario : Verify Security
  # ==============================================================

  @smoke @security @TC_LOGIN_006
  Scenario: Verify Password field masking
    Given user navigates to the login page
    When user enters credentials using test data "TC_LOGIN_006"
    Then the password field should be masked
