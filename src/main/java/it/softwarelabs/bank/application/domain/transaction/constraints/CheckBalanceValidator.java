package it.softwarelabs.bank.application.domain.transaction.constraints;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
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
    private AccountRepository accountRepository;

    @Autowired
    public CheckBalanceValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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
            String accountNumber = (String) number.get(object);

            Field amount = object.getClass().getDeclaredField(amountField);
            amount.setAccessible(true);
            Object requestedAmount = amount.get(object);

            if (requestedAmount == null || accountNumber == null || accountNumber.length() == 0) {
                return true;
            }

            Account account = accountRepository.singleByNumber(new Number(accountNumber));
            Double accountBalance = account.getBalance().toDouble();
            boolean result = accountBalance >= Double.valueOf(requestedAmount.toString());

            if (!result) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message.replace("{balance}", accountBalance.toString()))
                        .addPropertyNode(amountField)
                        .addConstraintViolation();
            }

            return result;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Could not access properties", e);
        }
    }
}
