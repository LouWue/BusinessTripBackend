package org.example.businesstripps.security.passwordSecurity;


import org.example.businesstripps.security.exceptions.FailedValidationException;
import org.example.businesstripps.security.auth.SignUpReqDTO;
import org.example.businesstripps.User.User;
import org.example.businesstripps.security.auth.SignInReqDTO;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.businesstripps.security.constants.RegexConstants.*;

/**
 * Utility class for validating authentication inputs using regular expressions.
 */
public class Validator {
    /**
     * Checks the principal value in a sign-in request and sets the corresponding user property.
     *
     * @param principal  The principal provided by the {@link SignInReqDTO}.
     * @param user The user object to sign in.
     * @throws BadCredentialsException if the principal is invalid.
     */
    public static void setPrincipal(String principal, User user) throws BadCredentialsException {
        if (Pattern.matches(EMAIL_REGEX, principal)) {
            user.setEmail(principal);
        } else if (Pattern.matches(USERNAME_REGEX, principal)) {
            user.setUsername(principal);
        } else {
            throw new BadCredentialsException("Invalid credentials, user could not be found!");
        }
    }

    /**
     * Validates the credentials provided in a sign-up request.
     *
     * @param dto  The sign-up request DTO.
     * @param user The user object to update.
     * @throws FailedValidationException if the credentials are invalid.
     */
    public static User validateCredentials(SignUpReqDTO dto, User user) throws FailedValidationException {
        Map<String, List<String>> errors = new HashMap<>();

        validateField(dto.getEmail(), EMAIL_PATTERN, "email", errors);
        validateField(dto.getUsername(), USERNAME_PATTERN, "username", errors);
        validateField(dto.getPassword(), PASSWORD_PATTERN, "password", errors);

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        } else {
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            return user;
        }
    }

    private static void validateField(String value, Pattern pattern, String fieldName, Map<String, List<String>> errors) {
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            errors.put(fieldName, List.of("Invalid " + fieldName + "!"));
        }
    }

    public static Boolean isValidUsername(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }
    public static Boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
    public static Boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

}
