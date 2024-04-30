package com.example.demo.utils;

import java.util.regex.Pattern;

public class Common {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.]+@[A-Za-z0-9+_.]+$");

    public static boolean isValidEmail(String emString) {
        if (emString == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(emString).matches();
    }
}
