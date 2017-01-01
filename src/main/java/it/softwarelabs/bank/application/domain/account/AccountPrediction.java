package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.event.AccountWasOpened;
import it.softwarelabs.bank.domain.account.event.BalanceChanged;
import it.softwarelabs.bank.domain.eventbus.EventSubscriber;
import it.softwarelabs.bank.domain.eventbus.Subscribe;

public class AccountPrediction implements EventSubscriber {

    private final AccountViewRepository accountViewRepository;

    public AccountPrediction(AccountViewRepository accountViewRepository) {
        this.accountViewRepository = accountViewRepository;
    }

    @Subscribe
    public void handle(AccountWasOpened event) {
        accountViewRepository.add(
            event.accountId().value(),
            event.number().toString(),
            event.deposit().toDouble(),
            event.owner().value()
        );
    }

    @Subscribe
    public void handle(BalanceChanged event) {
        accountViewRepository.updateBalance(event.accountId(), event.balance().toDouble());
    }
}
