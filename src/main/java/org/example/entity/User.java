package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.constant.ServiceValidationConstants;
import org.example.enums.Role;

import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = ServiceValidationConstants.USER_NAME_LENGTH)
    @Size(min = 2, max = 50, message = ServiceValidationConstants.USER_NAME_LENGTH)
    private String username;

    @NotEmpty(message = ServiceValidationConstants.EMAIL_INVALID)
    @Email(message = ServiceValidationConstants.EMAIL_INVALID)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotEmpty(message = ServiceValidationConstants.PASSWORD_LENGTH)
    @Size(min = 6, max = 70, message = ServiceValidationConstants.PASSWORD_LENGTH)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}