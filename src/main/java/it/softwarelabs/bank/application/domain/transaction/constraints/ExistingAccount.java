package it.softwarelabs.bank.application.domain.transaction.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Constraint(validatedBy = ExistingAccountValidator.class)
public @interface ExistingAccount {

    String message() default "Account does not exist.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
