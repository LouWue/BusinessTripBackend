package org.example.businesstripps.security.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.example.businesstripps.security.passwordSecurity.password.PasswordMatching;
import org.example.businesstripps.security.passwordSecurity.password.StrongPassword;

import java.util.Objects;

import static org.example.businesstripps.security.constants.RegexConstants.*;


@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password don't match!"
)
public class SignUpReqDTO {
    @NotBlank(message = "Username is required!")
    @Pattern(regexp = USERNAME_REGEX, message = "Invalid username!")
    @Size(min = 3, max = 255, message = "Username must be between 3 and 255 characters!")
    private String username;

    @Email
    @Pattern(regexp = EMAIL_REGEX, message = "Invalid email!")
    @NotBlank(message = "Email is required!")
    @Size(min = 5, max = 255)
    private String email;

    @NotBlank(message = "Password is required!")
    @StrongPassword
    private String password;
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignUpReqDTO that = (SignUpReqDTO) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

