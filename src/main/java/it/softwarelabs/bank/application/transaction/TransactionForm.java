package it.softwarelabs.bank.application.transaction;

import it.softwarelabs.bank.application.domain.transaction.constraints.CheckBalance;
import it.softwarelabs.bank.application.domain.transaction.constraints.ExistingAccount;
import it.softwarelabs.bank.application.domain.transaction.constraints.PrincipalAccount;
import it.softwarelabs.constraints.FieldMatch;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(field = "receiver", matchWith = "sender", inverse = true,
                message = "You have to transfer funds to another account.")
})
@CheckBalance(accountField = "sender", amountField = "amount")
public class TransactionForm {

    @Size(min = 6, max = 6, message = "Account number must be six chars long.")
    @NotNull
    @ExistingAccount
    @PrincipalAccount
    protected String sender;

    @Size(min = 6, max = 6, message = "Account number must be six chars long.")
    @NotNull
    @ExistingAccount
    protected String receiver;

    @Min(value = 10, message = "Minimum value is 10.")
    protected Integer amount;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
