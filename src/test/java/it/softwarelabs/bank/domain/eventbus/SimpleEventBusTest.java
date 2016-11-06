package it.softwarelabs.bank.domain.eventbus;

import it.softwarelabs.bank.application.domain.account.AccountPrediction;
import it.softwarelabs.bank.application.domain.account.InMemoryAccountViewRepository;
import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.account.event.AccountWasOpened;
import it.softwarelabs.bank.domain.user.UserId;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

public final class SimpleEventBusTest {

    @Test
    public void name() throws Exception {
        final InMemoryAccountViewRepository accountViewRepository = new InMemoryAccountViewRepository();
        final AccountPrediction accountPrediction = new AccountPrediction(accountViewRepository);

        final SimpleEventBus simpleEventBus = new SimpleEventBus(Arrays.asList(accountPrediction));

        simpleEventBus.publish(new AccountWasOpened(new AccountId(), new Number("ASDQWE"), new UserId(), new Money(12.5)));

        assertThat(accountViewRepository.all(), is(not(empty())));
    }
}
