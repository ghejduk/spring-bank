package it.softwarelabs.bank.application.domain.user.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

    private String message;

    @Override
    public void initialize(UniqueUserEmail constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean isAvailable = UniqueEmail.isAvailable(email);

        if (!isAvailable) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return isAvailable;
    }
}
