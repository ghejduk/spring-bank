package it.softwarelabs.bank.application.domain.transaction.constraints;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PrincipalAccountValidator implements ConstraintValidator<PrincipalAccount, String> {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Autowired
    public PrincipalAccountValidator(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
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
        final User user = userRepository.findById(account.getOwnerId());

        if (account == null || !email.equals(user.email().toString())) {
            throw new RuntimeException(String.format("Account %s does not exist", value));
        }

        return true;
    }
}
