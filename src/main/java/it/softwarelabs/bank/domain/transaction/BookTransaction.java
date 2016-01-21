package it.softwarelabs.bank.domain.transaction;

import it.softwarelabs.bank.domain.account.Account;
import it.softwarelabs.bank.domain.account.AccountException;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.stereotype.Service;

@Service
public class BookTransaction {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public BookTransaction(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void book(Transaction transaction) throws BookingFailed {
        Account sender = accountRepository.singleByNumber(transaction.getFrom());
        Account recipient = accountRepository.singleByNumber(transaction.getTo());

        try {
            sender.bookTransaction(transaction);
            recipient.bookTransaction(transaction);
            transaction.complete();
        } catch (AccountException | TransactionAlreadyCompleted e) {
            throw new BookingFailed(String.format("Could not book transaction %s", transaction.getId()), e);
        }

        accountRepository.update(sender);
        accountRepository.update(recipient);
        transactionRepository.update(transaction);
    }
}
