package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumberGeneratorImpl implements NumberGenerator {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private AccountRepository accountRepository;

    @Autowired
    public NumberGeneratorImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Number next() {
        Number number;
        boolean isUnique;

        do {
            number = new Number(randomAlphaNumeric(6));
            isUnique = null == accountRepository.findByNumber(number);
        } while (!isUnique);

        return number;
    }

    private String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
