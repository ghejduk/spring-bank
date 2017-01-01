package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.user.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class InMemoryAccountViewRepository implements AccountViewRepository {

    private final List<AccountView> accountViews = new ArrayList<>();

    @Override
    public void add(UUID id, String number, double balance, UUID ownerId) {
        accountViews.add(new AccountView(id, number, balance, ownerId));
    }

    @Override
    public void updateBalance(AccountId accountId, double balance) {
        // todo
    }

    @Override
    public List<AccountView> all() {
        return accountViews;
    }

    @Override
    public List<AccountView> findForOwner(UserId id) {
        return accountViews;
    }

    @Override
    public AccountView forNumber(Number number) {
        return accountViews.stream()
            .filter(accountView -> accountView.getNumber().equals(number.toString()))
            .findFirst()
            .get();
    }

    @Override
    public AccountView forAccountId(AccountId accountId) {
        // todo
        return null;
    }
}
