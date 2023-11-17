package com.etaskify.etaskifybackend.utility;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {
    private static final int PASSWORD_LENGTH = 12;
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String ALPHA_NUMERIC_STRING = LOWER_CASE + UPPER_CASE + NUMBERS;
    private static final SecureRandom random = new SecureRandom();

    public String generatePassword() {
        StringBuilder builder = new StringBuilder();
        while (PASSWORD_LENGTH > builder.length()) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }
}
