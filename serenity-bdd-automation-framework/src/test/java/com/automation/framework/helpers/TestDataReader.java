package com.automation.framework.helpers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Loads credential rows from {@code classpath:testdata/users.json}.
 */
public final class TestDataReader {

    private TestDataReader() {
    }

    /**
     * @param username Email / username field on login screen (JSON key {@code username} or {@code email}).
     * @param password Password when classic login is present (may be blank for OTP-only QA).
     * @param otpCode    Six-digit OTP after sanitizing non-digits (optional).
     */
    public record LoginCredentials(String username, String password, String otpCode) {

        public boolean usesOtp() {
            String digits = LeadTestDataReader.sanitizeOtp(otpCode);
            return digits != null && digits.length() >= 6;
        }

        /** Non-blank password → QA Email & Password tab flow (vs OTP tab). */
        public boolean usesPassword() {
            return password != null && !password.trim().isEmpty();
        }

        public String otpDigitsSix() {
            String digits = LeadTestDataReader.sanitizeOtp(otpCode);
            return digits.substring(0, Math.min(6, digits.length()));
        }
    }

    /** @deprecated Prefer {@link #readLoginCredentials(String)} for OTP-aware flows. */
    @Deprecated
    public record CredentialPair(String username, String password) {
    }

    public static LoginCredentials readLoginCredentials(String testDataKey) {
        try (InputStream in = TestDataReader.class.getResourceAsStream("/testdata/users.json")) {
            Objects.requireNonNull(in, "Missing classpath resource testdata/users.json");
            JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .getAsJsonObject();
            if (!root.has(testDataKey)) {
                throw new IllegalArgumentException("Unknown test data key in users.json: " + testDataKey);
            }
            JsonObject row = root.getAsJsonObject(testDataKey);
            String email = optStringPrefer(row, "email", "username");
            String password = optString(row, "password");
            String otp = optString(row, "otp_code");
            return new LoginCredentials(email, password == null ? "" : password, otp == null ? "" : otp);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read testdata/users.json", e);
        }
    }

    public static CredentialPair readCredentials(String testDataKey) {
        LoginCredentials lc = readLoginCredentials(testDataKey);
        return new CredentialPair(lc.username(), lc.password());
    }

    private static String optString(JsonObject row, String key) {
        if (!row.has(key) || row.get(key).isJsonNull()) {
            return "";
        }
        return row.get(key).getAsString();
    }

    private static String optStringPrefer(JsonObject row, String primaryKey, String fallbackKey) {
        String primary = optString(row, primaryKey);
        if (primary != null && !primary.isBlank()) {
            return primary.trim();
        }
        String fallback = optString(row, fallbackKey);
        return fallback == null ? "" : fallback.trim();
    }
}
