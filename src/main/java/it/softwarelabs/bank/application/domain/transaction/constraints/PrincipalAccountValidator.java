package it.softwarelabs.bank.application.domain.transaction.constraints;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PrincipalAccountValidator implements ConstraintValidator<PrincipalAccount, String> {

    private AccountRepository accountRepository;

    @Autowired
    public PrincipalAccountValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void initialize(PrincipalAccount constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.singleByNumber(new Number(value));

        if (null == account || !email.equals(account.getOwner().email().toString())) {
            throw new RuntimeException(String.format("Account %s does not exist", value));
        }

        return true;
    }
}
