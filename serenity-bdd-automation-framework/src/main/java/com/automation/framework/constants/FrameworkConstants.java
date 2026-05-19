package com.automation.framework.constants;

/**
 * Static defaults for URLs and identifiers used outside Serenity.conf.
 */
public final class FrameworkConstants {

    /** Matches {@code serenity.conf} default environment key. */
    public static final String DEFAULT_ENVIRONMENT_KEY = "qa";

    /** Auth path for OTP login flows. */
    public static final String JUSTO_AUTH_LOGIN_PATH = "/auth/login";

    private FrameworkConstants() {
    }
}
