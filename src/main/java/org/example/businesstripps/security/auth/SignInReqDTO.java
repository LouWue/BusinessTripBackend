package org.example.businesstripps.security.auth;


import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class SignInReqDTO {
    @NotBlank
    private String principal;

    @NotBlank
    private String password;

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInReqDTO that = (SignInReqDTO) o;
        return Objects.equals(principal, that.principal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(principal);
    }
}

