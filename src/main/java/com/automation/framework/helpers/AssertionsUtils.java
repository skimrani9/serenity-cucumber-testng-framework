package com.automation.framework.helpers;

/**
 * Lightweight assertion helpers without pulling JUnit onto the compile classpath.
 */
public final class AssertionsUtils {

    private AssertionsUtils() {
    }

    public static void assertContains(String haystack, String needle, String message) {
        if (haystack == null || !haystack.contains(needle)) {
            throw new AssertionError(message != null ? message
                    : ("Expected text to contain \"" + needle + "\" but was: " + haystack));
        }
    }
}
