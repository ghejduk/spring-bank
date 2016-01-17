package it.softwarelabs.bank.application.domain.transaction.constraints;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ExistingAccountValidator implements ConstraintValidator<ExistingAccount, String> {

    private String message;
    private AccountRepository accountRepository;

    @Autowired
    public ExistingAccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
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

        boolean isValid = null != accountRepository.singleByNumber(new Number(number));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return isValid;
    }
}
