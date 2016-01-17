package it.softwarelabs.bank.application.domain.transaction.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = PrincipalAccountValidator.class)
public @interface PrincipalAccount {

    String message() default "Account not found.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
