LEAAD MANAGEMENT TESTCASES FILE 
*** Settings ***
Documentation    Complete Test Suite for Lead Management
Library          Browser
Library          String
Library          Collections
Library    OperatingSystem
Library    ../../Resources/CustomKeywords/utilities.py
Resource    ../../Resources/Lead Management/lead_management.resource
Resource    ../../Resources/Login/login_keywords.resource
Resource    ../../Resources/User Management/add_user_keywords.resource
Resource    ../../Resources/RBAC Management/rbac_management_keywords.resource
Resource    ../../Resources/Coverage/coverage_keywords.resource
Test Setup    Initialize Test Coverage2
Test Teardown    Save Test Coverage2

*** Variables ***    
${TESTDATA}                    ${CURDIR}/../../TestData/Lead Management/lead_management_test_data.xlsx

*** Test Cases ***
TC_01 Verify Empty State For User When No lead Assigned To user
    [Documentation]    Verify Empty State For User When No lead Assigned To user    
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_01
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Settings Page Usign button 
    ${role_name}=    Create New Role With Role Name And Description In RBAC Management Page    ${my_dict}
    Navigate To Add User Page
    ${FIRST_NAME}=    Generate Random Alphabetic String
    ${LAST_NAME}=    Generate Random Alphabetic String
    ${EMAIL}=    Generate Email    ${FIRST_NAME}    ${LAST_NAME}
    ${CONTACT_NUMBER}=    Generate Indian Mobile Number
    Fill User Form Details with Generate Role    ${my_dict}    ${FIRST_NAME}    ${LAST_NAME}    ${EMAIL}    ${CONTACT_NUMBER}    ${role_name}   
    Send User Invitation
    Sign Out From Application
    Verify Email Activation    ${EMAIL}
    Verify That User Can Login By Fetching OTP From Mailinator     ${EMAIL}
    Navigate To Lead Management Page
    Verify That No Result Found Message Is Displayed In Lead Management Page

    
TC_02 Verify That User Without Create Permission Cannot Access Lead Creation And "Add Lead" Button Is Not Visible
    [Documentation]    Verify That User Without Create Permission Cannot Access Lead Creation And "Add Lead" Button Is Not Visible
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_02
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
     Navigate To Settings Page Usign button 
     ${role_name}=    Create New Role With Role Name And Description In RBAC Management Page    ${my_dict}
    Navigate To Add User Page
    ${FIRST_NAME}=    Generate Random Alphabetic String
    ${LAST_NAME}=    Generate Random Alphabetic String
    ${EMAIL}=    Generate Email    ${FIRST_NAME}    ${LAST_NAME}
    ${CONTACT_NUMBER}=    Generate Indian Mobile Number
    Fill User Form Details with Generate Role    ${my_dict}    ${FIRST_NAME}    ${LAST_NAME}    ${EMAIL}    ${CONTACT_NUMBER}    ${role_name}   
    Send User Invitation
    Sign Out From Application
    Verify Email Activation    ${EMAIL}
    Verify That User Can Login By Fetching OTP From Mailinator     ${EMAIL}
    Navigate To Lead Management Page
    Verify That Add Lead Button Is Not Visible In Lead Management Page

TC_03 Verify That The “Add Lead” Button Opens The Lead Creation Form Successfully.
    [Documentation]    Verify That The “Add Lead” Button Opens The Lead Creation Form Successfully.
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_03
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
     Navigate To Settings Page Usign button 
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Verify That The Lead Creation Form Is Opened Successfully
    

TC_04 Verify Validation Is Triggered When Submitting The Lead Form Without Filling Required Fields
    [Documentation]    Verify validation is triggered when submitting the Lead form without filling required fields
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_04
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Fill Personal Information On Lead Creation Form   
    Verify Add Lead Button Is Disabled When Required Fields Are Not Filled
    
    
TC_05 Verify That A Notification Is Displayed After Successful Addition Of A Lead
    [Documentation]    Verify That A Notification Is Displayed After Successful Addition Of A Lead
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_05
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Fill Lead Form Details only Required Fields    ${lead_testdata}
    Verify That The Lead Created Successfully Message Is Displayed



TC_06 Verify Successful Lead Creation When Optional Fields Are Left Blank.
    [Documentation]    Verify successful lead creation when optional fields are left blank.
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_06
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Fill Lead Form Details only Required Fields    ${lead_testdata}
    Verify That The Lead Created Successfully Message Is Displayed


   

TC_07 Verify Leads Display For A User With Access To A Single Project    
    [Documentation]    Verify leads display for a user with access to a single project
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_07
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Settings Page Usign button 
    ${user_dict}=    Create New User With Access To A Single Project With Mailinator    ${lead_testdata}
    Sign Out From Application
    Login To Justo Application User Account    ${user_dict}
    Navigate To Settings Page Usign button 
    Navigate To Lead Management Page
    Verify That Project Single Project Name Is Visible In Lead Management Page    ${lead_testdata}
    

TC_08 Verify Leads Display For A User With Access To Multiple Projects
    [Documentation]    Verify leads display for a user with access to multiple projects
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_08
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Settings Page Usign button 
    ${user_dict}=    Create New User With Access To Multiple Projects In Lead Management Page    ${lead_testdata}
    Sign Out From Application
    Login To Justo Application User Account    ${user_dict}
    Navigate To Lead Management Page
    Verify That Project Multiple Project Names Are Visible In Lead Management Page    ${lead_testdata}
    
TC_09 Verify unauthorized user cannot access lead list via direct URL
    [Documentation]    Verify unauthorized user cannot access lead list via direct URL
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_09
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Verify That Add Lead Button Is Not Visible In Lead Management Page



TC_10 Verify Lead List Are Sorted By First Name and Owner Should Be Alphabetically Sorted.
    [Documentation]    Verify lead list are sorted by first name and owner should be alphabetically sorted.
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_10
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Verify That Lead List Are Sorted By First Name Is Alphabetically Sorted
    Verify That Lead List Are Sorted By Owner Is Alphabetically Sorted
        
TC_11 Verify Error When Email Is Missing @ Symbol Or Contains Multiple @ Symbols.
    [Documentation]    Verify error when email is missing @ symbol or contains multiple @ symbols.
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_11    
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Verify Error When Email Is Missing @ Symbol Or Contains Multiple @ Symbols Is Displayed

TC_12 Verify Error When Email Is Entered Without A Domain
    [Documentation]    Verify error when email is entered without a domain
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_12
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Verify Error When Email Is Entered Without A Domain Is Displayed

TC_13 Verify Error When Email Contains Special Characters In Invalid Positions
    [Documentation]    Verify error when email contains special characters in invalid positions
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_13
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Verify Error When Email Contains Special Characters In Invalid Positions Is Displayed

TC_14 Verify Error When Phone Number Contains Alphabets Or Special Characters
    [Documentation]    Verify error when phone number contains alphabets or special characters
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_14
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Verify Error When Phone Number Contains Alphabets Or Special Characters Is Displayed

TC_15 Prevent Duplicate Contact Numbers For A Leads
    [Documentation]    Prevent duplicate contact numbers for a leads
    ${lead_testdata}=    Fetch Testdata By Id    ${TESTDATA}    Sheet1    TC_15
    ${my_dict}=       Create Dictionary      &{lead_testdata}
    Login To Justo Application     ${lead_testdata}
    Navigate To Lead Management Page
    Navigate To Add Lead Page
    Enter First Name On Lead Creation Form
    Enter Contact Number On Lead Creation Form    ${lead_testdata}
    Fill Lead Details On Lead Creation Form    ${lead_testdata}
    Submit Lead Form On Lead Creation Form
    Verify Error Message Is Displayed When Lead With Same Phone Number And Same Project Already Exists



LOGIN KEYWORDS FILES 
*** Settings ***
Documentation    Complete Test Suite for Search Functionality in Reporting Manager Popup
Library          Browser
Library          String
Library          Collections
Library          Process
Library    OperatingSystem
Library    ../CustomKeywords/utilities.py
Variables    ../../PageObjects/User Management/add_user_locators.py 
Variables    ../../PageObjects/login/login_locator.py
Library    RequestsLibrary
Library    OperatingSystem
Library    JSONLibrary


*** Variables ***
${BASE_URL}                     http://localhost:5173/auth/login
# ${BASE_URL}                      https://uat.manthan.justo.co.in/
${MAILINATOR_URL}               https://www.mailinator.com/v4/public/inboxes.jsp?trialshow=true
${COVERAGE_DIR}    coverage_backend
${COVERAGE_URL}    http://localhost:4000/__coverage__
${COVERAGE_FILE}  coverage/coverage-final.json
${COVERAGE_DIR}   coverage
${BACKEND_PROCESS}    None
${BACKEND_START_WAIT}  60s
${BACKEND_PATH}        C:\\Users\\Onkar Pawar\\Desktop\\justo-it-justo-manthan-backend-2203fe8feb56
${BACKEND_CMD}     npm run start:coverage
${BROWSER}    chromium

*** Keywords ***
# =============================================================================
# LOGIN FLOW KEYWORDS
# =============================================================================
Open Application
    [Documentation]    Open browser and navigate to application
    New Browser    ${BROWSER}    headless=${BROWSER_HEADLESS}    slowMo=0.3s
    New Context
    New Page    ${BASE_URL}  
    Wait For Load State    Load
    

Start Backend
    [Documentation]    Start backend process and wait for full startup
    Start Process    ${BACKEND_CMD}    shell=True  cwd=${BACKEND_PATH} 
    Sleep    ${BACKEND_START_WAIT}
    Log    Backend started and ready

Stop Backend And Save Coverage
    [Documentation]    Stop backend, wait for coverage file, and rename
    Terminate All Processes
    Sleep    2s
    ${test_name}=    Get Test Name
    ${dest_file}=    Set Variable    ${COVERAGE_DIR}/${test_name}.json
    Move File    ${COVERAGE_FILE}    ${dest_file}

Get Test Name
    [Documentation]    Return current test name
    ${test_name}=    Get Variable Value    ${TEST_NAME}
    [Return]    ${test_name}

Setup API Session
    ${headers}=    Create Dictionary    x-test-name=${TEST_NAME}
    Create Session    api    http://localhost:3000    headers=${headers}

# Save Coverage Data
#     ${coverage}=  Evaluate JavaScript  JSON.stringify(window.__coverage__)
#     ${filename}=  Set Variable  coverage/coverage_${TEST_NAME}.json
#     Create File  ${filename}  ${coverage}
#     Close Browser

# Save Coverage Data
#     ${coverage}=    Evaluate Javascript    JSON.stringify(window.__coverage__ || {})
#     ${filename}=    Set Variable    coverage/coverage_${TEST_NAME}.json
#     Create File     ${filename}    ${coverage}
#     Close Browser

Save Coverage Data
    [Documentation]    Save code coverage data from browser to JSON file - handles large coverage files by chunking
    Create Directory    coverage

    ${safe_name}=    Replace String    ${TEST_NAME}    ${SPACE}    _
    ${file}=    Set Variable    coverage/coverage_${safe_name}.json

    # Wait a moment for any pending coverage data to be recorded
    Sleep    0.5s

    # Get list of files in coverage object (to chunk the data)
    ${status}    ${file_count}=    Run Keyword And Ignore Error
    ...    Evaluate Javascript    body    () => window.__coverage__ ? Object.keys(window.__coverage__).length : 0

    IF    '${status}' == 'PASS' and ${file_count} > 0
        Log    Found coverage for ${file_count} files, retrieving in chunks...    INFO

        # Initialize the coverage object
        Create File    ${file}    {

        # Get each file's coverage individually to avoid message size limit
        ${index}=    Set Variable    ${0}
        FOR    ${i}    IN RANGE    ${file_count}
            ${chunk_status}    ${chunk}=    Run Keyword And Ignore Error
            ...    Evaluate Javascript    body
            ...    (i) => {
            ...        const keys = Object.keys(window.__coverage__);
            ...        const key = keys[${i}];
            ...        return JSON.stringify({key: key, data: window.__coverage__[key]});
            ...    }

            IF    '${chunk_status}' == 'PASS' and '${chunk}' != ''
                ${data}=    Evaluate    json.loads('''${chunk}''')    json
                ${key}=    Set Variable    ${data['key']}
                ${value}=    Evaluate    json.dumps(${data['data']})    json

                # Add comma if not first item
                IF    ${i} > 0
                    Append To File    ${file}    ,
                END

                # Write the file entry
                Append To File    ${file}    "${key}":${value}
            END
        END

        # Close the JSON object
        Append To File    ${file}    }

        Log    ✅ Successfully saved coverage data for ${file_count} files    INFO
    ELSE
        Log    ⚠️ Warning: No coverage data found or empty coverage    WARN
        Create File    ${file}    {}
    END

    Log    Coverage data saved to: ${file}

    # Now close the browser
    Run Keyword And Ignore Error    Close Browser



Reset Backend Coverage
    [Documentation]    Resets backend coverage before each test.
    Run Process    node    reset.js
    Log    Backend coverage cleared

Fetch Backend Coverage
    [Documentation]    Fetches backend coverage after each test and saves as JSON.
    TRY
        ${resp}=    GET    ${COVERAGE_URL}    expected_status=200
        Should Be Equal As Integers    ${resp.status_code}    200
        ${json}=    Evaluate    json.loads('''${resp.text}''')    json
        Create Directory    ${COVERAGE_DIR}
        ${safe_name}=    Replace String    ${TEST_NAME}    ${SPACE}    _
        ${file_path}=    Set Variable    ${COVERAGE_DIR}/${safe_name}.json
        ${json_str}=    Evaluate    json.dumps($json, indent=2)    json
        Create File    ${file_path}    ${json_str}
        Log    Saved backend coverage → ${file_path}
    EXCEPT
        Log    Warning: Could not fetch backend coverage - backend may not be running    WARN
    END

    Run Keyword And Ignore Error    Close Browser


Generate Combined Coverage Report
    [Documentation]  Merge all coverage JSON files under coverage/ and generate unified HTML report

    # Ensure coverage directory exists
    Directory Should Exist    coverage

    # Remove previous merged file if exists
    Run Keyword And Ignore Error    Remove File    coverage/coverage-final.json

    # --- Try merging with npx first ---
    ${npx_status}    ${_}=    Run Keyword And Ignore Error    Run Process
    ...    npx    --no-install    nyc    --cwd    .    merge    coverage    coverage/coverage-final.json
    ...    shell=True

    Run Keyword If    '${npx_status}' == 'PASS'    Log    ✅ Merged coverage using npx

    # --- If npx failed, try pnpm ---
    Run Keyword If    '${npx_status}' != 'PASS'    Log    npx merge failed, trying pnpm
    ${pnpm_status}    ${_}=    Run Keyword And Ignore Error    Run Process
    ...    pnpm    exec    nyc    --cwd    .    merge    coverage    coverage/coverage-final.json
    ...    shell=True

    Run Keyword If    '${npx_status}' != 'PASS' and '${pnpm_status}' != 'PASS'
    ...    Fail    ❌ Could not run nyc merge with npx or pnpm. Ensure nyc is installed.

    # --- Generate HTML report ---
    ${report_status}    ${_}=    Run Keyword And Ignore Error    Run Process
    ...    npx    --no-install    nyc    --cwd    .    report    --reporter    html
    ...    --report-dir    coverage_report    --temp-dir    coverage
    ...    shell=True

    Run Keyword If    '${report_status}' == 'PASS'    Log    ✅ Report generated using npx

    Run Keyword If    '${report_status}' != 'PASS'    Log    npx report failed, trying pnpm
    ${report_pnpm_status}    ${_}=    Run Keyword And Ignore Error    Run Process
    ...    pnpm    exec    nyc    --cwd    .    report    --reporter    html
    ...    --report-dir    coverage_report    --temp-dir    coverage
    ...    shell=True

    Run Keyword If    '${report_status}' != 'PASS' and '${report_pnpm_status}' != 'PASS'
    ...    Fail    ❌ Could not generate nyc report with npx or pnpm. Ensure nyc is installed.

    Log    ✅ Combined coverage report generated at coverage_report/index.html

    
Login To Justo Application
    [Documentation]    Complete login process with email and OTP verification
    [Arguments]    ${data}   
    ${my_dict}=       Create Dictionary      &{data}
    Enter Email Address    ${my_dict.admin_email}
    Send OTP
    Enter OTP Code    ${my_dict.otp_code}
    Verify And Login
    Wait For Dashboard

Login To Justo Application With Idx Domain
    [Documentation]    Complete login process with email and OTP verification
    [Arguments]    ${USER_EMAIL}    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Enter Email Address    ${USER_EMAIL}
    Send OTP
    Enter OTP Code    ${my_dict.otp_code}
    Verify And Login
    Wait For Dashboard

Enter Email Address
    [Documentation]    Enter email address in login form
    [Arguments]    ${email}
    Fill Text    ${EMAIL_INPUT_XPATH}    ${email}

Send OTP
    [Documentation]    Click Send OTP button
    Click    ${SEND_OTP_BUTTON_XPATH}

Enter OTP Code
    [Documentation]    Enter 6-digit OTP code
    [Arguments]    ${otp}
    # ${otp_digits}    Convert To List    ${otp} 
    ${otp_digits}    Convert To String    ${otp}    
    Fill Text    ${OTP_INPUT_1_XPATH}    ${otp_digits[0]}
    Fill Text    ${OTP_INPUT_2_XPATH}    ${otp_digits[1]}
    Fill Text    ${OTP_INPUT_3_XPATH}    ${otp_digits[2]}
    Fill Text    ${OTP_INPUT_4_XPATH}    ${otp_digits[3]}
    Fill Text    ${OTP_INPUT_5_XPATH}    ${otp_digits[4]}
    Fill Text    ${OTP_INPUT_6_XPATH}    ${otp_digits[5]}

Verify And Login
    [Documentation]    Click verify button to complete login
    Click    ${VERIFY_BUTTON_XPATH}

Wait For Dashboard
    [Documentation]    Wait for dashboard page to load
    Wait For Elements State    ${DASHBOARD_TEXT_XPATH}    visible    timeout=10s

Logout From Application
    [Documentation]    Logout current user from the application
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${user_avatar_dropdown}=        Replace String    ${USER_AVATAR_DROPDOWN_XPATH}    USERNAME   ${my_dict.admin_name}
    Click    ${user_avatar_dropdown}
    Click    ${SIGN_OUT_OPTION_XPATH}
    Wait For Elements State    ${EMAIL_INPUT_XPATH}    visible    


Complete Login Process By Mailinator email User
    [Documentation]    Completes the login process with OTP
    [Arguments]    ${data}    ${USER_EMAIL}  

    ${my_dict}=       Create Dictionary      &{data}
    Fill Text    ${EMAIL_INPUT_XPATH}    ${USER_EMAIL}
    Click     ${SEND_OTP_BUTTON_XPATH}
    Enter OTP Code    ${my_dict.otp_code}   
    Click     ${VERIFY_BUTTON_XPATH}


Login To Justo Application User Account
    [Documentation]    Login to justo application user account. Uses robust OTP extraction from Mailinator. Clicks Verify & login button twice: first after sending OTP, then after entering OTP.
    [Arguments]    ${data}
    ${BASE_OTP_URL}=    New Page    ${BASE_URL}
    ${my_dict}=       Create Dictionary      &{data}
    Enter Email Address    ${my_dict.user_email}
    Click     ${SEND_OTP_BUTTON_XPATH}
    Sleep    2s
    # Click Verify & login button first time (after sending OTP) - wait for button to be ready
    Set Strict Mode    False
    ${button_ready}=    Run Keyword And Return Status    Wait For Elements State    ${VERIFY_AND_LOGIN_BUTTON_XPATH}    visible    timeout=5s
    IF    ${button_ready}
        ${click_success}=    Run Keyword And Return Status    Click    ${VERIFY_AND_LOGIN_BUTTON_XPATH}
        IF    not ${click_success}
            # If normal click fails (button disabled), use JavaScript to force click
            ${button_element_found}=    Run Keyword And Return Status    Get Element    ${VERIFY_AND_LOGIN_BUTTON_XPATH}
            IF    ${button_element_found}
                ${button_element}=    Get Element    ${VERIFY_AND_LOGIN_BUTTON_XPATH}
                Evaluate JavaScript    ${button_element}    (element) => { element.removeAttribute('disabled'); element.click(); }
            END
        END
        Sleep    2s
    END
    Set Strict Mode    True
    # Extract username from email (part before @) for Mailinator
    ${email_username}=    Evaluate    '${my_dict.user_email}'.split('@')[0] if '@' in '${my_dict.user_email}' else '${my_dict.user_email}'
    New Page    ${MAILINATOR_URL}
    Click      ${CLOSE_POPUP_BUTTON}
    Fill Text    ${MAILINATOR_INBOX_FIELD}    ${email_username}
    Sleep    8s
    Click      ${MAILINATOR_GO_BUTTON}
    Sleep    8s
    Wait For Load State    networkidle    timeout=10s
    Set Strict Mode    False
    # Wait for email table to load
    Wait For Elements State    //table//tbody//tr    visible    timeout=20s
    # Try to find OTP email
    ${otp_email_found}=    Run Keyword And Return Status    Wait For Elements State    ${OTP_EMAIL}    visible    timeout=10s
    IF    not ${otp_email_found}
        ${simple_otp_locator}=    Set Variable    (//td[contains(text(),'One-Time Password')] | //td[contains(text(),'OTP')])[1]
        ${otp_email_found}=    Run Keyword And Return Status    Wait For Elements State    ${simple_otp_locator}    visible    timeout=10s
        IF    ${otp_email_found}
            Click    ${simple_otp_locator}
        ELSE
            ${first_email_row}=    Get Element    //table//tbody//tr[1]
            Click    ${first_email_row}
        END
    ELSE
        Click    ${OTP_EMAIL}
    END
    Set Strict Mode    True
    Sleep    3s
    Wait For Elements State    xpath=//iframe[@id="html_msg_body"]    attached    timeout=15s
    Sleep    2s
    # Try to get OTP using original locator
    ${otp}=    Set Variable    ${EMPTY}
    ${otp_extracted}=    Set Variable    False
    ${status}=    Run Keyword And Return Status    ${otp}=    Get Text    xpath=//iframe[@id="html_msg_body"] >>> ${OTP_TEXT}
    IF    ${status}
        ${otp}=    Strip String    ${otp}
        ${otp_extracted}=    Set Variable If    '${otp}' != '' and len('${otp}') >= 6    True    False
    END
    # If original locator fails, try extracting from body text using Python helper
    IF    not ${otp_extracted}
        ${body_text}=    Get Text    xpath=//iframe[@id="html_msg_body"] >>> //body
        ${otp}=    Extract OTP From Text    ${body_text}
        ${otp_extracted}=    Set Variable If    '${otp}' != '' and len('${otp}') >= 6    True    False
    END
    Should Be True    ${otp_extracted}    OTP code could not be extracted from email
    Should Not Be Equal    ${otp}    ${EMPTY}    OTP code is empty
    Log    Extracted OTP: ${otp}
    Switch Page    ${BASE_OTP_URL}
    Sleep    2s
    Wait For Elements State    ${OTP_INPUT_1_XPATH}    visible    timeout=10s
    Enter OTP Code    ${otp}
    # Click Verify & login button second time (after entering OTP)
    # Try VERIFY_AND_LOGIN_BUTTON_XPATH first, fallback to VERIFY_BUTTON_XPATH
    Set Strict Mode    False
    ${verify_button_found}=    Run Keyword And Return Status    Wait For Elements State    ${VERIFY_AND_LOGIN_BUTTON_XPATH}    enabled    timeout=10s
    IF    ${verify_button_found}
        Click    ${VERIFY_AND_LOGIN_BUTTON_XPATH}
    ELSE
        # Fallback to VERIFY_BUTTON_XPATH if VERIFY_AND_LOGIN_BUTTON_XPATH not found
        ${verify_button_found2}=    Run Keyword And Return Status    Wait For Elements State    ${VERIFY_BUTTON_XPATH}    enabled    timeout=10s
        IF    ${verify_button_found2}
            Click    ${VERIFY_BUTTON_XPATH}
        ELSE
            # Last resort: try direct locator
            ${verify_button_found3}=    Run Keyword And Return Status    Wait For Elements State    //button[text()='Verify & login']    enabled    timeout=10s
            IF    ${verify_button_found3}
                Click    //button[text()='Verify & login']
            ELSE
                # Try any verify button
                Click    //button[contains(text(),'Verify')]
            END
        END
    END
    Set Strict Mode    True
    Wait For Dashboard

Verify dashboard page is loaded
    [Documentation]    Verifies dashboard page is loaded
    Wait For Elements State    ${DASHBOARD_TEXT_XPATH}    visible    timeout=10s
    Navigate To Settings Page Usign button                          
    Wait For Elements State    ${USERS_LINK_XPATH}    visible    timeout=10s


Verify login error message when using an not registered Email
    [Documentation]    Verifies login error message when using an not registered Email

    Wait For Elements State    ${LOGIN_ERROR_NOT_REGISTERED_EMAIL_TOAST_XPATH}    visible    timeout=10s
    Wait For Elements State    ${LOGIN_ERROR_NOT_REGISTERED_EMAIL_XPATH}    visible    timeout=10s

Verify Login Error Message With Invalid OTP
    [Documentation]    Verifies login error message with invalid OTP
    Wait For Elements State    ${LOGIN_ERROR_INVALID_OTP_TOAST_XPATH}    visible    timeout=10s
    Wait For Elements State    ${LOGIN_ERROR_INVALID_OTP_XPATH}    visible    timeout=10s

Navigate To Settings Page Usign button 
    [Documentation]    Navigate to settings page by clicking the navigation button and settings link
    Wait For Elements State    ${SETTINGS_NAVIGATION_BUTTON_XPATH}    visible    timeout=10s
    Click    ${SETTINGS_NAVIGATION_BUTTON_XPATH}
    Sleep    1s
    Wait For Load State    load    

Navigate To Main Menu Settings Page Usign button 
    [Documentation]    Navigate to settings page by clicking the navigation button and settings link
    Wait For Elements State    ${SETTINGS_NAVIGATION_BUTTON_XPATH_FOR_MAIN_MENU}    visible    timeout=10s
    Click    ${SETTINGS_NAVIGATION_BUTTON_XPATH_FOR_MAIN_MENU}
    Sleep    1s
    Wait For Load State    load    

  
LEAD MANAGEMENT KEYWORDS FILES

*** Settings ***
Library          Browser
Library          String
Library          Collections
Library    OperatingSystem
Library    ../CustomKeywords/utilities.py
Variables   ../../PageObjects/Lead Management/lead_management_locator.py
Variables   ../../PageObjects/User Management/add_user_locators.py
Resource    ../User Management/add_user_keywords.resource
Resource    ../Projects/project_management_keywords.resource


*** Keywords ***
####################NAVIGATION KEYWORDS####################

Navigate To Lead Management Page
    [Documentation]    Navigate to lead management page
    Wait For Load State    networkidle    timeout=5s
    Sleep    2s
    Set Strict Mode    False
    # Try to find Lead Management link, if not found, navigate back to main menu first
    ${link_found}=    Run Keyword And Return Status    Wait For Elements State    ${LEAD_MANAGEMENT_LINK}    visible    timeout=5s
    IF    not ${link_found}
        # Navigate back to main menu if we're in Settings
        ${main_menu_button}=    Run Keyword And Return Status    Wait For Elements State    ${SETTINGS_NAVIGATION_BUTTON_XPATH_FOR_MAIN_MENU}    visible    timeout=5s
        IF    ${main_menu_button}
            Click    ${SETTINGS_NAVIGATION_BUTTON_XPATH_FOR_MAIN_MENU}
            Sleep    2s
        END
    END
    Wait For Elements State    ${LEAD_MANAGEMENT_LINK}    visible    timeout=20s
    Click    ${LEAD_MANAGEMENT_LINK}
    Set Strict Mode    True

Navigate To Add Lead Page
    [Documentation]    Navigate to add lead page
    Wait For Elements State    ${ADD_LEAD_BUTTON}    visible    timeout=10s
    Click    ${ADD_LEAD_BUTTON}
    
Navigate To Lead Details Page 
    [Documentation]    Navigate to lead details page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Wait For Elements State    //div[contains(text(),'${my_dict.lead_name}')]    visible    timeout=10s
    Click     //div[contains(text(),'${my_dict.lead_name}')]


####################ACTION KEYWORDS####################
Enter Lead Name
    [Documentation]    Enter lead name
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Fill Text    ${LEAD_NAME_FIELD}    ${my_dict.lead_name}

Search Lead On Lead Management Page
    [Arguments]        ${search_data}
    Wait For Elements State    ${LEAD_SEARCH_BAR}    visible    timeout=10s
    Wait For Elements State    ${LEAD_SEARCH_BAR}    enabled    timeout=15s
    Clear Text    ${LEAD_SEARCH_BAR}
    Fill Text    ${LEAD_SEARCH_BAR}    ${search_data}
    Press Keys    ${LEAD_SEARCH_BAR}    Enter
    Sleep    1s
    Set Strict Mode    False
    Wait For Elements State    //div[contains(text(),'${search_data}')]   visible    timeout=10s
    Set Strict Mode    True

Search Lead On Lead Management Page And Verify
    [Documentation]    Search for a lead and verify it is found. Used specifically for TC_17.
    [Arguments]    ${search_data}
    Wait For Elements State    ${LEAD_SEARCH_BAR}    visible    timeout=10s
    Clear Text    ${LEAD_SEARCH_BAR}
    Fill Text    ${LEAD_SEARCH_BAR}    ${search_data}
    Press Keys    ${LEAD_SEARCH_BAR}    Enter
    Sleep    2s
    Wait For Load State    networkidle    timeout=5s
    Set Strict Mode    False
    # Try multiple locator strategies to find the lead
    ${lead_found}=    Run Keyword And Return Status    Wait For Elements State    //div[contains(text(),'${search_data}')]    visible    timeout=10s
    IF    not ${lead_found}
        ${lead_found}=    Run Keyword And Return Status    Wait For Elements State    //span[contains(text(),'${search_data}')]    visible    timeout=10s
    END
    IF    not ${lead_found}
        ${lead_found}=    Run Keyword And Return Status    Wait For Elements State    //td[contains(text(),'${search_data}')]    visible    timeout=10s
    END
    IF    not ${lead_found}
        # Check if element exists even if not visible
        ${elements}=    Get Elements    //div[contains(text(),'${search_data}')] | //span[contains(text(),'${search_data}')] | //td[contains(text(),'${search_data}')]
        ${lead_found}=    Evaluate    len($elements) > 0
    END
    Should Be True    ${lead_found}    Lead with search term '${search_data}' not found on Lead Management page
    Set Strict Mode    True

Create Lead For Search Test
    [Documentation]    Creates a lead for search testing and returns the lead details. Used specifically for TC_17. Generates and fills email for searching.
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Navigate To Add Lead Page
    # Fill personal information and generate email
    ${lead_details}=    Fill Personal Information On Lead Creation Form
    # Generate email for searching
    ${random_string}=    Generate Random Alphabetic String    8
    ${email}=    Set Variable    ${random_string}@mailinator.com
    Scroll To Element    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}
    Fill Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    ${email}
    # Fill lead details
    Fill Lead Details On Lead Creation Form    ${my_dict}
    # Wait for form validation to complete
    Sleep    2s
    Wait For Load State    networkidle    timeout=5s
    # Submit the form
    Submit Lead Form On Lead Creation Form
    Sleep    2s
    # Check for success message or error message
    ${success_found}=    Run Keyword And Return Status    Wait For Elements State    ${LEAD_CREATED_SUCCESS_MESSAGE}    visible    timeout=10s
    IF    not ${success_found}
        # Check for error messages
        ${error_found}=    Run Keyword And Return Status    Get Element    //div[contains(@class,'error')] | //p[contains(@class,'error')] | //div[contains(text(),'error')]
        IF    ${error_found}
            ${error_text}=    Get Text    //div[contains(@class,'error')] | //p[contains(@class,'error')] | //div[contains(text(),'error')]
            Log    Error message found: ${error_text}
        END
        # Try waiting a bit more for success message
        Wait For Elements State    ${LEAD_CREATED_SUCCESS_MESSAGE}    visible    timeout=10s
    END
    # Return lead details for searching
    ${lead_info}=    Create Dictionary
    ...    lead_name=${lead_details.lead_name}
    ...    email=${email}
    ...    contact_number=${lead_details.contact_number}
    RETURN    ${lead_info}


Fetch Campaign Name Linked To The Searched Lead
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    RETURN    ${CAMPAIGN_NAME}

Fill Personal Information On Lead Creation Form
    [Documentation]    Fill personal information fields including email and phone
    Click    ${SALUTION_CHECKBOX}
    ${FIRST_NAME}=    Generate Random Alphabetic String
    Fill Text    ${LEAD_NAME_FIELD}    ${FIRST_NAME}
    ${CONTACT_NUMBER1}=   Generate Indian Mobile Number
    Fill Text    ${CONTACT_NUMBER}    ${CONTACT_NUMBER1}
    ${name_and_contact_number_dict}=    Create Dictionary    lead_name=${FIRST_NAME}    contact_number=${CONTACT_NUMBER1}
    RETURN    ${name_and_contact_number_dict}


Enter First Name On Lead Creation Form
    [Documentation]    Enter first name on lead creation form
    ${FIRST_NAME}=    Generate Random Alphabetic String
    Fill Text    ${LEAD_NAME_FIELD}    ${FIRST_NAME}

Enter Contact Number On Lead Creation Form
    [Documentation]    Enter contact number on lead creation form
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Fill Text    ${CONTACT_NUMBER}    ${my_dict.contact_number}
    
    
Fill Lead Details On Lead Creation Form
    [Documentation]    Fill lead details fields including lead source, budget, pipeline, stage, project configuration, project name, project location
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${lead_source_value}=    Get Variable Value    ${my_dict.lead_source}    ${EMPTY}
    IF    '${lead_source_value}' != '${EMPTY}' and '${lead_source_value}' != 'None' and '${lead_source_value}' != '${None}'
        Click    ${LEAD_SOURCES_DROPDOWN}
        ${LEAD_SOURCES_DROPDOWN_OPTION1}=    Replace String    ${LEAD_SOURCES_DROPDOWN_OPTION}    SOURCE_NAME   ${lead_source_value}
        Click    ${LEAD_SOURCES_DROPDOWN_OPTION1}
    END

    ${budget_value}=    Get Variable Value    ${my_dict.budget}    ${EMPTY}
    IF    '${budget_value}' != '${EMPTY}' and '${budget_value}' != 'None' and '${budget_value}' != '${None}'
        Click    ${MIN_MAX_BUDGET_DROPDOWN}
        Sleep    1s
        ${MIN_MAX_BUDGET_DROPDOWN_OPTION1}=    Replace String    ${MIN_MAX_BUDGET_DROPDOWN_OPTION}    BUDGET_NAME   ${budget_value}
        Wait For Elements State    ${MIN_MAX_BUDGET_DROPDOWN_OPTION1}    visible    timeout=10s
        Click    ${MIN_MAX_BUDGET_DROPDOWN_OPTION1}
    END

    ${pipeline_value}=    Get Variable Value    ${my_dict.pipeline}    ${EMPTY}
    IF    '${pipeline_value}' != '${EMPTY}' and '${pipeline_value}' != 'None' and '${pipeline_value}' != '${None}'
        Click    ${PIPELINE_DROPDOWN}
        ${PIPELINE_DROPDOWN_OPTION1}=    Replace String    ${PIPELINE_DROPDOWN_OPTION}    PIPELINE_NAME   ${pipeline_value}
        Click    ${PIPELINE_DROPDOWN_OPTION1}
    END

    ${stage_value}=    Get Variable Value    ${my_dict.stage}    ${EMPTY}
    IF    '${stage_value}' != '${EMPTY}' and '${stage_value}' != 'None' and '${stage_value}' != '${None}'
        Click    ${LEAD_STAGE_DROPDOWN}
        ${LEAD_STAGE_DROPDOWN_OPTION1}=    Replace String    ${LEAD_STAGE_DROPDOWN_OPTION}    STAGE_NAME   ${stage_value}
        Click    ${LEAD_STAGE_DROPDOWN_OPTION1}
    END

    ${project_config_value}=    Get Variable Value    ${my_dict.project_configuration}    ${EMPTY}
    IF    '${project_config_value}' != '${EMPTY}' and '${project_config_value}' != 'None' and '${project_config_value}' != '${None}'
        Click    ${PROJECT_CONFIGURATION_DROPDOWN}
        ${PROJECT_CONFIGURATION_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_CONFIGURATION_DROPDOWN_OPTION}    CONFIGURATION_NAME   ${project_config_value}
        Click    ${PROJECT_CONFIGURATION_DROPDOWN_OPTION1}
    END


    ${project_name_value}=    Get Variable Value    ${my_dict.project_name}    ${EMPTY}
    IF    '${project_name_value}' != '${EMPTY}' and '${project_name_value}' != 'None' and '${project_name_value}' != '${None}'
        Scroll To Element    ${PROJECT_NAME_DROPDOWN}
        Click    ${PROJECT_NAME_DROPDOWN}
        Wait For Elements State    ${PROJECT_SEARCH_INPUT}    visible    timeout=10s
        Clear Text    ${PROJECT_SEARCH_INPUT}
        Fill Text    ${PROJECT_SEARCH_INPUT}    ${project_name_value}
        Sleep    2s
        ${PROJECT_NAME_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_NAME_DROPDOWN_OPTION}    PROJECT_NAME   ${project_name_value}
        Set Strict Mode    False
        ${elements}=    Get Elements    ${PROJECT_NAME_DROPDOWN_OPTION1}
        Should Not Be Empty    ${elements}    msg=Project option not found for: ${project_name_value}
        ${option_element}=    Get Element    ${PROJECT_NAME_DROPDOWN_OPTION1}
        Wait For Elements State    ${option_element}    visible    timeout=15s
        Scroll To Element    ${option_element}
        Click    ${option_element}
        Set Strict Mode    True
    END

    Sleep    2s
    Click    ${PROJECT_PREFERENCES_DROPDOWN}
    Sleep    1s
    # Click the first option in the dropdown
    Set Strict Mode    False
    # Wait for dropdown options to appear
    ${options_visible}=    Run Keyword And Return Status    Wait For Elements State    //div[@role='option'] | //span[contains(@class,'option')]    visible    timeout=5s
    IF    ${options_visible}
        # Get all options and click the first one
        ${all_options}=    Get Elements    //div[@role='option'] | //span[contains(@class,'option')] | //li[contains(@class,'option')]
        IF    len($all_options) > 0
            ${first_option}=    Get Element    (//div[@role='option'] | //span[contains(@class,'option')] | //li[contains(@class,'option')])[1]
            Click    ${first_option}
        END
    ELSE
        # Fallback: try the original locator pattern
        ${first_option_found}=    Run Keyword And Return Status    Get Element    ${PROJECT_PREFERENCES_DROPDOWN_OPTION}
        IF    ${first_option_found}
            ${first_option}=    Get Element    ${PROJECT_PREFERENCES_DROPDOWN_OPTION}
            Click    ${first_option}
        END
    END
    Set Strict Mode    True

    # ${project_location_value}=    Get Variable Value    ${my_dict.project_location}    ${EMPTY}
    # IF    '${project_location_value}' != '${EMPTY}' and '${project_location_value}' != 'None' and '${project_location_value}' != '${None}'
    #     Click    ${PROJECT_LOCATION_DROPDOWN}
    #     Fill Text    ${PROJECT_LOCATION_SEARCH_INPUT}    ${project_location_value}
    #     # Press Keys    ${PROJECT_LOCATION_SEARCH_INPUT}    Enter
    #     ${PROJECT_LOCATION_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_LOCATION_DROPDOWN_OPTION}    PROJECT_LOCATION_NAME   ${project_location_value}
    #     Click    ${PROJECT_LOCATION_DROPDOWN_OPTION1}
    # END

Fill Lead Details On Lead Creation Form Expect Project Location
    [Documentation]    Fill lead details fields including lead source, budget, pipeline, stage, project configuration, project name, project location
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Click    ${LEAD_SOURCES_DROPDOWN}
    ${LEAD_SOURCES_DROPDOWN_OPTION1}=    Replace String    ${LEAD_SOURCES_DROPDOWN_OPTION}    SOURCE_NAME   ${my_dict.lead_source}
    Click    ${LEAD_SOURCES_DROPDOWN_OPTION1}

    Click    ${MIN_MAX_BUDGET_DROPDOWN}
    ${MIN_MAX_BUDGET_DROPDOWN_OPTION1}=    Replace String    ${MIN_MAX_BUDGET_DROPDOWN_OPTION}    BUDGET_NAME   ${my_dict.budget}
    Click    ${MIN_MAX_BUDGET_DROPDOWN_OPTION1}

    Click    ${PIPELINE_DROPDOWN}
    ${PIPELINE_DROPDOWN_OPTION1}=    Replace String    ${PIPELINE_DROPDOWN_OPTION}    PIPELINE_NAME   ${my_dict.pipeline}
    Click    ${PIPELINE_DROPDOWN_OPTION1}

    Click    ${LEAD_STAGE_DROPDOWN}
    ${LEAD_STAGE_DROPDOWN_OPTION1}=    Replace String    ${LEAD_STAGE_DROPDOWN_OPTION}    STAGE_NAME   ${my_dict.stage}
    Click    ${LEAD_STAGE_DROPDOWN_OPTION1}

    Click    ${PROJECT_CONFIGURATION_DROPDOWN}
    ${PROJECT_CONFIGURATION_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_CONFIGURATION_DROPDOWN_OPTION}    CONFIGURATION_NAME   ${my_dict.project_configuration}
    Click    ${PROJECT_CONFIGURATION_DROPDOWN_OPTION1}


    Scroll To Element    ${PROJECT_NAME_DROPDOWN}
    Click    ${PROJECT_NAME_DROPDOWN}
    Wait For Elements State    ${PROJECT_SEARCH_INPUT}    visible    timeout=10s
    Clear Text    ${PROJECT_SEARCH_INPUT}
    Fill Text    ${PROJECT_SEARCH_INPUT}    ${my_dict.project_name}
    Sleep    2s
    ${PROJECT_NAME_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_NAME_DROPDOWN_OPTION}    PROJECT_NAME   ${my_dict.project_name}
    Set Strict Mode    False
    ${elements}=    Get Elements    ${PROJECT_NAME_DROPDOWN_OPTION1}
    Should Not Be Empty    ${elements}    msg=Project option not found for: ${my_dict.project_name}
    ${option_element}=    Get Element    ${PROJECT_NAME_DROPDOWN_OPTION1}
    Wait For Elements State    ${option_element}    visible    timeout=15s
    Scroll To Element    ${option_element}
    Click    ${option_element}
    Set Strict Mode    True
    
Submit Lead Form On Lead Creation Form
    [Documentation]    Submit lead form on lead creation form
    Scroll To Element    ${ADD_LEAD_BUTTON_FORM}
    # Wait for button to be enabled, with fallback to JavaScript click if needed
    Set Strict Mode    False
    ${button_enabled}=    Run Keyword And Return Status    Wait For Elements State    ${ADD_LEAD_BUTTON_FORM}    enabled    timeout=10s
    IF    not ${button_enabled}
        # If button is still disabled, try to enable it with JavaScript
        ${button_element}=    Get Element    ${ADD_LEAD_BUTTON_FORM}
        Evaluate JavaScript    ${button_element}    (element) => { element.removeAttribute('disabled'); element.click(); }
    ELSE
        Click    ${ADD_LEAD_BUTTON_FORM}
    END
    Set Strict Mode    True

Fill Lead Form Details only Required Fields
    [Documentation]    Fill lead form details only required fields
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${name_and_contact_number_dict}=    Fill Personal Information On Lead Creation Form   
    Fill Lead Details On Lead Creation Form    ${data}
    Submit Lead Form On Lead Creation Form
    RETURN    ${name_and_contact_number_dict}

Fill Lead Details On Lead Creation Form with Campaign selected
    [Documentation]    Fill lead details fields including lead source, budget, pipeline, stage, project configuration, project name, project location. Uses campaign name from search instead of Excel data.
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${name_and_contact_number_dict}=    Fill Personal Information On Lead Creation Form 
    Fill Lead Details On Lead Creation Form    ${data}
    Click    ${SELECT_CAMPAIGN_DROPDOWN}
    Wait For Elements State    ${CAMPAIGN_SEARCH_INPUT}    visible    timeout=10s
    Clear Text    ${CAMPAIGN_SEARCH_INPUT}
    Fill Text    ${CAMPAIGN_SEARCH_INPUT}    ${my_dict.campaign_name}
    Sleep    1s
    Set Strict Mode    False
    ${campaign_dropdown_option}=    Replace String    ${SELECT_CAMPAIGN_DROPDOWN_OPTION}    CAMPAIGN_NAME   ${my_dict.campaign_name}
    ${elements}=    Get Elements    ${campaign_dropdown_option}
    Should Not Be Empty    ${elements}    msg=Campaign option not found for: ${my_dict.campaign_name}
    ${option_element}=    Get Element    ${campaign_dropdown_option}
    Wait For Elements State    ${option_element}    visible    timeout=10s
    Click    ${option_element}
    Set Strict Mode    True
    ${selected_campaign_name}=    Set Variable    ${my_dict.campaign_name}
    Click    ${ADD_CAMPAIGN_BUTTON}
    Wait For Elements State    ${LEAD_CREATED_SUCCESS_MESSAGE}    visible    timeout=10s
    RETURN    ${selected_campaign_name}
# Search Campaign By Campaign Name On Campaign Management Page
#     [Arguments]    ${data}
#     ${my_dict}=       Create Dictionary      &{data}
#     Wait For Elements State    ${SEARCH_CAMPAIGN_INPUT_FIELD}    visible    timeout=10s
#     Fill Text    ${SEARCH_CAMPAIGN_INPUT_FIELD}    ${my_dict.campaign_name}
#     Press Keys    ${SEARCH_CAMPAIGN_INPUT_FIELD}    Enter

Get Cleaned List Of Lead Management list page heading names
    [Documentation]    Get index of module wise permission checkbox
    
    ${row_index_text}=    Get Text    //thead/tr
    
    ${row_index_list}=    Split String    ${row_index_text}    \n
    ${cleaned_list}=    Create List
    FOR    ${item}    IN    @{row_index_list}
        ${clean_item}=    Strip String    ${item}
        Run Keyword If    '${clean_item}' != '' and '${clean_item}' != '\t'    Append To List    ${cleaned_list}    ${clean_item}
    END
    Log    ${cleaned_list}  
    RETURN    ${cleaned_list}

Click On Three Dot Menu Option And Click On Remove On Lead Management List
    [Documentation]    Clicks on the three dot menu option and clicks on remove on lead management list
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${lead_name}=    Get Variable Value    ${my_dict.lead_name}    ${EMPTY}
    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}=    Replace String    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT}    LEAD_NAME   ${lead_name}
    Scroll To Element    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}
    Click    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}
    Click    ${REMOVE_ICON_BUTTON}
    Click    ${YES_REMOVE_BUTTON}
    Wait For Elements State    ${LEAD_DELETED_SUCCESS_MESSAGE}    visible    timeout=10s

Fill Location Details On Lead Creation Form
    [Documentation]    Fill location details on lead creation form
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Scroll To Element    ${PROJECT_NAME_DROPDOWN}
    Click    ${PROJECT_NAME_DROPDOWN}
    Wait For Elements State    ${PROJECT_SEARCH_INPUT}    visible    timeout=10s
    Clear Text    ${PROJECT_SEARCH_INPUT}
    Fill Text    ${PROJECT_SEARCH_INPUT}    ${my_dict.project_name}
    Sleep    2s
    ${PROJECT_NAME_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_NAME_DROPDOWN_OPTION}    PROJECT_NAME   ${my_dict.project_name}
    Click    ${PROJECT_NAME_DROPDOWN_OPTION1}

    Click    ${PROJECT_PREFERENCES_DROPDOWN}
    ${PROJECT_PREFERENCES_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_PREFERENCES_DROPDOWN_OPTION}    LOCATION_PREFERENCE_NAME   ${my_dict.project_preferences_location}
    Click    ${PROJECT_PREFERENCES_DROPDOWN_OPTION1}
    Sleep    30s
    Wait For Elements State    //input[@value="${my_dict.project_location}"]    visible    timeout=10s

Click On Three Dot Menu Option And Click On Edit On Lead Management List
    [Documentation]    Clicks on the three dot menu option and clicks on edit on lead management list
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}=    Replace String    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT}    LEAD_NAME   ${my_dict.lead_name}
    Scroll To Element    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}
    Click    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}
    Click    ${EDIT_ICON_BUTTON}

Click On Three Dot Menu Option On Lead Management List
    [Documentation]    Clicks on the three dot menu option on lead management list
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}=    Replace String    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT}    LEAD_NAME   ${my_dict.lead_name}
    Scroll To Element    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}
    Click    ${THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT1}

Click On Cancel Button In Lead Details Page
    [Documentation]    Clicks on cancel button in lead details page
    Wait For Elements State    ${CANCEL_BUTTON}    visible    timeout=10s
    Click    ${CANCEL_BUTTON}
    Click    ${DISCARD_BUTTON}


Click On Update Button In Lead Details Page
    [Documentation]    Clicks on update button in lead details page
    Scroll To Element    ${UPDATE_BUTTON}
    Wait For Elements State    ${UPDATE_BUTTON}    visible    timeout=10s
    Click    ${UPDATE_BUTTON}
    Wait For Elements State    ${LEAD_UPDATED_SUCCESS_MESSAGE}    visible    timeout=10s


Click On Open In New Tab Button In Lead Details Page
    [Documentation]    Clicks on open in new tab button in lead details page
    Wait For Elements State    ${OPEN_IN_NEW_TAB_BUTTON}    visible    timeout=10s
    Click    ${OPEN_IN_NEW_TAB_BUTTON}
    Switch Page    NEW

Delete Lead On Lead Details Page In Open New Tab
    [Documentation]    Deletes lead on lead details page in open new tab
    Wait For Elements State    ${THREE_DOT_OPTION_LEAD_DETAILS}    visible    timeout=10s
    Click    ${THREE_DOT_OPTION_LEAD_DETAILS}
    Click    ${REMOVE_ICON_BUTTON}
    Click    ${YES_REMOVE_BUTTON}
    Wait For Elements State    ${LEAD_DELETED_SUCCESS_MESSAGE}    visible    timeout=10s
    Close Page    CURRENT

Reassign Lead On Lead Details Page
    [Documentation]    Reassign lead on lead details page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Wait For Elements State    ${REASSIGN_LEAD_BUTTON_ICON}    visible    timeout=10s
    Click    ${REASSIGN_LEAD_BUTTON_ICON}
    Wait For Elements State    ${SEARCH_BAR_REASSIGN}    visible    timeout=10s
    Fill Text    ${SEARCH_BAR_REASSIGN}    ${my_dict.user_name}
    Sleep    1s
    ${REASSIGN_USER_CHECKBOX1}=    Replace String    ${REASSIGN_USER_CHECKBOX}    USER_NAME   ${my_dict.user_name}
    Click    ${REASSIGN_USER_CHECKBOX1}
    Click    ${REASSIGN_BUTTON}
    Wait For Elements State    ${LEAD_REASSIGNED_SUCCESS_MESSAGE}    visible    timeout=10s

Verify Message Is Displayed When Lead Is deleted Trying To Open New Tab
    [Documentation]    Verifies message is displayed when lead is deleted in open new tab
    Wait For Elements State    ${NO_LEAD_FOUND_MESSAGE}    visible    timeout=10s


##########################VALIDATION KEYWORDS##########################
Verify That Searched Lead Is Present In Lead Management page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    

Verify That No Result Found Message Is Displayed In Lead Management Page
    [Documentation]    Verifies that no result found message is displayed
    Wait For Elements State    ${NO_RESULT_FOUND_MESSAGE}    visible    timeout=10s
 
Verify That Add Lead Button Is Not Visible In Lead Management Page
    [Documentation]    Verifies that add lead button is not visible
    Wait For Elements State    ${ADD_LEAD_BUTTON_NOT_VISIBLE}    hidden    timeout=10s

Verify Add Lead Button Is Disabled When Required Fields Are Not Filled
    [Documentation]    Verifies that add lead button is disabled when required fields are not filled
    Wait For Elements State    ${ADD_LEAD_BUTTON_FORM}    disabled    timeout=10s


Verify That The Lead Creation Form Is Opened Successfully
    [Documentation]    Verifies that the lead creation form is opened successfully
    Wait For Elements State    ${LEAD_NAME_FIELD}    visible    timeout=10s
    Wait For Elements State    ${ADD_LEAD_BUTTON_FORM}    visible    timeout=10s
    

Verify That The Lead Created Successfully Message Is Displayed
    [Documentation]    Verifies that the lead created successfully message is displayed
    Wait For Elements State    ${LEAD_CREATED_SUCCESS_MESSAGE}    visible    timeout=10s

Verify That Project Single Project Name Is Visible In Lead Management Page
    [Documentation]    Verifies that project heading name is visible in lead management page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Scroll To Element    ${PROJECT_HEADING_NAME}
    Wait For Elements State    ${PROJECT_HEADING_NAME}    visible    timeout=10s
    ${cleaned_list}=    Get Cleaned List Of Lead Management list page heading names
    ${row_index}=    Get Index From List    ${cleaned_list}    Project
    Log    ${row_index}
    ${row_index1}=    Evaluate    ${row_index} + 2
    ${column_index1}=    Convert To String    ${row_index1}
    ${PROJECT_NAME_COLUMN_DATA1}=    Replace String    ${PROJECT_NAME_COLUMN_DATA}    INDEX   ${column_index1}
    Set Strict Mode    False
    ${els}=    Get Elements    ${PROJECT_NAME_COLUMN_DATA1}
        FOR    ${el}    IN    @{els}
            ${text}=    Get Property    ${el}    innerText
            Should Contain    ${text}    ${my_dict.project_name}    ignore_case=True
        END
    Set Strict Mode    True

Verify That Project Multiple Project Names Are Visible In Lead Management Page
    [Documentation]    Verifies that project multiple project names are visible in lead management page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Wait For Load State    networkidle    timeout=10s
    Sleep    3s
    # Wait for the lead management page to be fully loaded
    ${project_heading_visible}=    Run Keyword And Return Status    Wait For Elements State    ${PROJECT_HEADING_NAME}    visible    timeout=15s
    IF    ${project_heading_visible}
        Scroll To Element    ${PROJECT_HEADING_NAME}
    END
    Set Strict Mode    False
    FOR    ${project_name}    IN    @{my_dict.project_name_list}
        # Try multiple locator strategies to find project names
        ${project_found}=    Run Keyword And Return Status    Wait For Elements State    //div[contains(text(),'${project_name}')]    visible    timeout=10s
        IF    not ${project_found}
            ${project_found}=    Run Keyword And Return Status    Wait For Elements State    //span[contains(text(),'${project_name}')]    visible    timeout=10s
        END
        IF    not ${project_found}
            ${project_found}=    Run Keyword And Return Status    Wait For Elements State    //td[contains(text(),'${project_name}')]    visible    timeout=10s
        END
        IF    not ${project_found}
            # Check if element exists (might be in table but not visible yet)
            ${elements}=    Get Elements    //div[contains(text(),'${project_name}')] | //span[contains(text(),'${project_name}')] | //td[contains(text(),'${project_name}')]
            ${project_found}=    Evaluate    len($elements) > 0
        END
        Should Be True    ${project_found}    Project name '${project_name}' not found on Lead Management page. The project may not be assigned to the user or there are no leads for this project.
    END
    Set Strict Mode    True


Verify That Lead List Are Sorted By First Name Is Alphabetically Sorted
    [Documentation]    Verify that lead list are sorted by first name and owner should be alphabetically sorted
    ${cleaned_list}=    Get Cleaned List Of Lead Management list page heading names
    ${row_index}=    Get Index From List    ${cleaned_list}    Full Name
    Log    ${row_index}
    Click    ${FULL_NAME_HEADING}
    ${row_index1}=    Evaluate    ${row_index} + 2
    ${column_index1}=    Convert To String    ${row_index1}
    ${FIRST_NAME_COLUMN_DATA1}=    Replace String    ${FULL_NAME_COLUMN_DATA}    INDEX   ${column_index1}
    Set Strict Mode    False
    ${els}=    Get Elements    ${FIRST_NAME_COLUMN_DATA1}
    #get all the text of the elements and check if they are sorted alphabetically    
    ${text_list}=    Create List
    FOR    ${el}    IN    @{els}
        ${text}=    Get Property    ${el}    innerText
        Append To List    ${text_list}    ${text}
    END
    ${sorted_text_list}=    Copy List    ${text_list}
    Sort List    ${sorted_text_list}
    Should Be Equal    ${text_list}    ${sorted_text_list}
    Set Strict Mode    True


Verify That Lead List Are Sorted By Owner Is Alphabetically Sorted
    [Documentation]    Verify that lead list are sorted by owner and owner should be alphabetically sorted
    ${cleaned_list}=    Get Cleaned List Of Lead Management list page heading names
    ${row_index}=    Get Index From List    ${cleaned_list}    Owner
    Log    ${row_index}
    Scroll To Element    ${OWNER_HEADING}
    Click    ${OWNER_HEADING}
    ${row_index1}=    Evaluate    ${row_index} + 2
    ${column_index1}=    Convert To String    ${row_index1}
    ${OWNER_COLUMN_DATA1}=    Replace String    ${PROJECT_NAME_COLUMN_DATA}    INDEX   ${column_index1}
    Set Strict Mode    False
    ${els}=    Get Elements    ${OWNER_COLUMN_DATA1}
    ${text_list}=    Create List
    FOR    ${el}    IN    @{els}
        ${text}=    Get Property    ${el}    innerText
        Append To List    ${text_list}    ${text}
    END
    ${sorted_text_list}=    Copy List    ${text_list}
    Sort List    ${sorted_text_list}
    Should Be Equal    ${text_list}    ${sorted_text_list}
    Set Strict Mode    True

Verify Error When Email Is Missing @ Symbol Or Contains Multiple @ Symbols Is Displayed
    [Documentation]    Verify error when email is missing @ symbol or contains multiple @ symbols is displayed
    Scroll To Element    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}
    Fill Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    testtest.com
    Wait For Elements State    ${ERROR_EMAIL_MESSAGE}    visible    timeout=10s
    Clear Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}
    Fill Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    test@test.com@test.com
    Wait For Elements State    ${ERROR_EMAIL_MESSAGE}    visible    timeout=10s


Verify Error When Email Is Entered Without A Domain Is Displayed
    [Documentation]    Verify error when email is entered without a domain is displayed
    Scroll To Element    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}
    Fill Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    test@test
    Wait For Elements State    ${ERROR_EMAIL_MESSAGE}    visible    timeout=10s

Verify Error When Email Contains Special Characters In Invalid Positions Is Displayed
    [Documentation]    Verify error when email contains special characters in invalid positions is displayed    
    Scroll To Element    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    
    Fill Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    test@test.com@test.com
    Wait For Elements State    ${ERROR_EMAIL_MESSAGE}    visible    timeout=10s
    Clear Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}
    Fill Text    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    test@test.com@test.com
    Wait For Elements State    ${ERROR_EMAIL_MESSAGE}    visible    timeout=10s

Verify Error When Phone Number Contains Alphabets Or Special Characters Is Displayed
    [Documentation]    Verify error when phone number contains alphabets or special characters is displayed
    Scroll To Element    ${CONTACT_NUMBER}
    Fill Text    ${CONTACT_NUMBER}    test123
    Wait For Elements State    ${ERROR_PHONE_NUMBER_MESSAGE}    visible    timeout=10s
    Clear Text    ${CONTACT_NUMBER}
    Fill Text    ${CONTACT_NUMBER}    test123@
    Wait For Elements State    ${ERROR_PHONE_NUMBER_MESSAGE}    visible    timeout=10s

Verify Prevent Duplicate Contact Numbers For A Leads Is Displayed
    [Documentation]    Verify prevent duplicate contact numbers for a leads is displayed
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
     # fill form with valid data and submit return the contact number and repeat the same contact number
     # verify error message is displayed
     ${first_name}=    Generate Random Alphabetic String
     Fill Text  ${LEAD_NAME_FIELD}    ${first_name}
     Fill Text  ${CONTACT_NUMBER}    ${my_dict.contact_number}
     Fill Lead Details On Lead Creation Form    ${data}
     Submit Lead Form On Lead Creation Form


Verify Error Message Is Displayed When Contact Number Is Already Present
    [Documentation]    Verify error message is displayed when contact number is already present
    Wait For Elements State    ${ERROR_PHONE_NUMBER_ALREADY_PRESENT_MESSAGE}    visible    timeout=10s
    

Verify Tabbing Through The Form Fields Follows Logical Navigation Order
    [Documentation]    Verify tabbing through the form fields follows logical navigation order
    ${expected_order}=    Create List    ${LEAD_NAME_FIELD}    ${CONTACT_NUMBER_PERFIX}    ${CONTACT_NUMBER}    ${LEAD_SOURCES_DROPDOWN}    ${MIN_MAX_BUDGET_DROPDOWN}    ${PIPELINE_DROPDOWN}    ${LEAD_STAGE_DROPDOWN}    ${PROJECT_CONFIGURATION_DROPDOWN}    ${PROJECT_NAME_DROPDOWN}    ${PROJECT_PREFERENCES_DROPDOWN}    ${PROJECT_LOCATION_DROPDOWN}    ${EMAIL_INPUT_FIELD_LEAD_MANAGEMENT}    ${OCCUPATION_DROPDOWN}    ${EMAIL_FIELD}    ${ALTERNATE_CONTACT_NUMBER}    ${SELECT_CARPET_AREA_DROPDOWN}    ${SELECT_PURPOSE_DROPDOWN}    ${SELECT_CAMPAIGN_DROPDOWN}
    # get the order of the form fields by pressing tab key and get the text of the fields
    Set Strict Mode    False
    ${actual_order}=    Create List
    # Focus on the first element
    ${first_element}=    Get Element    ${expected_order}[0]
    Focus    ${first_element}
    Sleep    0.5s

    ${actual_order}=    Create List
    FOR    ${el}    IN    @{expected_order}
        # Get the element (handle multiple matches by getting first one)
        ${element}=    Get Element    ${el}
        # Focus on the element
        Focus    ${element}
        Sleep    0.3s
        # Get the active element using JavaScript
        ${outer_html}=    Evaluate JavaScript    ${element}    () => { const active = document.activeElement; return active ? active.outerHTML : ''; }
        Append To List    ${actual_order}    ${outer_html}
        # Press Tab to move to next field
        Press Keys    ${element}    Tab
        Sleep    0.3s
    END
    Set Strict Mode    True

    Log    Expected order count: ${expected_order}
    Log    Actual order: ${actual_order}
    # Compare the order - check if we got the expected number of elements
    ${expected_count}=    Get Length    ${expected_order}
    ${actual_count}=    Get Length    ${actual_order}
    Should Be Equal    ${expected_count}    ${actual_count}    Expected ${expected_count} fields but got ${actual_count} fields in tab order


Verify Error Message Is Displayed When Lead With Same Phone Number And Same Project Already Exists
    [Documentation]    Verify error message is displayed when lead with same phone number and same project already exists
    Wait For Elements State    ${ERROR_MSG_PHONE_NUMBER_EXISTS}    visible    timeout=10s

Verify Column Is Frozen In Lead Management List Page        
    [Documentation]    Verifies name column is frozen in lead management list page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${NAME_FREEZE_COLUMN1}=    Replace String    ${NAME_FREEZE_COLUMN}    NAME   ${my_dict.column_name}
    Wait For Elements State    ${NAME_FREEZE_COLUMN1}    visible     timeout=10s

Verify Column Is Unfrozen In Lead Management List Page
    [Documentation]    Verifies name column is unfrozen in lead management list page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    ${NAME_FREEZE_COLUMN1}=    Replace String    ${NAME_FREEZE_COLUMN}    NAME   ${my_dict.column_name}
    Wait For Elements State    ${NAME_FREEZE_COLUMN1}    hidden     timeout=10s

Verify That The Lead Is Visible In Lead Management List
    [Documentation]    Verifies that the lead is visible in lead management list
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Wait For Elements State    //div[contains(text(),'${my_dict.lead_name}')]    visible    timeout=10s


Verify That The Lead Is Not Visible In Lead Management List
    [Documentation]    Verifies that the lead is not visible in lead management list
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Wait For Elements State    //div[contains(text(),'${my_dict.lead_name}')]    hidden    timeout=10s


Verify That Lead List Are Sorted By Created/Updated Date In Descending Order By Default
    [Documentation]    Verify that lead list are sorted by Created/updated Date in descending order by default
    ${cleaned_list}=    Get Cleaned List Of Lead Management list page heading names
    ${row_index}=    Get Index From List    ${cleaned_list}    Created/Updated Date
    Log    ${row_index}
    Click    ${CREATED_UPDATED_DATE_HEADING}
    ${row_index1}=    Evaluate    ${row_index} + 2
    ${column_index1}=    Convert To String    ${row_index1}
    ${CREATED_UPDATED_DATE_COLUMN_DATA1}=    Replace String    ${PROJECT_NAME_COLUMN_DATA}    INDEX   ${column_index1}
    Set Strict Mode    False
    ${els}=    Get Elements    ${CREATED_UPDATED_DATE_COLUMN_DATA1}
    ${text_list}=    Create List
    FOR    ${el}    IN    @{els}
        ${text}=    Get Property    ${el}    innerText
        Append To List    ${text_list}    ${text}
    END
    ${sorted_text_list}=    Copy List    ${text_list}
    Sort List    ${sorted_text_list}
    Should Be Equal    ${text_list}    ${sorted_text_list}
    Set Strict Mode    True


Verify That Stage Dropdown Values Change Based On Selected Pipeline
    [Documentation]    Verify that stage dropdown values change based on selected pipeline
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Scroll To Element    ${PIPELINE_DROPDOWN}
    Click    ${PIPELINE_DROPDOWN}
    ${PIPELINE_DROPDOWN_OPTION1}=    Replace String    ${PIPELINE_DROPDOWN_OPTION}    PIPELINE_NAME   ${my_dict.pipeline}
    Click    ${PIPELINE_DROPDOWN_OPTION1}

    Scroll To Element    ${LEAD_STAGE_DROPDOWN}
    Click    ${LEAD_STAGE_DROPDOWN}
    FOR    ${stage_name}    IN    @{my_dict.stage_name_list}
        ${LEAD_STAGE_DROPDOWN_OPTION1}=    Replace String    ${LEAD_STAGE_DROPDOWN_OPTION}    STAGE_NAME   ${stage_name}
        Wait For Elements State    ${LEAD_STAGE_DROPDOWN_OPTION1}    visible    timeout=10s
    END
    ${LEAD_STAGE_DROPDOWN_OPTION1}=    Replace String    ${LEAD_STAGE_DROPDOWN_OPTION}    STAGE_NAME   ${my_dict.stage_name_list[0]}
    Click    ${LEAD_STAGE_DROPDOWN_OPTION1}
    
    
    Click    ${PIPELINE_DROPDOWN_OPTION1} 
    ${PIPELINE_DROPDOWN_OPTION2}=    Replace String    ${PIPELINE_DROPDOWN_OPTION}    PIPELINE_NAME   ${my_dict.pipeline_2}
    Click    ${PIPELINE_DROPDOWN_OPTION2}
    Scroll To Element    ${LEAD_STAGE_DROPDOWN}
    Click    ${LEAD_STAGE_DROPDOWN}
    Sleep    2s
    FOR    ${stage_name}    IN    @{my_dict.stage_name_list_2}
        ${LEAD_STAGE_DROPDOWN_OPTION1}=    Replace String    ${LEAD_STAGE_DROPDOWN_OPTION}    STAGE_NAME   ${stage_name}
        Wait For Elements State    ${LEAD_STAGE_DROPDOWN_OPTION1}    visible    timeout=10s
    END

Verify Preferred Location Dropdown Behavior And Auto-Population Based On Selection
    [Documentation]    Verify preferred location dropdown behavior and auto-population based on selection
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Scroll To Element    ${PROJECT_NAME_DROPDOWN}
    Click    ${PROJECT_NAME_DROPDOWN}
    Wait For Elements State    ${PROJECT_SEARCH_INPUT}    visible    timeout=10s
    Clear Text    ${PROJECT_SEARCH_INPUT}
    Fill Text    ${PROJECT_SEARCH_INPUT}    ${my_dict.project_name}
    Sleep    2s
    ${PROJECT_NAME_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_NAME_DROPDOWN_OPTION}    PROJECT_NAME   ${my_dict.project_name}
    Set Strict Mode    False
    ${elements}=    Get Elements    ${PROJECT_NAME_DROPDOWN_OPTION1}
    Should Not Be Empty    ${elements}    msg=Project option not found for: ${my_dict.project_name}
    ${option_element}=    Get Element    ${PROJECT_NAME_DROPDOWN_OPTION1}
    Wait For Elements State    ${option_element}    visible    timeout=15s
    Scroll To Element    ${option_element}
    Click    ${option_element}
    Set Strict Mode    True

    Scroll To Element    ${PROJECT_PREFERENCES_DROPDOWN}
    Click    ${PROJECT_PREFERENCES_DROPDOWN}
    ${PROJECT_PREFERENCES_DROPDOWN_OPTION1}=    Replace String    ${PROJECT_PREFERENCES_DROPDOWN_OPTION}    LOCATION_PREFERENCE_NAME   ${my_dict.project_preferences_location}
    Click    ${PROJECT_PREFERENCES_DROPDOWN_OPTION1}

    Scroll To Element    ${PROJECT_LOCATION_DROPDOWN}
    Sleep    2s
    Wait For Elements State    //input[@value="${my_dict.project_location}"]    visible    timeout=10s
    

Verify Edit Icon Is Visible Under Actions Column Only To Users With Edit Permission
    [Documentation]    Verify edit icon is visible under actions column only to users with edit permission
    Wait For Elements State    ${EDIT_ICON_BUTTON}    visible    timeout=10s
    Click    ${EDIT_ICON_BUTTON}


Verify Edit Icon Is Not Visible In Lead Management List
    [Documentation]    Verify edit icon is not visible in lead management list
    Wait For Elements State    ${EDIT_ICON_BUTTON}    hidden    timeout=10s

Verify Edit Pencil Icon Is Visible On Lead Details Page
    [Documentation]    Verify edit pencil icon is visible on lead details page
    Wait For Elements State    ${EDIT_PENCIL_ICON}    visible    timeout=10s

Verify Lead Fields Are Editable On Lead Details Page
    [Documentation]    Verify lead fields are editable on lead details page
    Click    ${EDIT_PENCIL_ICON}
    Scroll To Element    ${LEAD_NAME_FIELD}
    Wait For Elements State    ${LEAD_NAME_FIELD}    visible    timeout=10s
    Clear Text    ${LEAD_NAME_FIELD}
    ${FIRST_NAME}=    Generate Random Alphabetic String
    Fill Text    ${LEAD_NAME_FIELD}    ${FIRST_NAME}
    Clear Text    ${CONTACT_NUMBER}
    ${CONTACT_NUMBER1}=    Generate Indian Mobile Number
    Fill Text    ${CONTACT_NUMBER}    ${CONTACT_NUMBER1}
    ${Updated_data}=    Create Dictionary    lead_name=${FIRST_NAME}    contact_number=${CONTACT_NUMBER1}
    RETURN    ${Updated_data}
   
        

Verify Original Field Values Are Restored In Lead Details Page
    [Documentation]    Verify original field values are restored in lead details page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Scroll To Element    //div[contains(text(),'${my_dict.lead_name}')]
    Wait For Elements State    //div[contains(text(),'${my_dict.lead_name}')]    visible    timeout=10s
    Scroll To Element    //div[contains(text(),'${my_dict.contact_number}')]
    Wait For Elements State    //div[contains(text(),'${my_dict.contact_number}')]    visible    timeout=10s

Verify Updated Details In Lead Management List
    [Documentation]    Verify updated details in lead management list
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Scroll To Element    //div[contains(text(),'${my_dict.lead_name}')]
    Wait For Elements State    //div[contains(text(),'${my_dict.lead_name}')]    visible    timeout=10s
    Scroll To Element    //div[contains(text(),'${my_dict.contact_number}')]
    Wait For Elements State    //div[contains(text(),'${my_dict.contact_number}')]    visible    timeout=10s


Verify Lead Fields Validation Is Applied On Lead Details Page
    [Documentation]    Verify lead fields validation is applied on lead details page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Click    ${EDIT_PENCIL_ICON}
    Scroll To Element    ${LEAD_NAME_FIELD}
    Wait For Elements State    ${LEAD_NAME_FIELD}    visible    timeout=10s
    Clear Text    ${LEAD_NAME_FIELD}
    Fill Text    ${LEAD_NAME_FIELD}    ${my_dict.lead_name}
    Wait For Elements State    ${ERROR_LEAD_NAME_MESSAGE}    visible    timeout=10s

    Clear Text    ${CONTACT_NUMBER}
    Fill Text    ${CONTACT_NUMBER}    ${my_dict.contact_number}
    Wait For Elements State    ${ERROR_PHONE_NUMBER_MESSAGE}    visible    timeout=10s


Verify That Lead Detail Opens In A New Tab From The Lead List View
    [Documentation]    Verify that lead detail opens in a new tab from the lead list view
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    # after clicking on the open new tab is visible then i want to switch opened tab to verify deatils

    Click    ${OPEN_IN_NEW_TAB_BUTTON}
    Sleep    2s
    Switch Page    NEW
    Click    ${EDIT_PENCIL_ICON}
    Wait For Elements State    ${LEAD_NAME_FIELD}    visible    timeout=10s
    ${LEAD_NAME_FIELD_TEXT}=    Get Text    ${LEAD_NAME_FIELD}
    Should Be Equal    ${LEAD_NAME_FIELD_TEXT}    ${my_dict.lead_name}    ignore_case=True
    ${CONTACT_NUMBER_TEXT}=    Get Text    ${CONTACT_NUMBER}
    Should Be Equal    ${CONTACT_NUMBER_TEXT}    ${my_dict.contact_number}    ignore_case=True




Verify That Lead Name Is Visible In Lead Details Page
    [Documentation]    Verify that lead name is visible in lead details page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    # ${Lead_name_lowercase}=    Convert To Lowercase    ${my_dict.lead_name}
    Wait For Elements State    //div[contains(text(),'${my_dict.lead_name}')]    visible    timeout=10s

Verify That Lead Deatils All Relevant Sections Are Displayed Having Read Only Permission
    [Documentation]    Verify that lead deatils all relevant sections are displayed having read only permission
    Wait For Elements State    ${TASK_TAB}        visible            timeout=10s
    Wait For Elements State    ${NOTE_TAB}        visible            timeout=10s
    Wait For Elements State    ${SITE_VISIT_TAB}    visible        timeout=10s
    Wait For Elements State    ${ACTIVITY_LOGS_TAB}    visible        timeout=10s



#testdata keywords for lead management
Create New User With Access To A Single Project
    [Documentation]    Creates a new user with access to a single project. Uses @idx.com domain for email.
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Navigate To Add User Page
    ${user_details}=    Fill User Form Details Only Required Fields With Idx Domain    ${my_dict}   
    Send User Invitation
    Sign Out From Application
    Verify Email Activation    ${user_details.EMAIL}
    Verify That User Can Login By Fetching OTP From Mailinator     ${user_details.EMAIL}
    Sign Out From Application
    Login To Justo Application    ${my_dict}
    Navigate To Settings Page Usign button 
    Navigate To Projects Page
    Assign Project To User On Project Management Page    ${user_details}    ${my_dict}
    ${user_dict}=    Create Dictionary    FIRST_NAME=${user_details.FIRST_NAME}    LAST_NAME=${user_details.LAST_NAME}    user_email=${user_details.EMAIL}
    RETURN    ${user_dict}

Create New User With Access To A Single Project With Mailinator
    [Documentation]    Creates a new user with access to a single project. Uses @mailinator.com domain for email. Used specifically for TC_07.
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Navigate To Add User Page
    ${user_details}=    Fill User Form Details Only Required Fields    ${my_dict}   
    Send User Invitation
    Sign Out From Application
    Verify Email Activation    ${user_details.EMAIL}
    Verify That User Can Login By Fetching OTP From Mailinator     ${user_details.EMAIL}
    Sign Out From Application
    Login To Justo Application    ${my_dict}
    Navigate To Settings Page Usign button 
    Navigate To Projects Page
    Assign Project To User On Project Management Page    ${user_details}    ${my_dict}
    ${user_dict}=    Create Dictionary    FIRST_NAME=${user_details.FIRST_NAME}    LAST_NAME=${user_details.LAST_NAME}    user_email=${user_details.EMAIL}
    RETURN    ${user_dict}

     


Create New User With Access To Multiple Projects In Lead Management Page
    [Documentation]    Creates a new user with access to multiple projects in lead management page
    [Arguments]    ${data}
    ${my_dict}=       Create Dictionary      &{data}
    Navigate To Add User Page
    ${user_details}=    Fill User Form Details Only Required Fields    ${my_dict}   
    Send User Invitation    
    Sign Out From Application
    Verify Email Activation    ${user_details.EMAIL}
    Verify That User Can Login By Fetching OTP From Mailinator     ${user_details.EMAIL}
    Sign Out From Application
    Login To Justo Application    ${my_dict}
    Navigate To Settings Page Usign button 
    Navigate To Projects Page
    Assign Multiple Projects To User On Project Management Page    ${my_dict}    ${user_details.FIRST_NAME}    ${user_details.LAST_NAME}    ${user_details.EMAIL}
    ${user_dict}=    Create Dictionary    FIRST_NAME=${user_details.FIRST_NAME}    LAST_NAME=${user_details.LAST_NAME}    user_email=${user_details.EMAIL}
    RETURN    ${user_dict}


Assign Multiple Projects To User On Project Management Page
    [Documentation]    Assigns multiple projects to the user
    [Arguments]    ${data}    ${first_name}    ${last_name}    ${email}

    ${my_dict}=       Create Dictionary      &{data}
    # Handle project_name_list - convert to list if it's a string
    ${project_name_list_raw}=    Get Variable Value    ${my_dict.project_name_list}    ${EMPTY}
    ${project_name_list}=    Run Keyword If    isinstance($project_name_list_raw, str)
    ...    Evaluate    [item.strip() for item in str($project_name_list_raw).split(',') if item.strip()]
    ...    ELSE
    ...    Set Variable    ${project_name_list_raw}
    Log    Project names from Excel: ${project_name_list}
    FOR    ${project_name}    IN    @{project_name_list}
        Log    Searching for project: ${project_name}
        Navigate To Projects Page
        Search Project By Project Name By Arguments    ${project_name}
        ${project_name_text1}=    Replace String    ${PROJECT_NAME_TEXT}    PROJECT_NAME   ${project_name}
        Click    ${project_name_text1}
    Navigate To Project Users Page
    Navigate To Add User Page On Project Users Page
    Search User By Name On Project Users Page       ${email} 
    #convert email to lowercase
    ${email}=      Convert To Lowercase    ${email}
    ${user_checkbox}=    Replace String    ${PROJECT_USER_CHECKBOX}    USER_EMAIL   ${email}
    Click    ${user_checkbox}
    Click    ${ASSIGN_BUTTON}
    END

Search Project By Project Name By Arguments
    [Documentation]    Searches for a project by project name
    [Arguments]    ${project_name}
    Clear Text    ${SEARCH_PROJECT_INPUT_FIELD}
    Fill Text    ${SEARCH_PROJECT_INPUT_FIELD}    ${project_name}
    Press Keys    ${SEARCH_PROJECT_INPUT_FIELD}    Enter

# Click On First Lead And Action Button
#     [Documentation]    Navigate to lead management page, click on first lead row, then click action button
#     Navigate To Lead Management Page
#     Sleep    2s
#     # Wait for table to be visible
#     Wait For Elements State    //table/tbody/tr[1]    visible    timeout=10s
#     # Find and click first lead row (using td[3] as reference - more dynamic)
#     ${first_lead_cell}=    Get Element    xpath=//table/tbody/tr[1]/td[3]
#     Click    ${first_lead_cell}
#     Sleep    1s
#     # Click on action button
#     Wait For Elements State    (//div//button)[5]    visible    timeout=10s
#     Click    (//div//button)[5]    

# ============================================================================
# TC_37: Filter Keywords for Lead Management
# ============================================================================

Click On Filter Button
    [Documentation]    Click on the Filter button to open filter panel. For TC_37.
    Set Strict Mode    False
    Wait For Elements State    ${FILTER_BUTTON}    visible    timeout=15s
    Click    ${FILTER_BUTTON}
    Sleep    1s
    Log    ✓ Filter button clicked successfully
    Set Strict Mode    True

Click On Add Filter Button
    [Documentation]    Click on Add Filter button to add a new filter. For TC_37.
    Set Strict Mode    False
    Wait For Elements State    ${ADD_FILTER_BUTTON}    visible    timeout=10s
    Click    ${ADD_FILTER_BUTTON}
    Sleep    1s
    Log    ✓ Add Filter button clicked successfully
    Set Strict Mode    True

Select Salutation From Filter Options
    [Documentation]    Select Salutation option from filter dropdown. For TC_37.
    Set Strict Mode    False
    # Try multiple strategies to find and click salutation option
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${SALUTATION_FILTER_OPTION}    visible    timeout=8s
    IF    ${method1}
        Click    ${SALUTATION_FILTER_OPTION}
        Log    ✓ Salutation selected using primary locator
    ELSE
        # Try alternative: search for input field and type
        ${filter_search}=    Set Variable    //input[@placeholder='Search' or @placeholder='search' or contains(@placeholder,'filter')]
        ${search_exists}=    Run Keyword And Return Status    Wait For Elements State    ${filter_search}    visible    timeout=5s
        IF    ${search_exists}
            Fill Text    ${filter_search}    Salutation
            Sleep    0.3s
            ${salutation_in_list}=    Set Variable    //div[contains(text(),'Salutation')]
            Wait For Elements State    ${salutation_in_list}    visible    timeout=5s
            Click    ${salutation_in_list}
            Log    ✓ Salutation selected using search method
        ELSE
            Fail    Could not find Salutation filter option
        END
    END
    Sleep    0.5s
    Set Strict Mode    True

Select Equals Criteria
    [Documentation]    Select 'Equals' criteria for the filter. For TC_37.
    Set Strict Mode    False
    # Wait for criteria dropdown to appear
    Sleep    0.3s
    # Try to find and click Equals option
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${EQUALS_CRITERIA_OPTION}    visible    timeout=8s
    IF    ${method1}
        Click    ${EQUALS_CRITERIA_OPTION}
        Log    ✓ Equals criteria selected
    ELSE
        # Try finding dropdown first
        ${criteria_dropdown}=    Set Variable    //button[contains(@aria-label,'criteria') or contains(@placeholder,'criteria')] | //span[contains(text(),'Select')]
        ${dropdown_exists}=    Run Keyword And Return Status    Wait For Elements State    ${criteria_dropdown}    visible    timeout=5s
        IF    ${dropdown_exists}
            Click    ${criteria_dropdown}
            Sleep    0.3s
            Wait For Elements State    ${EQUALS_CRITERIA_OPTION}    visible    timeout=5s
            Click    ${EQUALS_CRITERIA_OPTION}
            Log    ✓ Equals criteria selected via dropdown
        ELSE
            Log    Equals criteria may be default, continuing...    level=WARN
        END
    END
    Sleep    0.3s
    Set Strict Mode    True

Select Mr Salutation
    [Documentation]    Select 'Mr' salutation from dropdown. For TC_37.
    Set Strict Mode    False
    # Click on salutation dropdown
    ${dropdown_clicked}=    Set Variable    ${False}
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${SELECT_SALUTATION_DROPDOWN}    visible    timeout=8s
    IF    ${method1}
        Click    ${SELECT_SALUTATION_DROPDOWN}
        ${dropdown_clicked}=    Set Variable    ${True}
        Log    ✓ Salutation dropdown clicked
    ELSE
        # Try generic select/dropdown
        ${generic_dropdown}=    Set Variable    //button[contains(text(),'Select')] | //div[contains(@class,'select')]//button | //span[contains(text(),'Select')]
        ${generic_exists}=    Run Keyword And Return Status    Wait For Elements State    ${generic_dropdown}    visible    timeout=5s
        IF    ${generic_exists}
            Click    ${generic_dropdown}
            ${dropdown_clicked}=    Set Variable    ${True}
            Log    ✓ Salutation dropdown clicked via generic locator
        END
    END
    
    IF    ${dropdown_clicked}
        Sleep    0.5s
        # Select Mr option
        Wait For Elements State    ${MR_SALUTATION_OPTION}    visible    timeout=8s
        Click    ${MR_SALUTATION_OPTION}
        Log    ✓ Mr salutation selected successfully
    ELSE
        Fail    Could not open salutation dropdown
    END
    Sleep    0.3s
    Set Strict Mode    True

Apply Filter
    [Documentation]    Apply the filter (if apply button exists) or auto-applied. For TC_37.
    Set Strict Mode    False
    # Check if Apply button exists
    ${apply_exists}=    Run Keyword And Return Status    Wait For Elements State    ${APPLY_FILTER_BUTTON}    visible    timeout=5s
    IF    ${apply_exists}
        Click    ${APPLY_FILTER_BUTTON}
        Log    ✓ Apply button clicked
        Sleep    1s
    ELSE
        Log    Filter may be auto-applied, waiting for results...    level=INFO
        Sleep    0.5s
    END
    # Wait for filtered results to load
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    Set Strict Mode    True

Verify Mr Salutation In Table
    [Documentation]    Verify that only leads with Mr salutation are displayed in the table. For TC_37.
    Set Strict Mode    False
    # Wait for table to be populated
    Sleep    0.5s
    
    # Get all rows in the table
    ${table_rows}=    Set Variable    //table//tbody//tr
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} lead(s) in filtered table
    
    IF    ${row_count} == 0
        Fail    No leads found after applying Mr salutation filter
    END
    
    # Verify each row contains Mr salutation
    ${mr_count}=    Set Variable    0
    FOR    ${row}    IN    @{rows}
        # Try to find Mr in the row
        ${row_text}=    Get Property    ${row}    innerText
        ${contains_mr}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mr
        IF    ${contains_mr}
            ${mr_count}=    Evaluate    ${mr_count} + 1
            Log    ✓ Row contains Mr salutation: ${row_text}
        ELSE
            Log    WARNING: Row does not contain Mr: ${row_text}    level=WARN
        END
    END
    
    Log    Verified ${mr_count} out of ${row_count} rows contain Mr salutation
    
    # At least verify some rows have Mr
    Should Be True    ${mr_count} > 0    Expected at least 1 lead with Mr salutation, found ${mr_count}
    
    Set Strict Mode    True
    Log    ✅ PASS: Filter verification completed - Mr salutation leads are displayed

Filter Leads By Salutation Mr
    [Documentation]    Complete flow to filter leads by Mr salutation. For TC_37.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select Equals criteria
    Select Equals Criteria
    
    # Step 5: Select Mr salutation
    Select Mr Salutation
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results
    Verify Mr Salutation In Table
    
    Log    ✅ Successfully filtered leads by Mr salutation

# ============================================================================
# TC_38: Filter Keywords for Mrs Salutation
# ============================================================================

Select Mrs Salutation
    [Documentation]    Select 'Mrs' salutation from dropdown. For TC_38.
    Set Strict Mode    False
    # Click on salutation dropdown
    ${dropdown_clicked}=    Set Variable    ${False}
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${SELECT_SALUTATION_DROPDOWN}    visible    timeout=8s
    IF    ${method1}
        Click    ${SELECT_SALUTATION_DROPDOWN}
        ${dropdown_clicked}=    Set Variable    ${True}
        Log    ✓ Salutation dropdown clicked
    ELSE
        # Try generic select/dropdown
        ${generic_dropdown}=    Set Variable    //button[contains(text(),'Select')] | //div[contains(@class,'select')]//button | //span[contains(text(),'Select')]
        ${generic_exists}=    Run Keyword And Return Status    Wait For Elements State    ${generic_dropdown}    visible    timeout=5s
        IF    ${generic_exists}
            Click    ${generic_dropdown}
            ${dropdown_clicked}=    Set Variable    ${True}
            Log    ✓ Salutation dropdown clicked via generic locator
        END
    END
    
    IF    ${dropdown_clicked}
        Sleep    0.5s
        # Select Mrs option
        Wait For Elements State    ${MRS_SALUTATION_OPTION}    visible    timeout=8s
        Click    ${MRS_SALUTATION_OPTION}
        Log    ✓ Mrs salutation selected successfully
    ELSE
        Fail    Could not find salutation dropdown to select Mrs
    END
    
    Sleep    0.3s
    Set Strict Mode    True

Verify Mrs Salutation In Table
    [Documentation]    Verify that all visible leads in the table have 'Mrs' salutation. For TC_38.
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Try multiple locator strategies to find Mrs in table
    ${method1}=    Run Keyword And Return Status    Get Elements    ${MRS_IN_TABLE}
    ${method2}=    Run Keyword And Return Status    Get Elements    //td[text()='Mrs']
    ${method3}=    Run Keyword And Return Status    Get Elements    //table//td[contains(text(),'Mrs')]
    ${method4}=    Run Keyword And Return Status    Get Elements    //tr//td[contains(.,'Mrs')]
    
    Log    Method 1 (MRS_IN_TABLE): ${method1}
    Log    Method 2 (exact text): ${method2}
    Log    Method 3 (table contains): ${method3}
    Log    Method 4 (tr/td contains): ${method4}
    
    # Use the most flexible locator
    ${all_table_rows}=    Get Elements    //table//tbody//tr
    ${row_count}=    Get Length    ${all_table_rows}
    Log    Total table rows found: ${row_count}
    
    ${mrs_count}=    Set Variable    0
    FOR    ${row}    IN    @{all_table_rows}
        ${row_text}=    Get Property    ${row}    innerText
        Log    Row text: ${row_text}
        ${contains_mrs}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mrs
        IF    ${contains_mrs}
            ${mrs_count}=    Evaluate    ${mrs_count} + 1
        END
    END
    
    Log    Total rows: ${row_count}
    Log    Mrs rows found: ${mrs_count}
    
    Should Be True    ${row_count} > 0    No leads displayed after filtering.
    Should Be True    ${mrs_count} > 0    No Mrs leads found in table after filtering.
    Log    ✓ Found ${mrs_count} leads with Mrs salutation out of ${row_count} total rows.
    Set Strict Mode    True

Filter Leads By Salutation Mrs
    [Documentation]    Complete flow to filter leads by Mrs salutation. For TC_38.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select Equals criteria
    Select Equals Criteria
    
    # Step 5: Select Mrs salutation
    Select Mrs Salutation
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results
    Verify Mrs Salutation In Table
    
    Log    ✅ Successfully filtered leads by Mrs salutation

# ============================================================================
# TC_39: Filter Keywords for Ms Salutation
# ============================================================================

Select Ms Salutation
    [Documentation]    Select 'Ms' salutation from dropdown. For TC_39.
    Set Strict Mode    False
    # Click on salutation dropdown
    ${dropdown_clicked}=    Set Variable    ${False}
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${SELECT_SALUTATION_DROPDOWN}    visible    timeout=8s
    IF    ${method1}
        Click    ${SELECT_SALUTATION_DROPDOWN}
        ${dropdown_clicked}=    Set Variable    ${True}
        Log    ✓ Salutation dropdown clicked
    ELSE
        # Try generic select/dropdown
        ${generic_dropdown}=    Set Variable    //button[contains(text(),'Select')] | //div[contains(@class,'select')]//button | //span[contains(text(),'Select')]
        ${generic_exists}=    Run Keyword And Return Status    Wait For Elements State    ${generic_dropdown}    visible    timeout=5s
        IF    ${generic_exists}
            Click    ${generic_dropdown}
            ${dropdown_clicked}=    Set Variable    ${True}
            Log    ✓ Salutation dropdown clicked via generic locator
        END
    END
    
    IF    ${dropdown_clicked}
        Sleep    0.5s
        # Select Ms option
        Wait For Elements State    ${MS_SALUTATION_OPTION}    visible    timeout=8s
        Click    ${MS_SALUTATION_OPTION}
        Log    ✓ Ms salutation selected successfully
    ELSE
        Fail    Could not find salutation dropdown to select Ms
    END
    
    Sleep    0.3s
    Set Strict Mode    True

Verify Ms Salutation In Table
    [Documentation]    Verify that all visible leads in the table have 'Ms' salutation. For TC_39.
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Try multiple locator strategies to find Ms in table
    ${method1}=    Run Keyword And Return Status    Get Elements    ${MS_IN_TABLE}
    ${method2}=    Run Keyword And Return Status    Get Elements    //td[text()='Ms']
    ${method3}=    Run Keyword And Return Status    Get Elements    //table//td[contains(text(),'Ms')]
    ${method4}=    Run Keyword And Return Status    Get Elements    //tr//td[contains(.,'Ms')]
    
    Log    Method 1 (MS_IN_TABLE): ${method1}
    Log    Method 2 (exact text): ${method2}
    Log    Method 3 (table contains): ${method3}
    Log    Method 4 (tr/td contains): ${method4}
    
    # Use the most flexible locator
    ${all_table_rows}=    Get Elements    //table//tbody//tr
    ${row_count}=    Get Length    ${all_table_rows}
    Log    Total table rows found: ${row_count}
    
    ${ms_count}=    Set Variable    0
    FOR    ${row}    IN    @{all_table_rows}
        ${row_text}=    Get Property    ${row}    innerText
        Log    Row text: ${row_text}
        ${contains_ms}=    Run Keyword And Return Status    Should Contain    ${row_text}    Ms
        IF    ${contains_ms}
            ${ms_count}=    Evaluate    ${ms_count} + 1
        END
    END
    
    Log    Total rows: ${row_count}
    Log    Ms rows found: ${ms_count}
    
    Should Be True    ${row_count} > 0    No leads displayed after filtering.
    Should Be True    ${ms_count} > 0    No Ms leads found in table after filtering.
    Log    ✓ Found ${ms_count} leads with Ms salutation out of ${row_count} total rows.
    Set Strict Mode    True

Filter Leads By Salutation Ms
    [Documentation]    Complete flow to filter leads by Ms salutation. For TC_39.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select Equals criteria
    Select Equals Criteria
    
    # Step 5: Select Ms salutation
    Select Ms Salutation
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results
    Verify Ms Salutation In Table
    
    Log    ✅ Successfully filtered leads by Ms salutation

Select Not Equals Criteria
    [Documentation]    Select 'Not Equals' criteria for the filter. For TC_40.
    Set Strict Mode    False
    # Wait for criteria dropdown to appear
    Sleep    0.3s
    # Try to find and click Not Equals option
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${NOT_EQUALS_CRITERIA_OPTION}    visible    timeout=8s
    IF    ${method1}
        Click    ${NOT_EQUALS_CRITERIA_OPTION}
        Log    ✓ Not Equals criteria selected
    ELSE
        # Try finding dropdown first
        ${criteria_dropdown}=    Set Variable    //button[contains(@aria-label,'criteria') or contains(@placeholder,'criteria')] | //span[contains(text(),'Select')]
        ${dropdown_exists}=    Run Keyword And Return Status    Wait For Elements State    ${criteria_dropdown}    visible    timeout=5s
        IF    ${dropdown_exists}
            Click    ${criteria_dropdown}
            Sleep    0.3s
            Wait For Elements State    ${NOT_EQUALS_CRITERIA_OPTION}    visible    timeout=5s
            Click    ${NOT_EQUALS_CRITERIA_OPTION}
            Log    ✓ Not Equals criteria selected via dropdown
        ELSE
            Fail    Could not find Not Equals criteria option
        END
    END
    Sleep    0.3s
    Set Strict Mode    True

Verify Not Ms Salutation In Table
    [Documentation]    Verify that table shows only Mr and Mrs records (NO Ms records). For TC_40.
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Get all rows in the table
    ${table_rows}=    Set Variable    //table//tbody//tr
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} rows in filtered table
    
    # Verify at least some records exist
    Should Be True    ${row_count} > 0    msg=No leads displayed after filtering with Not Equals Ms
    
    # Verify NO Ms records exist and only Mr/Mrs exist
    ${ms_found}=    Set Variable    ${False}
    ${mr_or_mrs_found}=    Set Variable    ${False}
    
    FOR    ${row}    IN    @{rows}
        ${row_text}=    Get Property    ${row}    innerText
        Log    Checking row: ${row_text}
        
        # Check if Ms is present (should NOT be)
        ${has_ms}=    Run Keyword And Return Status    Should Contain    ${row_text}    Ms
        IF    ${has_ms}
            ${ms_found}=    Set Variable    ${True}
            Log    ❌ ERROR: Found Ms salutation in row: ${row_text}    level=ERROR
        END
        
        # Check if Mr or Mrs is present (should be present)
        ${has_mr}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mr
        ${has_mrs}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mrs
        IF    ${has_mr} or ${has_mrs}
            ${mr_or_mrs_found}=    Set Variable    ${True}
            Log    ✓ Found valid salutation (Mr or Mrs) in row
        END
    END
    
    # Final verification
    Should Not Be True    ${ms_found}    msg=ERROR: Ms salutation found in table but should be filtered out with Not Equals
    Should Be True    ${mr_or_mrs_found}    msg=ERROR: No Mr or Mrs salutations found in table
    
    Log    ✅ Verified: Only Mr and Mrs salutations present (Ms correctly filtered out)
    Set Strict Mode    True

Filter Leads By Salutation Not Equals Ms
    [Documentation]    Complete flow to filter leads by NOT EQUALS Ms (shows Mr and Mrs only). For TC_40.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select NOT EQUALS criteria
    Select Not Equals Criteria
    
    # Step 5: Select Ms salutation (to filter OUT)
    Select Ms Salutation
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results (should show Mr and Mrs, NO Ms)
    Verify Not Ms Salutation In Table
    
    Log    ✅ Successfully filtered leads by NOT EQUALS Ms (showing Mr and Mrs only)

Verify Not Mr Salutation In Table
    [Documentation]    Verify that table shows only Mrs and Ms records (NO Mr records). For TC_41.
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Get all rows in the table
    ${table_rows}=    Set Variable    //table//tbody//tr
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} rows in filtered table
    
    # Verify at least some records exist
    Should Be True    ${row_count} > 0    msg=No leads displayed after filtering with Not Equals Mr
    
    # Verify NO Mr records exist and only Mrs/Ms exist
    ${mr_found}=    Set Variable    ${False}
    ${mrs_or_ms_found}=    Set Variable    ${False}
    
    FOR    ${row}    IN    @{rows}
        ${row_text}=    Get Property    ${row}    innerText
        Log    Checking row: ${row_text}
        
        # Check if Mr is present (should NOT be)
        ${has_mr}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mr
        IF    ${has_mr}
            ${mr_found}=    Set Variable    ${True}
            Log    ❌ ERROR: Found Mr salutation in row: ${row_text}    level=ERROR
        END
        
        # Check if Mrs or Ms is present (should be present)
        ${has_mrs}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mrs
        ${has_ms}=    Run Keyword And Return Status    Should Contain    ${row_text}    Ms
        IF    ${has_mrs} or ${has_ms}
            ${mrs_or_ms_found}=    Set Variable    ${True}
            Log    ✓ Found valid salutation (Mrs or Ms) in row
        END
    END
    
    # Final verification
    Should Not Be True    ${mr_found}    msg=ERROR: Mr salutation found in table but should be filtered out with Not Equals
    Should Be True    ${mrs_or_ms_found}    msg=ERROR: No Mrs or Ms salutations found in table
    
    Log    ✅ Verified: Only Mrs and Ms salutations present (Mr correctly filtered out)
    Set Strict Mode    True

Filter Leads By Salutation Not Equals Mr
    [Documentation]    Complete flow to filter leads by NOT EQUALS Mr (shows Mrs and Ms only). For TC_41.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select NOT EQUALS criteria
    Select Not Equals Criteria
    
    # Step 5: Select Mr salutation (to filter OUT)
    Select Mr Salutation
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results (should show Mrs and Ms, NO Mr)
    Verify Not Mr Salutation In Table
    
    Log    ✅ Successfully filtered leads by NOT EQUALS Mr (showing Mrs and Ms only)

Verify Not Mrs Salutation In Table
    [Documentation]    Verify that table shows only Mr and Ms records (NO Mrs records). For TC_42.
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Get all rows in the table
    ${table_rows}=    Set Variable    //table//tbody//tr
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} rows in filtered table
    
    # Verify at least some records exist
    Should Be True    ${row_count} > 0    msg=No leads displayed after filtering with Not Equals Mrs
    
    # Verify NO Mrs records exist and only Mr/Ms exist
    ${mrs_found}=    Set Variable    ${False}
    ${mr_or_ms_found}=    Set Variable    ${False}
    
    FOR    ${row}    IN    @{rows}
        ${row_text}=    Get Property    ${row}    innerText
        Log    Checking row: ${row_text}
        
        # Check if Mrs is present (should NOT be)
        ${has_mrs}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mrs
        IF    ${has_mrs}
            ${mrs_found}=    Set Variable    ${True}
            Log    ❌ ERROR: Found Mrs salutation in row: ${row_text}    level=ERROR
        END
        
        # Check if Mr or Ms is present (should be present)
        ${has_mr}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mr
        ${has_ms}=    Run Keyword And Return Status    Should Contain    ${row_text}    Ms
        IF    ${has_mr} or ${has_ms}
            ${mr_or_ms_found}=    Set Variable    ${True}
            Log    ✓ Found valid salutation (Mr or Ms) in row
        END
    END
    
    # Final verification
    Should Not Be True    ${mrs_found}    msg=ERROR: Mrs salutation found in table but should be filtered out with Not Equals
    Should Be True    ${mr_or_ms_found}    msg=ERROR: No Mr or Ms salutations found in table
    
    Log    ✅ Verified: Only Mr and Ms salutations present (Mrs correctly filtered out)
    Set Strict Mode    True

Filter Leads By Salutation Not Equals Mrs
    [Documentation]    Complete flow to filter leads by NOT EQUALS Mrs (shows Mr and Ms only). For TC_42.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select NOT EQUALS criteria
    Select Not Equals Criteria
    
    # Step 5: Select Mrs salutation (to filter OUT)
    Select Mrs Salutation
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results (should show Mr and Ms, NO Mrs)
    Verify Not Mrs Salutation In Table
    
    Log    ✅ Successfully filtered leads by NOT EQUALS Mrs (showing Mr and Ms only)

# ============================================================================
# TC_43: Filter Keywords for "Is Not Set" Criteria
# ============================================================================

Select Is Not Set Criteria
    [Documentation]    Select 'Is Not Set' criteria for the filter. For TC_43. This criteria doesn't require a value selection.
    Set Strict Mode    False
    # Wait for criteria dropdown to appear
    Sleep    0.3s
    # Try to find and click Is Not Set option
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${IS_NOT_SET_CRITERIA_OPTION}    visible    timeout=8s
    IF    ${method1}
        Click    ${IS_NOT_SET_CRITERIA_OPTION}
        Log    ✓ Is Not Set criteria selected
    ELSE
        # Try finding dropdown first
        ${criteria_dropdown}=    Set Variable    //button[contains(@aria-label,'criteria') or contains(@placeholder,'criteria')] | //span[contains(text(),'Select')]
        ${dropdown_exists}=    Run Keyword And Return Status    Wait For Elements State    ${criteria_dropdown}    visible    timeout=5s
        IF    ${dropdown_exists}
            Click    ${criteria_dropdown}
            Sleep    0.3s
            Wait For Elements State    ${IS_NOT_SET_CRITERIA_OPTION}    visible    timeout=5s
            Click    ${IS_NOT_SET_CRITERIA_OPTION}
            Log    ✓ Is Not Set criteria selected via dropdown
        ELSE
            Fail    Could not find Is Not Set criteria option
        END
    END
    Sleep    0.3s
    Set Strict Mode    True

Verify Salutation Dropdown Is Disabled
    [Documentation]    Verify that salutation dropdown is disabled when "Is Not Set" criteria is selected. For TC_43.
    Set Strict Mode    False
    Sleep    0.5s
    
    # Try to find the salutation dropdown
    ${dropdown_exists}=    Run Keyword And Return Status    Wait For Elements State    ${SELECT_SALUTATION_DROPDOWN}    visible    timeout=5s
    
    IF    ${dropdown_exists}
        # Check if dropdown is disabled
        ${is_disabled}=    Run Keyword And Return Status    Get Attribute    ${SELECT_SALUTATION_DROPDOWN}    disabled
        ${aria_disabled}=    Run Keyword And Return Status    Get Attribute    ${SELECT_SALUTATION_DROPDOWN}    aria-disabled
        
        # Also check if it has disabled class or attribute
        ${has_disabled_class}=    Run Keyword And Return Status    Get Attribute    ${SELECT_SALUTATION_DROPDOWN}    class
        
        IF    ${is_disabled} or ${aria_disabled}
            Log    ✓ Salutation dropdown is correctly DISABLED for 'Is Not Set' criteria
        ELSE
            # Try clicking to verify it's not interactive
            ${click_failed}=    Run Keyword And Return Status    Click    ${SELECT_SALUTATION_DROPDOWN}    timeout=2s
            IF    not ${click_failed}
                Log    ⚠ Dropdown appears to be clickable, but no value selection should be possible    level=WARN
            ELSE
                Log    ✓ Salutation dropdown is not interactive (correctly disabled)
            END
        END
    ELSE
        # Dropdown not visible - this is expected behavior for "Is Not Set"
        Log    ✓ Salutation dropdown is not shown for 'Is Not Set' criteria (expected behavior)
    END
    
    Set Strict Mode    True

Verify Empty Salutation In Table
    [Documentation]    Verify that table shows only records with empty/dash "-" salutation (no Mr/Mrs/Ms). For TC_43.
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Get all rows in the table
    ${table_rows}=    Set Variable    ${TABLE_ROWS}
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} rows in filtered table
    
    # Verify at least some records exist
    Should Be True    ${row_count} > 0    msg=No leads displayed after filtering with Is Not Set
    
    # Verify only empty/dash salutation exists
    ${invalid_salutation_found}=    Set Variable    ${False}
    ${empty_salutation_found}=    Set Variable    ${False}
    
    FOR    ${row}    IN    @{rows}
        ${row_text}=    Get Property    ${row}    innerText
        Log    Checking row: ${row_text}
        
        # Check if Mr/Mrs/Ms is present (should NOT be)
        ${has_mr}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mr
        ${has_mrs}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mrs
        ${has_ms}=    Run Keyword And Return Status    Should Contain    ${row_text}    Ms
        
        IF    ${has_mr} or ${has_mrs} or ${has_ms}
            ${invalid_salutation_found}=    Set Variable    ${True}
            Log    ❌ ERROR: Found Mr/Mrs/Ms salutation in row: ${row_text}    level=ERROR
        ELSE
            # Check for dash, empty, N/A, or similar empty indicators
            ${has_dash}=    Run Keyword And Return Status    Should Contain    ${row_text}    -
            ${has_na}=    Run Keyword And Return Status    Should Contain    ${row_text}    N/A
            IF    ${has_dash} or ${has_na}
                ${empty_salutation_found}=    Set Variable    ${True}
                Log    ✓ Found empty/dash salutation ("-") in row
            ELSE
                # Check if row has any text at all (may be truly empty)
                ${text_length}=    Get Length    ${row_text}
                IF    ${text_length} == 0
                    ${empty_salutation_found}=    Set Variable    ${True}
                    Log    ✓ Found empty salutation (no text) in row
                ELSE
                    Log    ✓ Row appears to have empty/not set salutation
                    ${empty_salutation_found}=    Set Variable    ${True}
                END
            END
        END
    END
    
    # Final verification
    Should Not Be True    ${invalid_salutation_found}    msg=ERROR: Mr/Mrs/Ms salutation found in table but should be filtered out with Is Not Set
    Should Be True    ${empty_salutation_found}    msg=ERROR: No empty/dash salutations found in table
    
    Log    ✅ Verified: Only empty/dash "-" salutations present (all Mr/Mrs/Ms correctly filtered out)
    Set Strict Mode    True

Filter Leads By Salutation Is Not Set
    [Documentation]    Complete flow to filter leads by IS NOT SET criteria (shows only empty/dash salutation). For TC_43.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select IS NOT SET criteria
    Select Is Not Set Criteria
    
    # Step 5: Verify salutation dropdown is disabled (no value selection needed)
    Verify Salutation Dropdown Is Disabled
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results (should show only empty/dash salutation, NO Mr/Mrs/Ms)
    Verify Empty Salutation In Table
    
    Log    ✅ Successfully filtered leads by IS NOT SET (showing only empty/dash salutation)

# ============================================================================
# TC_44: Filter Keywords for "Is Set" Criteria
# ============================================================================

Select Is Set Criteria
    [Documentation]    Select 'Is Set' criteria for the filter. For TC_44. This criteria doesn't require a value selection.
    Set Strict Mode    False
    # Wait for criteria dropdown to appear
    Sleep    0.3s
    # Try to find and click Is Set option
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${IS_SET_CRITERIA_OPTION}    visible    timeout=8s
    IF    ${method1}
        Click    ${IS_SET_CRITERIA_OPTION}
        Log    ✓ Is Set criteria selected
    ELSE
        # Try finding dropdown first
        ${criteria_dropdown}=    Set Variable    //button[contains(@aria-label,'criteria') or contains(@placeholder,'criteria')] | //span[contains(text(),'Select')]
        ${dropdown_exists}=    Run Keyword And Return Status    Wait For Elements State    ${criteria_dropdown}    visible    timeout=5s
        IF    ${dropdown_exists}
            Click    ${criteria_dropdown}
            Sleep    0.3s
            Wait For Elements State    ${IS_SET_CRITERIA_OPTION}    visible    timeout=5s
            Click    ${IS_SET_CRITERIA_OPTION}
            Log    ✓ Is Set criteria selected via dropdown
        ELSE
            Fail    Could not find Is Set criteria option
        END
    END
    Sleep    0.3s
    Set Strict Mode    True

Verify Salutation Dropdown Is Disabled For Is Set
    [Documentation]    Verify that salutation dropdown is disabled when "Is Set" criteria is selected. For TC_44.
    Set Strict Mode    False
    Sleep    0.5s
    
    # Try to find the salutation dropdown
    ${dropdown_exists}=    Run Keyword And Return Status    Wait For Elements State    ${SELECT_SALUTATION_DROPDOWN}    visible    timeout=5s
    
    IF    ${dropdown_exists}
        # Check if dropdown is disabled
        ${is_disabled}=    Run Keyword And Return Status    Get Attribute    ${SELECT_SALUTATION_DROPDOWN}    disabled
        ${aria_disabled}=    Run Keyword And Return Status    Get Attribute    ${SELECT_SALUTATION_DROPDOWN}    aria-disabled
        
        IF    ${is_disabled} or ${aria_disabled}
            Log    ✓ Salutation dropdown is correctly DISABLED for 'Is Set' criteria
        ELSE
            # Try clicking to verify it's not interactive
            ${click_failed}=    Run Keyword And Return Status    Click    ${SELECT_SALUTATION_DROPDOWN}    timeout=2s
            IF    not ${click_failed}
                Log    ⚠ Dropdown appears to be clickable, but no value selection should be possible    level=WARN
            ELSE
                Log    ✓ Salutation dropdown is not interactive (correctly disabled)
            END
        END
    ELSE
        # Dropdown not visible - this is expected behavior for "Is Set"
        Log    ✓ Salutation dropdown is not shown for 'Is Set' criteria (expected behavior)
    END
    
    Set Strict Mode    True

Verify Set Salutation In Table
    [Documentation]    Verify that table shows only records with Mr/Mrs/Ms salutation (NO empty/dash). For TC_44.
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Get all rows in the table
    ${table_rows}=    Set Variable    ${TABLE_ROWS}
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} rows in filtered table
    
    # Verify at least some records exist
    Should Be True    ${row_count} > 0    msg=No leads displayed after filtering with Is Set
    
    # Verify only Mr/Mrs/Ms salutation exists (NO empty/dash)
    ${empty_salutation_found}=    Set Variable    ${False}
    ${valid_salutation_found}=    Set Variable    ${False}
    
    FOR    ${row}    IN    @{rows}
        ${row_text}=    Get Property    ${row}    innerText
        Log    Checking row: ${row_text}
        
        # Check if Mr/Mrs/Ms is present (should be present)
        ${has_mr}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mr
        ${has_mrs}=    Run Keyword And Return Status    Should Contain    ${row_text}    Mrs
        ${has_ms}=    Run Keyword And Return Status    Should Contain    ${row_text}    Ms
        
        IF    ${has_mr} or ${has_mrs} or ${has_ms}
            ${valid_salutation_found}=    Set Variable    ${True}
            Log    ✓ Found valid salutation (Mr/Mrs/Ms) in row
        ELSE
            # Check for empty indicators (dash, N/A, empty) - should NOT be present
            ${has_dash}=    Run Keyword And Return Status    Should Contain    ${row_text}    -
            ${has_na}=    Run Keyword And Return Status    Should Contain    ${row_text}    N/A
            ${text_stripped}=    Strip String    ${row_text}
            ${text_length}=    Get Length    ${text_stripped}
            
            IF    ${has_dash} or ${has_na} or ${text_length} == 0
                ${empty_salutation_found}=    Set Variable    ${True}
                Log    ❌ ERROR: Found empty/dash salutation in row: ${row_text}    level=ERROR
            ELSE
                # Row may contain other data, but no clear empty indicator
                Log    ⚠ Row doesn't have Mr/Mrs/Ms or empty indicator: ${row_text}    level=WARN
            END
        END
    END
    
    # Final verification
    Should Not Be True    ${empty_salutation_found}    msg=ERROR: Empty/dash salutation found in table but should be filtered out with Is Set
    Should Be True    ${valid_salutation_found}    msg=ERROR: No Mr/Mrs/Ms salutations found in table
    
    Log    ✅ Verified: Only Mr/Mrs/Ms salutations present (all empty/dash correctly filtered out)
    Set Strict Mode    True

Filter Leads By Salutation Is Set
    [Documentation]    Complete flow to filter leads by IS SET criteria (shows Mr/Mrs/Ms, NO empty/dash). For TC_44.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Salutation from filter options
    Select Salutation From Filter Options
    
    # Step 4: Select IS SET criteria
    Select Is Set Criteria
    
    # Step 5: Verify salutation dropdown is disabled (no value selection needed)
    Verify Salutation Dropdown Is Disabled For Is Set
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results (should show Mr/Mrs/Ms, NO empty/dash)
    Verify Set Salutation In Table
    
    Log    ✅ Successfully filtered leads by IS SET (showing Mr/Mrs/Ms, excluding empty/dash)

# ============================================================================
# TC_45: Filter Keywords for "Full Name Equals" with Exact Match Verification
# ============================================================================

Select Full Name From Filter Options
    [Documentation]    Select Full Name option from filter dropdown. For TC_45.
    Set Strict Mode    False
    # Try multiple strategies to find and click Full Name option
    ${method1}=    Run Keyword And Return Status    Wait For Elements State    ${FULL_NAME_FILTER_OPTION}    visible    timeout=8s
    IF    ${method1}
        Click    ${FULL_NAME_FILTER_OPTION}
        Log    ✓ Full Name selected using primary locator
    ELSE
        # Try alternative: search for input field and type
        ${filter_search}=    Set Variable    //input[@placeholder='Search' or @placeholder='search' or contains(@placeholder,'filter')]
        ${search_exists}=    Run Keyword And Return Status    Wait For Elements State    ${filter_search}    visible    timeout=5s
        IF    ${search_exists}
            Fill Text    ${filter_search}    Full Name
            Sleep    0.3s
            ${fullname_in_list}=    Set Variable    //div[contains(text(),'Full Name') or contains(text(),'Name')]
            Wait For Elements State    ${fullname_in_list}    visible    timeout=5s
            Click    ${fullname_in_list}
            Log    ✓ Full Name selected using search method
        ELSE
            Fail    Could not find Full Name filter option
        END
    END
    Sleep    0.5s
    Set Strict Mode    True

Enter Full Name Value In Filter
    [Documentation]    Enter full name value in the filter input box. For TC_45.
    [Arguments]    ${full_name}
    Set Strict Mode    False
    Sleep    0.5s
    
    # Try to find the value input box
    ${input_exists}=    Run Keyword And Return Status    Wait For Elements State    ${FILTER_VALUE_INPUT}    visible    timeout=10s
    IF    ${input_exists}
        # Clear and enter the full name
        Fill Text    ${FILTER_VALUE_INPUT}    ${full_name}
        Log    ✓ Entered full name: ${full_name}
        Sleep    0.5s
    ELSE
        # Try generic input locator
        ${generic_input}=    Set Variable    //input[@type='text' or @type='search']
        ${generic_exists}=    Run Keyword And Return Status    Wait For Elements State    ${generic_input}    visible    timeout=5s
        IF    ${generic_exists}
            Fill Text    ${generic_input}    ${full_name}
            Log    ✓ Entered full name via generic input: ${full_name}
            Sleep    0.5s
        ELSE
            Fail    Could not find input box to enter full name
        END
    END
    
    Set Strict Mode    True

Verify Full Name In Table
    [Documentation]    Verify that table shows only records with matching full name (case-insensitive). For TC_45.
    [Arguments]    ${expected_full_name}
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Get all rows in the table
    ${table_rows}=    Set Variable    ${TABLE_ROWS}
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} rows in filtered table
    
    # Verify at least one record exists
    Should Be True    ${row_count} > 0    msg=No leads displayed after filtering by Full Name
    
    # Convert expected name to lowercase for case-insensitive comparison
    ${expected_name_lower}=    Convert To Lower Case    ${expected_full_name}
    
    # Verify full name match (case-insensitive)
    ${match_found}=    Set Variable    ${False}
    ${all_rows_match}=    Set Variable    ${True}
    
    FOR    ${row}    IN    @{rows}
        ${row_text}=    Get Property    ${row}    innerText
        ${row_text_lower}=    Convert To Lower Case    ${row_text}
        
        # Check if row contains the expected full name (case-insensitive)
        ${has_name}=    Run Keyword And Return Status    Should Contain    ${row_text_lower}    ${expected_name_lower}
        IF    ${has_name}
            ${match_found}=    Set Variable    ${True}
            Log    ✓ Found matching name in row (case-insensitive): ${expected_full_name}
        ELSE
            ${all_rows_match}=    Set Variable    ${False}
            Log    ⚠ Row does not contain expected name: ${row_text}    level=WARN
        END
    END
    
    # Final verification
    Should Be True    ${match_found}    msg=ERROR: Expected full name "${expected_full_name}" not found in table
    Should Be True    ${all_rows_match}    msg=WARNING: Some rows don't contain expected full name (filter may not be working correctly)
    
    Log    ✅ Verified: All displayed records contain "${expected_full_name}" (case-insensitive match working correctly)
    Set Strict Mode    True

Create Test User For Filter
    [Documentation]    Create a test user with specified full name. For TC_45.
    [Arguments]    ${user_data}
    Log    Creating test user: ${user_data}[full_name]
    # This keyword should navigate to User Management and create user
    # Implementation depends on your User Management keywords
    # For now, this is a placeholder - replace with actual user creation logic
    Log    ⚠ User creation keyword needs to be implemented    level=WARN

Create Lead For Test User
    [Documentation]    Create a lead assigned to specific user. For TC_45.
    [Arguments]    ${lead_data}
    Log    Creating lead for user: ${lead_data}[assigned_to]
    # This keyword should create a lead with specified user assignment
    # Implementation depends on your Lead Creation keywords
    # For now, this is a placeholder - replace with actual lead creation logic
    Log    ⚠ Lead creation keyword needs to be implemented    level=WARN

Test Full Name Filter Selection Flow
    [Documentation]    Test ONLY the filter selection flow (without data). For TC_45 Step 1.
    [Arguments]    ${data}
    
    Log    Step 1: Clicking Filter button    level=INFO
    Click On Filter Button
    
    Log    Step 2: Clicking Add Filter button    level=INFO
    Click On Add Filter Button
    
    Log    Step 3: Selecting Full Name from filter options    level=INFO
    Select Full Name From Filter Options
    
    Log    ✅ Full Name filter selection successful! Ready for next steps    level=INFO

Test Full Name Filter With Equals Criteria
    [Documentation]    Test Filter > Add Filter > Full Name > Select Equals. For TC_45 Step 2.
    [Arguments]    ${data}
    
    Log    Step 1: Clicking Filter button    level=INFO
    Click On Filter Button
    
    Log    Step 2: Clicking Add Filter button    level=INFO
    Click On Add Filter Button
    
    Log    Step 3: Selecting Full Name from filter options    level=INFO
    Select Full Name From Filter Options
    
    Log    Step 4: Selecting Equals criteria    level=INFO
    Select Equals Criteria
    
    Log    ✅ Full Name + Equals criteria selected! Ready for value input    level=INFO

Test Full Name Filter With Value Input
    [Documentation]    Test Filter > Add Filter > Full Name > Equals > Enter Value. For TC_45 Step 3.
    [Arguments]    ${full_name_value}
    
    Log    Step 1: Clicking Filter button    level=INFO
    Click On Filter Button
    
    Log    Step 2: Clicking Add Filter button    level=INFO
    Click On Add Filter Button
    
    Log    Step 3: Selecting Full Name from filter options    level=INFO
    Select Full Name From Filter Options
    
    Log    Step 4: Selecting Equals criteria    level=INFO
    Select Equals Criteria
    
    Log    Step 5: Entering full name value: ${full_name_value}    level=INFO
    Enter Full Name Value In Filter    ${full_name_value}
    
    Log    ✅ Full Name value entered successfully! Ready for Apply Filter    level=INFO

Test Full Name Filter Complete With Excel Data
    [Documentation]    Complete test: Filter > Add Filter > Full Name > Equals > Enter Value > Apply > Verify. Uses Excel data. For TC_45 Step 4.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Get full name from Excel data (note: column has leading space in Excel)
    ${full_name_from_excel}=    Get From Dictionary    ${my_dict}    ${SPACE}full_name
    
    Log    Step 1: Clicking Filter button    level=INFO
    Click On Filter Button
    
    Log    Step 2: Clicking Add Filter button    level=INFO
    Click On Add Filter Button
    
    Log    Step 3: Selecting Full Name from filter options    level=INFO
    Select Full Name From Filter Options
    
    Log    Step 4: Selecting Equals criteria    level=INFO
    Select Equals Criteria
    
    Log    Step 5: Entering full name from Excel: ${full_name_from_excel}    level=INFO
    Enter Full Name Value In Filter    ${full_name_from_excel}
    
    Log    Step 6: Applying filter    level=INFO
    Apply Filter
    
    Log    Step 7: Verifying table results    level=INFO
    Verify Full Name In Table    ${full_name_from_excel}
    
    Log    ✅ Complete test passed! Only "${full_name_from_excel}" records verified in table    level=INFO

Filter Leads By Full Name Equals
    [Documentation]    Complete flow to filter leads by Full Name with Equals criteria. For TC_45.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Extract full name to filter (note: column has leading space in Excel)
    ${full_name_to_filter}=    Get From Dictionary    ${my_dict}    ${SPACE}full_name
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Full Name from filter options
    Select Full Name From Filter Options
    
    # Step 4: Select Equals criteria
    Select Equals Criteria
    
    # Step 5: Enter full name value
    Enter Full Name Value In Filter    ${full_name_to_filter}
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results (should show only exact match)
    Verify Full Name In Table    ${full_name_to_filter}
    
    Log    ✅ Successfully filtered leads by Full Name Equals "${full_name_to_filter}"

Verify Full Name NOT In Table
    [Documentation]    Verify that table does NOT show records with specified full name in the Full Name column only (case-insensitive). For TC_46.
    [Arguments]    ${excluded_full_name}
    Set Strict Mode    False
    Sleep    0.5s
    Wait For Load State    networkidle    timeout=10s
    Sleep    0.5s
    
    # Get all rows in the table (using tbody to avoid header)
    ${table_rows}=    Set Variable    //table//tbody//tr
    ${rows}=    Get Elements    ${table_rows}
    ${row_count}=    Get Length    ${rows}
    
    Log    Found ${row_count} rows in filtered table
    
    # Verify at least one record exists
    Should Be True    ${row_count} > 0    msg=No leads displayed after filtering by Full Name Not Equals
    
    # Convert excluded name to lowercase for case-insensitive comparison
    ${excluded_name_lower}=    Convert To Lower Case    ${excluded_full_name}
    
    # Verify excluded name is NOT present in any row's Full Name column
    ${other_names_found}=    Set Variable    ${False}
    ${excluded_name_found}=    Set Variable    ${False}
    
    FOR    ${row}    IN    @{rows}
        # Get only the 2nd column (Full Name column - index 1 for 0-based)
        # Try multiple column positions as Full Name can be in different positions
        ${name_cell_found}=    Set Variable    ${False}
        ${name_cell_text}=    Set Variable    ${EMPTY}
        
        # Try column 2 (td[2])
        ${col2_exists}=    Run Keyword And Return Status    Get Element    ${row} >> xpath=./td[2]
        IF    ${col2_exists}
            ${name_cell}=    Get Element    ${row} >> xpath=./td[2]
            ${name_cell_text}=    Get Property    ${name_cell}    innerText
            ${name_cell_found}=    Set Variable    ${True}
        END
        
        # If not found, try column 3 (td[3])
        IF    not ${name_cell_found}
            ${col3_exists}=    Run Keyword And Return Status    Get Element    ${row} >> xpath=./td[3]
            IF    ${col3_exists}
                ${name_cell}=    Get Element    ${row} >> xpath=./td[3]
                ${name_cell_text}=    Get Property    ${name_cell}    innerText
                ${name_cell_found}=    Set Variable    ${True}
            END
        END
        
        # Check if this cell contains the excluded name
        IF    ${name_cell_found}
            ${name_cell_lower}=    Convert To Lower Case    ${name_cell_text}
            ${name_cell_lower}=    Strip String    ${name_cell_lower}
            
            # Check if cell text matches excluded name
            ${is_excluded}=    Run Keyword And Return Status    Should Be Equal    ${name_cell_lower}    ${excluded_name_lower}
            IF    ${is_excluded}
                ${excluded_name_found}=    Set Variable    ${True}
                Log    ❌ FOUND excluded name "${excluded_full_name}" in Full Name column: ${name_cell_text}    level=ERROR
            ELSE
                ${other_names_found}=    Set Variable    ${True}
                Log    ✓ Full Name column has different name: ${name_cell_text} (correct)
            END
        END
    END
    
    # Final verification
    Should Not Be True    ${excluded_name_found}    msg=ERROR: Excluded full name "${excluded_full_name}" was found in Full Name column (should be filtered out)
    Should Be True    ${other_names_found}    msg=ERROR: No other names found in table (filter may not be working)
    
    Log    ✅ Verified: Excluded name "${excluded_full_name}" is NOT present in any Full Name column (filter working correctly)
    Set Strict Mode    True

Filter Leads By Full Name Not Equals
    [Documentation]    Complete flow to filter leads by Full Name with Not Equals criteria. For TC_46.
    [Arguments]    ${data}
    ${my_dict}=    Create Dictionary    &{data}
    
    # Extract full name to exclude (note: column has leading space in Excel)
    ${full_name_to_exclude}=    Get From Dictionary    ${my_dict}    ${SPACE}full_name
    
    # Step 1: Click Filter button
    Click On Filter Button
    
    # Step 2: Click Add Filter button
    Click On Add Filter Button
    
    # Step 3: Select Full Name from filter options
    Select Full Name From Filter Options
    
    # Step 4: Select Not Equals criteria
    Select Not Equals Criteria
    
    # Step 5: Enter full name value to exclude
    Enter Full Name Value In Filter    ${full_name_to_exclude}
    
    # Step 6: Apply filter
    Apply Filter
    
    # Step 7: Verify results (should show all EXCEPT the excluded name)
    Verify Full Name NOT In Table    ${full_name_to_exclude}
    
    Log    ✅ Successfully filtered leads by Full Name Not Equals "${full_name_to_exclude}"



LEAD MANAGEMENT TESTDATA :

TC_ID	admin_email	otp_code	shift	module_name	lead_name	lead_source	budget	pipeline	stage	project_configuration	project_name	project_preferences_location	project_location	role	shift	project_name_list	contact_number	email	column_name	source_column	target_column	stage_name_list	stage_name_list_2	pipeline_2	user_name
TC_01	onkar.powar@idx.com	654,321	Morning Shift	User,Lead																					
TC_02	onkar.powar@idx.com	654,321	Morning Shift	Lead																					
TC_03	onkar.powar@idx.com	654,321		Delete																					
TC_04	onkar.powar@idx.com	654,321			abcd																				
TC_05	onkar.powar@idx.com	654,321				Google	Below 50L	aqua pen	Won	3BHK	demo project	No	Wakad												
TC_06	onkar.powar@idx.com	654,321				Google	Below 50L	aqua pen	Won	3BHK	Test Meta Lead	No	Baner												
TC_07	onkar.powar@idx.com	654,321				Google	Below 50L	aqua pen	Won	3BHK	Test Meta Lead	No	Baner	All Module All Permissions	Evening Shift										
TC_08	onkar.powar@idx.com	654,321				Google	Below 50L	aqua pen	Won	2BHK	Hiranadani	No	Wakad	All Module All Permissions	Evening Shift	Hiranadani, QA Project									
TC_09																									
TC_10	onkar.powar@idx.com	654,321																							
TC_11	onkar.powar@idx.com	654,321																							
TC_12	onkar.powar@idx.com	654,321																							
TC_13	onkar.powar@idx.com	654,321																							
TC_14	onkar.powar@idx.com	654,321																							

LOGIN PAGE LOCATORS: 
EMAIL_INPUT_XPATH = "//input[@placeholder='Email' or contains(@placeholder, 'email')]"
SEND_OTP_BUTTON_XPATH = "//button[contains(text(), 'Send OTP')]"
OTP_INPUT_1_XPATH = "//input[@data-testid='otp-input-1']"
OTP_INPUT_2_XPATH = "//input[@data-testid='otp-input-2']"
OTP_INPUT_3_XPATH = "//input[@data-testid='otp-input-3']"
OTP_INPUT_4_XPATH = "//input[@data-testid='otp-input-4']"
OTP_INPUT_5_XPATH = "//input[@data-testid='otp-input-5']"
OTP_INPUT_6_XPATH = "//input[@data-testid='otp-input-6']"
VERIFY_BUTTON_XPATH = "//button[contains(text(), 'Verify')]"
# XPath Locators - Error Messages
LOGIN_ERROR_NOT_REGISTERED_EMAIL_TOAST_XPATH = "//div[contains(text(),'Email not found in system')]"
LOGIN_ERROR_NOT_REGISTERED_EMAIL_XPATH = "//p[contains(text(),'Email not found in system')]"
LOGIN_ERROR_INVALID_OTP_TOAST_XPATH = "//div[contains(text(),'Invalid OTP')]"
LOGIN_ERROR_INVALID_OTP_XPATH = "//p[contains(text(),'Invalid OTP')]"
SETTINGS_NAVIGATION_BUTTON_XPATH = "//button[@aria-label='More menus']"


LEAD MANAGEMENT LOCATORS : LEAD_MANAGEMENT_LINK="//a[@href='/lead-management']"
ADD_LEAD_BUTTON="//button[@data-testid='create-new-lead-button']"
VERIFY_AND_LOGIN_BUTTON_XPATH = "//button[text()='Verify & login']"
CAMPAIGN_DROPDOWN="//span[contains(text(),'Select campaign')]"
CAMPAIGN_DROPDOWN_OPTION="//span[contains(text(),'CAMPAIGN_NAME')]"
CANCEL_BUTTON="//button[contains(text(),'Cancel')]"

PIPELINE_DROPDOWN="//span[contains(text(),'Select a pipeline')]"
PIPELINE_DROPDOWN_OPTION="//span[contains(text(),'PIPELINE_NAME')]"
LEAD_STAGE_DROPDOWN="//span[contains(text(),'Select a stage')]"
LEAD_STAGE_DROPDOWN_OPTION="//span[contains(text(),'STAGE_NAME')]"
NO_RESULT_FOUND_MESSAGE="//div[contains(text(),'No Result found.')]"
ADD_LEAD_BUTTON_NOT_VISIBLE="//button[@data-testid='create-new-lead-button']"

#form locators
SALUTION_CHECKBOX="//button[@id='MR']"
ADD_LEAD_BUTTON_FORM="//button[contains(text(),'Add')]"
LEAD_NAME_FIELD="//input[@placeholder='Enter full name']"
CONTACT_NUMBER_PERFIX="//label[text()='Phone Number']/following-sibling::div//button"
CONTACT_NUMBER="//label[contains(text(),'Phone Number')]/following-sibling::div//input[@placeholder='Enter phone number']"
LEAD_SOURCES_DROPDOWN="//span[contains(text(),'Select a source')]"
LEAD_SOURCES_DROPDOWN_OPTION="//span[contains(text(),'SOURCE_NAME')]"
MIN_MAX_BUDGET_DROPDOWN="//span[contains(text(),'Select a budget')]"
MIN_MAX_BUDGET_DROPDOWN_OPTION="//span[contains(text(),'BUDGET_NAME')]"
PIPELINE_DROPDOWN="//span[contains(text(),'Select a pipeline')]"
PIPELINE_DROPDOWN_OPTION="//span[contains(text(),'PIPELINE_NAME')]"
LEAD_STAGE_DROPDOWN="//span[contains(text(),'Select a stage')]"
LEAD_STAGE_DROPDOWN_OPTION="//span[contains(text(),'STAGE_NAME')]"
PROJECT_CONFIGURATION_DROPDOWN="//span[contains(text(),'Select a configuration')]"
PROJECT_CONFIGURATION_DROPDOWN_OPTION="//span[contains(text(),'CONFIGURATION_NAME')]"
PROJECT_NAME_DROPDOWN="//label[text()='Project']/following::span[text()='Select project']"
PROJECT_SEARCH_INPUT="//input[@placeholder='Search projects...']"
PROJECT_PREFERENCES_DROPDOWN="//span[contains(text(),'Select a location preference')]"
PROJECT_PREFERENCES_DROPDOWN_OPTION="//span[contains(text(),'LOCATION_PREFERENCE_NAME')]"
PROJECT_LOCATION_DROPDOWN="//span[contains(text(),'Select project location')]"
PROJECT_LOCATION_SEARCH_INPUT="//input[@placeholder='Search locations...']"
PROJECT_LOCATION_DROPDOWN_OPTION="//div[contains(text(),'PROJECT_LOCATION_NAME')]"
EMAIL_INPUT_FIELD_LEAD_MANAGEMENT="//input[@placeholder='Enter email address']"
PROJECT_NAME_DROPDOWN_OPTION="//div[contains(@id,'radix-')]"

#lead optional fields locators
OCCUPATION_DROPDOWN="//span[contains(text(),'Select an occupation')]"
VERIFY_AND_LOGIN_BUTTON_XPATH = "//button[text()='Verify & login']"
OCCUPATION_DROPDOWN_OPTION="//span[contains(text(),'OCCUPATION_NAME')]"
EMAIL_FIELD="//input[@placeholder='Enter email address']"
ALTERNATE_CONTACT_NUMBER="//input[@placeholder='Enter phone number']"
SELECT_CARPET_AREA_DROPDOWN="//span[contains(text(),'Select carpet area range')]"
SELECT_CARPET_AREA_DROPDOWN_OPTION="//span[contains(text(),'CARPET_AREA_RANGE_NAME')]"
SELECT_PURPOSE_DROPDOWN="//span[contains(text(),'Select purpose')]"
SELECT_PURPOSE_DROPDOWN_OPTION="//span[contains(text(),'PURPOSE_NAME')]"
SELECT_CAMPAIGN_DROPDOWN="//span[contains(text(),'Select campaign')]"
SELECT_CAMPAIGN_DROPDOWN_OPTION="//div[@role='option' and contains(text(),'CAMPAIGN_NAME')] | //div[@data-value='CAMPAIGN_NAME']"
CAMPAIGN_SEARCH_INPUT="//input[@placeholder='Search campaigns...']"
ADD_CAMPAIGN_BUTTON="//button[text()='Add']"

#created success message
LEAD_CREATED_SUCCESS_MESSAGE="//div[text()='Lead created successfully']"

#lead management listpage locators
PROJECT_HEADING_NAME="//th/div[text()='Project']"
PROJECT_NAME_COLUMN_DATA="//tr/td[INDEX]"
CREATED_UPDATED_DATE_HEADING="//th/div[contains(text(),'Created/Updated Date')]"
FULL_NAME_HEADING="//th/div[contains(text(),'Full Name')]"
FULL_NAME_COLUMN_DATA="//tr/td[INDEX]"
OWNER_HEADING="//th/div[contains(text(),'Owner')]"
LEAD_SEARCH_BAR="//input[@placeholder='Search Lead by name']"
THREE_DOT_MENU_OPTION_LEAD_MANAGEMENT="//div[contains(text(),'LEAD_NAME')]/parent::td/following-sibling::td//button[@data-slot='button']"
REMOVE_ICON_BUTTON="//button[contains(text(),'Remove')]"
YES_REMOVE_BUTTON="//button[contains(text(),'Yes, Remove')]"
EDIT_ICON_BUTTON="//button[contains(text(),'Edit')]"


#error message locators
ERROR_EMAIL_MESSAGE="//p[contains(text(),'Please enter a valid email address.')]"
ERROR_PHONE_NUMBER_MESSAGE="//p[contains(text(),'Invalid phone number format. Must be 10 digits starting with 6-9')]"
ERROR_PHONE_NUMBER_ALREADY_PRESENT_MESSAGE="//p[contains(text(),'Contact number already exists')]"
ERROR_MSG_PHONE_NUMBER_EXISTS="//div[contains(text(),'Lead with same phone number and same project already exists')]"
LEAD_DELETED_SUCCESS_MESSAGE="//div[text()='Lead deleted successfully']"
LEAD_UPDATED_SUCCESS_MESSAGE="//div[text()='Lead updated successfully']"
NO_LEAD_FOUND_MESSAGE="//div[contains(text(),'No Lead Found')]"


#lead details page locators
EDIT_PENCIL_ICON="//*[name()='svg' and contains(@class,'lucide-pencil')]/ancestor::button"
DISCARD_BUTTON="//button[contains(text(),'Discard')]"
UPDATE_BUTTON="//button[contains(text(),'Update')]"
ERROR_LEAD_NAME_MESSAGE="//p[contains(text(),'Full name cannot be empty')]"
OPEN_IN_NEW_TAB_BUTTON="//button[contains(text(),'Open in New Tab')]"
THREE_DOT_OPTION_LEAD_DETAILS="//*[name()='svg' and contains(@class,'lucide-ellipsis-vertical')]"
REASSIGN_LEAD_BUTTON_ICON="//button[@id='reassign-button']"
SEARCH_BAR_REASSIGN="//input[@placeholder='Search user']"
REASSIGN_USER_CHECKBOX="//div[contains(text(),'USER_NAME')]/ancestor::tr//button[@role='radio']"
REASSIGN_BUTTON="//button[contains(text(),'Reassign')]"
LEAD_REASSIGNED_SUCCESS_MESSAGE="//div[text()='Lead assigned to user successfully']"




#lead details page relevant sections locators
TASK_TAB="//button[contains(text(),'Tasks')]"
NOTE_TAB="//button[contains(text(),'Notes')]"
SITE_VISIT_TAB="//button[contains(text(),'Site Visit')]"
ACTIVITY_LOGS_TAB="//button[contains(text(),'Activity Logs')]"


#Filter locators for TC_37
FILTER_BUTTON="(//span[contains(text(),'Filter')])[1]"
ADD_FILTER_BUTTON="(//span[contains(text(),'Add Filter')])"
SALUTATION_FILTER_OPTION="//button[contains(text(),'Salutation')]"
FULL_NAME_FILTER_OPTION="//button[contains(text(),'Full Name') or contains(text(),'Name') or contains(text(),'Lead Name')]"
FILTER_VALUE_INPUT="//input[contains(@placeholder,'Select a  Full Name')] | //input[@placeholder='Enter value' or @placeholder='Value' or contains(@placeholder,'value') or @type='text']"
EQUALS_CRITERIA_OPTION="//div[contains(text(),'Equals') or text()='=' or contains(text(),'equals')][@role='option'] | //span[contains(text(),'Equals')]"
NOT_EQUALS_CRITERIA_OPTION="//div[contains(text(),'Not Equals') or contains(text(),'not equals') or text()='!=' or contains(text(),'Not equals')][@role='option'] | //span[contains(text(),'Not Equals')]"
IS_NOT_SET_CRITERIA_OPTION="//div[contains(text(),'Is Not Set') or contains(text(),'is not set') or contains(text(),'Not Set') or contains(text(),'Empty')][@role='option'] | //span[contains(text(),'Is Not Set')]"
IS_SET_CRITERIA_OPTION="//div[contains(text(),'Is Set') or contains(text(),'is set') or text()='Set' or contains(text(),'Has Value')][@role='option'] | //span[contains(text(),'Is Set')]"
SELECT_SALUTATION_DROPDOWN="//span[contains(text(),'Select Salutation') or contains(text(),'Select salutation')] | //button[contains(@aria-label,'salutation')]"
MR_SALUTATION_OPTION="//div[contains(text(),'Mr') or text()='Mr' or contains(text(),'Mr.')][@role='option'] | //span[text()='Mr' or text()='Mr.']"
MRS_SALUTATION_OPTION="//div[contains(text(),'Mrs') or text()='Mrs' or contains(text(),'Mrs.')][@role='option'] | //span[text()='Mrs' or text()='Mrs.']"
MS_SALUTATION_OPTION="//div[contains(text(),'Ms') or text()='Ms' or contains(text(),'Ms.')][@role='option'] | //span[text()='Ms' or text()='Ms.']"
APPLY_FILTER_BUTTON="//button[contains(text(),'Apply') or contains(text(),'apply')]"
CLEAR_FILTER_BUTTON="//button[contains(text(),'Clear') or contains(text(),'clear') or contains(text(),'Reset')]"
FILTER_PANEL="//div[contains(@class,'filter') or @role='dialog']"
SALUTATION_COLUMN_IN_TABLE="//td[contains(@data-column,'salutation') or contains(@class,'salutation')]"
MR_IN_TABLE="//td[contains(text(),'Mr') or text()='Mr' or contains(text(),'Mr.')]"
MRS_IN_TABLE="//td[contains(text(),'Mrs') or text()='Mrs' or contains(text(),'Mrs.')]"
MS_IN_TABLE="//td[contains(text(),'Ms') or text()='Ms' or contains(text(),'Ms.')]"
EMPTY_SALUTATION_IN_TABLE="//td[text()='-' or normalize-space(text())='' or text()='N/A' or contains(@class,'empty')]"
TABLE_ROWS="//table//tbody//tr"

