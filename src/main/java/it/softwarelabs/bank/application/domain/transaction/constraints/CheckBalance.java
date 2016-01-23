package it.softwarelabs.bank.application.domain.transaction.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = CheckBalanceValidator.class)
public @interface CheckBalance {

    String message() default "You cannot transfer more than {balance}.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String accountField();

    String amountField();
}
