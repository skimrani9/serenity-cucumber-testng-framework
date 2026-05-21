package com.automation.framework.helpers;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomLeadHelper {

    private RandomLeadHelper() {
    }

    public static String alphabetic(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        ThreadLocalRandom r = ThreadLocalRandom.current();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String indianMobile() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        char first = "6789".charAt(r.nextInt(4));
        StringBuilder sb = new StringBuilder();
        sb.append(first);
        for (int i = 0; i < 9; i++) {
            sb.append(r.nextInt(10));
        }
        return sb.toString();
    }
}
