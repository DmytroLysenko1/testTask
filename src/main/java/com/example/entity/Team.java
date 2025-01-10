package com.example.entity;

import com.example.entity.abstractions.Organization;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "team", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Team extends Organization {

    @Column(nullable = false)
    private Double commission;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Player> players;

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }
}

