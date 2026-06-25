package src.util;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_PATTERN = 
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(EMAIL_PATTERN, email);
    }
    
    public static String getEmailHelp() {
        return "Email should be like: username@domain.com";
    }
}