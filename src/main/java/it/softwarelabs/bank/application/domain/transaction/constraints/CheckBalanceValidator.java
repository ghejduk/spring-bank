package it.softwarelabs.bank.application.domain.transaction.constraints;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.EventStore;
import it.softwarelabs.bank.domain.eventstore.exception.EventStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Component
public class CheckBalanceValidator implements ConstraintValidator<CheckBalance, Object> {

    private String message;
    private String accountField;
    private String amountField;
    private final EventStore eventStore;

    @Autowired
    public CheckBalanceValidator(EventStore eventStore) {
        this.eventStore = eventStore;
    }


    @Override
    public void initialize(CheckBalance constraintAnnotation) {
        message = constraintAnnotation.message();
        accountField = constraintAnnotation.accountField();
        amountField = constraintAnnotation.amountField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Field number = object.getClass().getDeclaredField(accountField);
            number.setAccessible(true);
            String accountId = (String) number.get(object);

            Field amount = object.getClass().getDeclaredField(amountField);
            amount.setAccessible(true);
            Object requestedAmount = amount.get(object);

            if (requestedAmount == null || accountId == null || accountId.length() == 0) {
                return true;
            }

            Account account = new Account(eventStore.loadEventStreamFor(new AccountId(accountId)));
            Double accountBalance = account.getBalance().toDouble();
            boolean result = accountBalance >= Double.valueOf(requestedAmount.toString());

            if (!result) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message.replace("{balance}", accountBalance.toString()))
                        .addPropertyNode(amountField)
                        .addConstraintViolation();
            }

            return result;
        } catch (NoSuchFieldException | IllegalAccessException | EventStoreException e) {
            throw new RuntimeException("Could not access properties", e);
        }
    }
}
