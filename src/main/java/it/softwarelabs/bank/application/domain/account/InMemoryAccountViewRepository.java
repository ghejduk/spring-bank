package it.softwarelabs.bank.application.domain.account;

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
    public List<AccountView> all() {
        return accountViews;
    }

    @Override
    public List<AccountView> findForOwner(UserId id) {
        return accountViews;
    }
}
