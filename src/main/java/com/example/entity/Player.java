package com.example.entity;

import com.example.entity.abstractions.Person;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Entity
@Table(name = "player", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"team_id"})
})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"team"})
public class Player extends Person {

    @DecimalMin("0.00")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

    @Min(0)
    @Column(name ="experience_months", nullable = false)
    private int experienceMonths;

    @DecimalMin("0.00")
    @Column(name = "market_value", nullable = false, precision = 12, scale = 2)
    private BigDecimal marketValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "team_id", nullable = false)
    @NotNull
    private Team team;


    public @DecimalMin("0.00") BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(@DecimalMin("0.00") BigDecimal salary) {
        this.salary = salary;
    }

    @Min(0)
    public int getExperienceMonths() {
        return experienceMonths;
    }

    public void setExperienceMonths(@Min(0) int experienceMonths) {
        this.experienceMonths = experienceMonths;
    }


    public @DecimalMin("0.00") BigDecimal getMarketValue() {
        return marketValue;
    }

    public void  setMarketValue(@DecimalMin("0.00")BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    @NotNull
    public Team getTeam() {
        return team;
    }

    public void setTeam(@NotNull Team team) {
        this.team = team;
    }
}