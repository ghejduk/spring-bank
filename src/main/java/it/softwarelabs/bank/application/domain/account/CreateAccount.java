package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.EventStore;
import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAccount {

    private final NumberGenerator numberGenerator;
    private final UserRepository userRepository;
    private final EventStore eventStore;

    @Autowired
    public CreateAccount(
        NumberGenerator numberGenerator,
        UserRepository userRepository,
        EventStore eventStore
    ) {
        this.numberGenerator = numberGenerator;
        this.userRepository = userRepository;
        this.eventStore = eventStore;
    }

    public void open(String email, int balance) {
        Number number = numberGenerator.next();
        User user = userRepository.findByEmail(new Email(email));
        Account account = Account.open(new AccountId(), number, user, new Money((double) balance));
        eventStore.appendToEventStream(account.id(), 0, account.producedEvents());
    }
}
