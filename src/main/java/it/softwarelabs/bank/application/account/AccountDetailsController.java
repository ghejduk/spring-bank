package it.softwarelabs.bank.application.account;

import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.account.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountDetailsController {

    private AccountRepository accountRepository;

    @Autowired
    public AccountDetailsController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping("/account/{number}")
    @PreAuthorize("hasPermission(#number, 'DISPLAY')")
    public String details(@PathVariable Number number, Model model) {
        model.addAttribute("account", accountRepository.singleByNumber(number));
        return "account/details";
    }
}
