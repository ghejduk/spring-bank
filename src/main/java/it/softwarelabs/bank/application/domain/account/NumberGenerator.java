package it.softwarelabs.bank.application.domain.account;

import it.softwarelabs.bank.domain.account.Number;

public interface NumberGenerator {

    Number next();
}
