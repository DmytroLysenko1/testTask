package org.example.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.annotations.PasswordValidation;
import org.passay.*;

public class UserPasswordValidator implements ConstraintValidator<PasswordValidation, String> {
    private PasswordValidator validator;

    public UserPasswordValidator() {
        this.validator = new PasswordValidator(
                new LengthRule(8, 20),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        RuleResult result = validator.validate(new PasswordData(value));
        if (result.isValid()) {
            return true;
        } else {
            String message = validator.getMessages(result).stream().reduce((x, y) -> x + " " + y)
                    .orElse("Invalid password format!");
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }
    }
}

