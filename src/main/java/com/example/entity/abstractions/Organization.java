package com.example.entity.abstractions;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @DecimalMin("0.00")
    @Column(name = "annual_budget", nullable = false, precision = 15, scale = 2)
    private BigDecimal annualBudget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    @NotBlank
    public String getCountry() {
        return country;
    }

    public void setCountry(@NotBlank String country) {
        this.country = country;
    }

    @NotBlank
    public String getCity() {
        return city;
    }

    public void setCity(@NotBlank String city) {
        this.city = city;
    }

    public @DecimalMin("0.00") BigDecimal getAnnualBudget() {
        return annualBudget;
    }

    public void setAnnualBudget(@DecimalMin("0.00") BigDecimal annualBudget) {
        this.annualBudget = annualBudget;
    }
}
