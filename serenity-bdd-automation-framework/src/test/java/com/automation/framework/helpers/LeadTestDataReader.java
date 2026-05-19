package com.automation.framework.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Loads lead scenarios from {@code classpath:testdata/lead_management.json}.
 */
public final class LeadTestDataReader {

    private LeadTestDataReader() {
    }

    public record LeadScenario(
            String id,
            String adminEmail,
            String adminPassword,
            String otpDigits,
            String leadSource,
            String budget,
            String pipeline,
            String stage,
            String projectConfiguration,
            String projectName,
            String projectPreferencesLocation,
            String projectLocation,
            String contactNumber,
            List<String> projectNameList,
            String moduleName,
            String role,
            String shift
    ) {
        /** Non-blank {@code admin_password} uses QA Email &amp; Password tab; otherwise OTP via {@link #otpDigits()}. */
        public boolean usesPasswordLogin() {
            return adminPassword != null && !adminPassword.isBlank();
        }

        public boolean hasLeadFormDetails() {
            return leadSource != null && !leadSource.isBlank();
        }

        /** Sanitized OTP has six digits → can fall back when Email &amp; Password UI is unavailable. */
        public boolean hasValidOtpFallback() {
            return otpDigits != null && otpDigits.length() >= 6;
        }
    }

    public static LeadScenario readScenario(String testCaseId) {
        try (InputStream in = LeadTestDataReader.class.getResourceAsStream("/testdata/lead_management.json")) {
            Objects.requireNonNull(in, "Missing classpath resource testdata/lead_management.json");
            JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .getAsJsonObject();
            if (!root.has(testCaseId)) {
                throw new IllegalArgumentException("Unknown lead test id in lead_management.json: " + testCaseId);
            }
            JsonObject row = root.getAsJsonObject(testCaseId);
            return new LeadScenario(
                    testCaseId,
                    optString(row, "admin_email"),
                    optString(row, "admin_password"),
                    sanitizeOtp(optString(row, "otp_code")),
                    optString(row, "lead_source"),
                    optString(row, "budget"),
                    optString(row, "pipeline"),
                    optString(row, "stage"),
                    optString(row, "project_configuration"),
                    optString(row, "project_name"),
                    optString(row, "project_preferences_location"),
                    optString(row, "project_location"),
                    optString(row, "contact_number"),
                    readStringList(row, "project_name_list"),
                    optString(row, "module_name"),
                    optString(row, "role"),
                    optString(row, "shift")
            );
        } catch (IOException e) {
            throw new IllegalStateException("Could not read testdata/lead_management.json", e);
        }
    }

    private static List<String> readStringList(JsonObject row, String key) {
        if (!row.has(key) || row.get(key).isJsonNull()) {
            return Collections.emptyList();
        }
        JsonElement el = row.get(key);
        List<String> out = new ArrayList<>();
        if (el.isJsonArray()) {
            JsonArray arr = el.getAsJsonArray();
            for (JsonElement item : arr) {
                out.add(item.getAsString());
            }
            return out;
        }
        String raw = el.getAsString();
        if (raw == null || raw.isBlank()) {
            return Collections.emptyList();
        }
        for (String part : raw.split(",")) {
            String p = part.trim();
            if (!p.isEmpty()) {
                out.add(p);
            }
        }
        return out;
    }

    private static String optString(JsonObject row, String key) {
        if (!row.has(key) || row.get(key).isJsonNull()) {
            return null;
        }
        String v = row.get(key).getAsString();
        return v == null || v.isBlank() ? null : v.trim();
    }

    public static String sanitizeOtp(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.replaceAll("\\D", "");
    }
}
