package org.example.businesstripps.security.passwordSecurity.password;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {
    String message() default "Must be 8 characters long and combination of at least one uppercase, one lowercase, one digit, one special character!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

