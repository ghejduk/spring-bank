package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.UserId;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
public interface AccountViewRepository {

    void add(UUID id, String number, double balance, UUID ownerId);

    void updateBalance(AccountId accountId, double balance);

    List<AccountView> all();

    List<AccountView> findForOwner(UserId id);

    AccountView forNumber(Number number);

    AccountView forAccountId(AccountId accountId);
}
