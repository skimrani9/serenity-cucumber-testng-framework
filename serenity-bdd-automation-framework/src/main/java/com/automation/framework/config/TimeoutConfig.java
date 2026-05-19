package com.automation.framework.config;

/**
 * Centralized implicit wait / fluent timeout hints for framework helpers.
 * Serenity page objects typically use their own {@code withTimeoutOf}.
 */
public final class TimeoutConfig {

    public static final int ELEMENT_VISIBILITY_SECONDS = 15;
    public static final int NAVIGATION_SECONDS = 25;

    private TimeoutConfig() {
    }
}
