# QA Manthan: OTP tab (default) or Email & Password tab — see testdata/users.json.
@login
Feature: Login Functionality

  @smoke @positive @TC_LOGIN_001
  Scenario: Successful login with valid Email & Password credentials
    Given user navigates to the login page
    When user completes OTP authentication using test data "TC_LOGIN_001"
    Then user should land on the authenticated home screen

  @regression @negative @TC_LOGIN_002
  Scenario: OTP request rejected for invalid email
    Given user navigates to the login page
    When user requests OTP with invalid email from test data "TC_LOGIN_002"
    Then login feedback should contain text "Email"

  @regression @negative @TC_LOGIN_003
  Scenario: OTP request with empty email shows validation
    Given user navigates to the login page
    When user requests OTP leaving email empty using test data "TC_LOGIN_003"
    Then the Send OTP button should be disabled

  @smoke @navigation @TC_LOGIN_004
  Scenario: Forgot password link navigation
    Given user navigates to the login page
    When user clicks the Forgot password link
    Then user should be redirected to the Reset Password page

  @smoke @navigation @TC_LOGIN_005
  Scenario: Create an account navigation
    Given user navigates to the login page
    When user clicks the Create an account button
    Then user should be redirected to the Sign Up page

  @wip @security @TC_LOGIN_006
  Scenario: Password field masking on Email & Password tab
    Given user navigates to the login page
    When user opens Email & Password login tab using test data "TC_LOGIN_006"
    Then the password field should be masked
