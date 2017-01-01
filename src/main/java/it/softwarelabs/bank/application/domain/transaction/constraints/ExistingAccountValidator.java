package it.softwarelabs.bank.application.domain.transaction.constraints;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.eventstore.EventStore;
import it.softwarelabs.bank.domain.eventstore.exception.EventStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ExistingAccountValidator implements ConstraintValidator<ExistingAccount, String> {

    private String message;
    private final EventStore eventStore;

    @Autowired
    public ExistingAccountValidator(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void initialize(ExistingAccount constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        if (number == null || number.length() == 0) {
            return true;
        }

        try {
            eventStore.loadEventStreamFor(new AccountId(number));

            return true;
        } catch (EventStoreException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

            return false;
        }
    }
}
