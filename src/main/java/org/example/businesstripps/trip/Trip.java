package org.example.businesstripps.trip;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.businesstripps.User.User;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trip {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "budget", nullable = false)
   private float budget;

    @Column(name = "title", nullable = false)
    private String Title;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "start_at", nullable = false)
    private Timestamp startAt;

    @Column(name = "end_at", nullable = false)
    private Timestamp endAt;

    @ManyToMany(mappedBy = "trips")
    private Set<User> users;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id == trip.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
