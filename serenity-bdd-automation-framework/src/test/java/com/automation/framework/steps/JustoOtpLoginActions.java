package com.automation.framework.steps;

import net.serenitybdd.annotations.Step;
import org.openqa.selenium.TimeoutException;
import com.automation.framework.helpers.LeadTestDataReader;
import com.automation.framework.pageObjects.JustoOtpLoginPage;
import com.automation.framework.pageObjects.LoginPage;

public class JustoOtpLoginActions {

    LoginPage loginPage;
    JustoOtpLoginPage justoOtpLoginPage;

    @Step("Open Justo login and authenticate from lead scenario {0}")
    public void loginWithLeadScenario(String testCaseId) {
        LeadTestDataReader.LeadScenario data = LeadTestDataReader.readScenario(testCaseId);
        justoOtpLoginPage.open();
        loginPage.dismissLandingGateIfPresent();
        if (data.usesPasswordLogin()) {
            try {
                loginPage.switchToEmailPasswordTabAndWaitForFields();
            } catch (TimeoutException passwordUiTimeout) {
                if (!data.hasValidOtpFallback()) {
                    throw passwordUiTimeout;
                }
                completeOtpLoginAfterLanding(data);
                return;
            }
            loginPage.enterJustoEmailAndPassword(data.adminEmail(), data.adminPassword());
            loginPage.clickJustoLoginButton();
            justoOtpLoginPage.waitUntilLoggedInShellVisible();
            return;
        }
        completeOtpLoginAfterLanding(data);
    }

    private void completeOtpLoginAfterLanding(LeadTestDataReader.LeadScenario data) {
        String otp = trimOtp(data.otpDigits());
        justoOtpLoginPage.enterEmail(data.adminEmail());
        justoOtpLoginPage.clickSendOtp();
        justoOtpLoginPage.enterOtpDigits(otp);
        justoOtpLoginPage.clickVerifyLogin();
        justoOtpLoginPage.waitUntilLoggedInShellVisible();
    }

    private static String trimOtp(String digits) {
        if (digits == null || digits.length() < 6) {
            throw new IllegalArgumentException("OTP must resolve to at least 6 digits for scenario");
        }
        return digits.substring(0, 6);
    }
}
