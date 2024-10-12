package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.constant.ServiceValidationConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "detail_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = ServiceValidationConstants.REPORTING_DATE_NOT_NULL)
    private LocalDate reportingDate;

    @NotNull(message = ServiceValidationConstants.SUM_NOT_NULL)
    private BigDecimal sum;

    @NotNull(message = ServiceValidationConstants.PERCENTAGE_NOT_NULL)
    private BigDecimal percentage;

    @NotNull(message = ServiceValidationConstants.DISCOUNT_RATE_NOT_NULL)
    private BigDecimal discountRate;

    @NotNull(message = ServiceValidationConstants.TOTAL_SUM_NOT_NULL)
    private Long totalSum;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)
    private BankAccount bankAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailAccount that = (DetailAccount) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
