package org.example.businesstripps.security.auth;

import org.example.businesstripps.User.User;

public class SignUpMapper {
    public static SignUpResDTO toDTO(User src) {
        SignUpResDTO res = new SignUpResDTO();
        res.setId(src.getId());
        res.setUsername(src.getUsername());
        res.setEmail(src.getEmail());

        /*
        TODO: Remove comment as soon as Role is implemented
        res.setRoles(src.getRoles()
                        .stream()
                        .map(role ->
                                role.getName()
                                    .name())
                        .toList()
        );
         */
        return res;
    }

    public static User fromDTO(SignUpReqDTO dto) {
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        /*
        TODO: Remove comment as soon as Role is implemented
        Set<String> strRoles = reqDTO.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = findByName(ERole.ROLE_USER).orElseThrow(() -> new EntityNotFoundException("Error: Requested role could not be found!"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin", "ADMIN", "Admin":
                        Role admin = findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new EntityNotFoundException("Error: Role \"" + role + "\"could not be found"));
                        roles.add(admin);
                        break;
                    case "moderator", "mod", "MODERATOR", "MOD", "Moderator":
                        Role moderator = findByName(ERole.ROLE_MODERATOR).orElseThrow(() -> new EntityNotFoundException("Error: Role \"" + role + "\"could not be found"));
                        roles.add(moderator);
                        break;
                    default:
                        Role usr = findByName(ERole.ROLE_USER).orElseThrow(() -> new EntityNotFoundException("Error: Role \"" + role + "\"could not be found"));
                        roles.add(usr);
                }
            });
        }
        u.setRoles(roles);*/
        return u;
    }
}
