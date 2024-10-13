package org.example.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.valid.UserPasswordValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PasswordValidation {
    String message() default "Invalid password format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
