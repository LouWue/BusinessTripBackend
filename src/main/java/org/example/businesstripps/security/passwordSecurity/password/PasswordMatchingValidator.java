package org.example.businesstripps.security.passwordSecurity.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {
    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordMatching matching) {
        this.password = matching.password();
        this.confirmPassword = matching.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        Object pwdValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object confirmPwdValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

        return Objects.equals(pwdValue, confirmPwdValue);
    }
}
