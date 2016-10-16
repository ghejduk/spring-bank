package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.*;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.eventstore.Aggregate;
import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAccount {

    private NumberGenerator numberGenerator;
    private AccountRepository accountRepository;
    private UserRepository userRepository;

    @Autowired
    public CreateAccount(NumberGenerator numberGenerator,
                         AccountRepository accountRepository,
                         UserRepository userRepository) {
        this.numberGenerator = numberGenerator;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Aggregate open(String email, int balance) {
        Number number = numberGenerator.next();
        User user = userRepository.findByEmail(new Email(email));
        Aggregate account = Account.open(new AccountId(), number, user, new Money((double) balance));
        accountRepository.add(account);
        return account;
    }
}
