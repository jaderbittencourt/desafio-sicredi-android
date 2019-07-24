package com.jaderbittencourt.sicredidigital.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static Boolean isBlank(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String emailStr) {
        if (!isBlank(emailStr)) {
            emailStr = emailStr.trim();
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
            return matcher.find();
        } else {
            return false;
        }

    }
}
