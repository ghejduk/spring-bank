package it.softwarelabs.bank.application.domain.transaction;

import it.softwarelabs.bank.domain.eventbus.EventSubscriber;
import it.softwarelabs.bank.domain.eventbus.Subscribe;
import it.softwarelabs.bank.domain.transaction.TransactionStatus;
import it.softwarelabs.bank.domain.transaction.event.TransactionWasCompleted;
import it.softwarelabs.bank.domain.transaction.event.TransactionWasCreated;

public final class TransactionPrediction implements EventSubscriber {

    private final TransactionViewRepository transactionViewRepository;

    public TransactionPrediction(TransactionViewRepository transactionViewRepository) {
        this.transactionViewRepository = transactionViewRepository;
    }

    @Subscribe
    public void handle(TransactionWasCreated event) {
        transactionViewRepository.add(
            new TransactionView(
                event.id().value(),
                event.amount().toDouble(),
                event.createdAt(),
                event.from().value(),
                event.to().value(),
                event.status().ordinal()
            )
        );
    }

    @Subscribe
    public void handle(TransactionWasCompleted event) {
        transactionViewRepository.updateStatus(event.transactionId(), TransactionStatus.COMPLETED.ordinal());
    }
}
