package org.example.businesstripps.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.businesstripps.role.Role;
import org.example.businesstripps.trip.Trip;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name ="email", nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_trip",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns =@JoinColumn(name = "trip_id"))
    private Set<Trip> trips;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn( name = "role_id"))
    private Set<Role> roles;

}
