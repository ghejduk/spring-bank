package it.softwarelabs.bank.application.account;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateAccountForm {

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(10)
    @Max(1_000_000)
    @NotNull
    protected Integer balance = 100;

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
