package org.example.businesstripps.security.auth;

import org.example.businesstripps.security.JwtGenerator;
import org.example.businesstripps.security.passwordSecurity.Validator;
import org.example.businesstripps.security.exceptions.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.example.businesstripps.User.User;
import org.example.businesstripps.User.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {
    private final UserService uSrv;
    private final AuthenticationManager authMgr;

    public AuthService(UserService uSrv, AuthenticationManager authMgr) {
        this.uSrv = uSrv;
        this.authMgr = authMgr;
    }

    public SignUpResDTO SignUp(SignUpReqDTO dto) {
        Map<String, List<String>> errors = new HashMap<>();
        User newUser = new User();
        User u = Validator.validateCredentials(dto, newUser);
        if (uSrv.existsByUsername(u.getUsername())) {
            errors.put("username", List.of("Username is already taken!"));
        }
        if (uSrv.existsByEmail(u.getEmail())) {
            errors.put("email", List.of("Email is already taken!"));
        }
        if (!errors.isEmpty()) {
            throw new UserAlreadyExistsException(errors);
        }
        User uToBeResponded = uSrv.create(u);
        return SignUpMapper.toDTO(uToBeResponded);
    }

    public SignInResDTO SignIn(SignInReqDTO dto) {
        User u = SignInMapper.fromDTO(dto);
        String password = dto.getPassword();

        if (u.getEmail() != null) {
            u = uSrv.findByEmail(u.getEmail());
        } else if (u.getUsername() != null) {
            u = uSrv.findByUsername(u.getUsername());
        } else {
            throw new EntityNotFoundException();
        }

        Authentication token = new UsernamePasswordAuthenticationToken(u.getUsername(), password);

        if (authMgr.authenticate(token).isAuthenticated()) {
            String jwt = JwtGenerator.generateJwtToken(u.getUsername());
            return SignInMapper.toDTO(u, jwt);
        } else {
            throw new BadCredentialsException("Invalid credentials!");
        }
    }
}

