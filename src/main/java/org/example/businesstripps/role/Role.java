package org.example.businesstripps.role;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.businesstripps.User.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
