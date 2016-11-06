package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.event.AccountWasOpened;
import it.softwarelabs.bank.domain.eventbus.EventSubscriber;
import it.softwarelabs.bank.domain.eventbus.Subscribe;
import org.springframework.stereotype.Component;

@Component
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
}
