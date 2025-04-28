package com.intern.sms.util;

import static com.intern.sms.util.Constants.*;

public class InputValidator {

    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidRole(String role) {
        if (role == null) return false;
        return ROLE_PATTERN.matcher(role).matches();
    }

    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
