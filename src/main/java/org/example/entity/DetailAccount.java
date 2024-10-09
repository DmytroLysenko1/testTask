package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "detail_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate reportingDate;

    @NotNull
    private BigDecimal sum;

    @NotNull
    private BigDecimal percentage;

    @NotNull
    private BigDecimal discountRate;

    @NotNull
    private Long totalSum;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;
}
