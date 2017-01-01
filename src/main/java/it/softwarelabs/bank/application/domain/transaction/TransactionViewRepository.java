package it.softwarelabs.bank.application.domain.transaction;

import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.transaction.TransactionId;

import java.util.List;

public interface TransactionViewRepository {

    void add(TransactionView transactionView);

    void updateStatus(TransactionId id, int status);

    List<TransactionView> forAccountId(AccountId id);
}
