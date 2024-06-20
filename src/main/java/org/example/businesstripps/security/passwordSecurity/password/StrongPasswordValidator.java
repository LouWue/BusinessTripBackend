package org.example.businesstripps.security.passwordSecurity.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.example.businesstripps.security.constants.RegexConstants.PASSWORD_REGEX;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value.matches(PASSWORD_REGEX);
    }
}