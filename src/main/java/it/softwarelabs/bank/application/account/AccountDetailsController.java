package it.softwarelabs.bank.application.account;

import it.softwarelabs.bank.application.domain.account.AccountViewRepository;
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
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountDetailsController(
        AccountViewRepository accountViewRepository,
        TransactionRepository transactionRepository
    ) {
        this.accountViewRepository = accountViewRepository;
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping("/account/{number}")
    @PreAuthorize("hasPermission(#number, 'DISPLAY')")
    public String details(@PathVariable Number number, Model model) {
        model.addAttribute("account", accountViewRepository.forNumber(number));
        model.addAttribute("transactions", transactionRepository.findByAccount(number).all());
        return "account/details";
    }
}
