package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.annotations.PasswordValidation;
import org.example.constant.ServiceValidationConstants;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {
    @Size(min = 1, max = 70, message = ServiceValidationConstants.USER_NAME_LENGTH)
    private String username;
    @Email
    private String email;
    @PasswordValidation
    @Size(min = 6, max = 70, message = ServiceValidationConstants.PASSWORD_LENGTH)
    private String password;
}
