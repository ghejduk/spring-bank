package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class NumberPermissionEvaluator implements PermissionEvaluator {

    private AccountRepository accountRepository;

    public NumberPermissionEvaluator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Number && permission.equals("DISPLAY")) {
            Account account = accountRepository.singleByNumber((Number) targetDomainObject);
            return account.getOwner().email().toString().equals(authentication.getName());
        }

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
