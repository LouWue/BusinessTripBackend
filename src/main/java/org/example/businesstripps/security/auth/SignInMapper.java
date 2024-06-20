package org.example.businesstripps.security.auth;

import org.example.businesstripps.User.User;
import org.example.businesstripps.security.passwordSecurity.Validator;

public class SignInMapper {
    public static SignInResDTO toDTO(User src, String accessToken) {
        SignInResDTO dto = new SignInResDTO();

        dto.setId(src.getId());
        dto.setUsername(src.getUsername());
        dto.setEmail(src.getEmail());

        /*
        TODO: Remove comment as soon as Role is implemented
        List<String> roles = src.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        dto.setRoles(roles);
         */
        dto.setToken(accessToken);
        return dto;
    }

    public static User fromDTO(SignInReqDTO dto) {
        User u = new User();

        u.setPassword(dto.getPassword());
        Validator.setPrincipal(dto.getPrincipal(), u);

        return u;
    }
}
