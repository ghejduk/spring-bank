package it.softwarelabs.bank.application.account;

import it.softwarelabs.bank.application.domain.account.AccountViewRepository;
import it.softwarelabs.bank.application.domain.transaction.TransactionViewRepository;
import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.transaction.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountDetailsController {

    private final AccountViewRepository accountViewRepository;
    private final TransactionViewRepository transactionViewRepository;

    @Autowired
    public AccountDetailsController(
        AccountViewRepository accountViewRepository,
        TransactionViewRepository transactionViewRepository
    ) {
        this.accountViewRepository = accountViewRepository;
        this.transactionViewRepository = transactionViewRepository;
    }

    @RequestMapping("/account/{accountId}")
    @PreAuthorize("hasPermission(#accountId, 'DISPLAY')")
    public String details(@PathVariable AccountId accountId, Model model) {
        model.addAttribute("account", accountViewRepository.forAccountId(accountId));
        model.addAttribute("transactions", transactionViewRepository.forAccountId(accountId));

        return "account/details";
    }
}
