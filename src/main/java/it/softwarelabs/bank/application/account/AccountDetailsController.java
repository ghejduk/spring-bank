package it.softwarelabs.bank.application.account;

import it.softwarelabs.bank.domain.account.AccountRepository;
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

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public AccountDetailsController(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @RequestMapping("/account/{number}")
    @PreAuthorize("hasPermission(#number, 'DISPLAY')")
    public String details(@PathVariable Number number, Model model) {
        model.addAttribute("account", accountRepository.singleByNumber(number));
        model.addAttribute("transactions", transactionRepository.findByAccount(number).all());
        return "account/details";
    }
}
