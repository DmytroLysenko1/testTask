package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.constant.ServiceValidationConstants;
import org.example.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = ServiceValidationConstants.BANK_NUMBER_NOT_EMPTY)
    private String bankNumber;

    @NotEmpty(message = ServiceValidationConstants.TYPE_NOT_EMPTY)
    private String type;

    @NotNull(message = ServiceValidationConstants.STATUS_NOT_NULL)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @NotNull(message = ServiceValidationConstants.DATE_START_NOT_NULL)
    private LocalDate dateStart;

    private LocalDate dateEnd;

    @NotNull(message = ServiceValidationConstants.VALID_UNTIL_NOT_NULL)
    private LocalDate validUntil;

    @NotNull(message = ServiceValidationConstants.BALANCE_NOT_NULL)
    private BigDecimal balance = BigDecimal.ZERO;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<DetailAccount> detailAccounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
