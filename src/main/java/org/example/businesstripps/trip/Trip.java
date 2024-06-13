package org.example.businesstripps.trip;

import jakarta.persistence.*;
import org.example.businesstripps.User.User;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "startAt", nullable = false)
    private Timestamp startAt;

    @Column(name = "endAt", nullable = false)
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
