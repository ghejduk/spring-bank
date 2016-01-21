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

    public void book(Transaction transaction) throws AccountException, TransactionAlreadyCompleted {
        Account sender = accountRepository.singleByNumber(transaction.getFrom());
        Account recipient = accountRepository.singleByNumber(transaction.getTo());

        sender.bookTransaction(transaction);
        recipient.bookTransaction(transaction);
        transaction.complete();

        accountRepository.update(sender);
        accountRepository.update(recipient);
        transactionRepository.update(transaction);
    }
}
