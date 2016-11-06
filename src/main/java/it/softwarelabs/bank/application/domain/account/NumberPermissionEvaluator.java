package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class NumberPermissionEvaluator implements PermissionEvaluator {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public NumberPermissionEvaluator(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Number && permission.equals("DISPLAY")) {
            Account account = accountRepository.singleByNumber((Number) targetDomainObject);
            final User user = userRepository.findById(account.getOwnerId());
            return user.email().toString().equals(authentication.getName());
        }

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
