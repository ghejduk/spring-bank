package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserId;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

public class NumberPermissionEvaluator implements PermissionEvaluator {

    private final AccountViewRepository accountViewRepository;
    private final UserRepository userRepository;

    public NumberPermissionEvaluator(AccountViewRepository accountViewRepository, UserRepository userRepository) {
        this.accountViewRepository = accountViewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Number && permission.equals("DISPLAY")) {
            final AccountView accountView = accountViewRepository.forNumber((Number) targetDomainObject);
            final User user = userRepository.findById(new UserId(accountView.getOwnerId()));
            return user.email().toString().equals(authentication.getName());
        }

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
