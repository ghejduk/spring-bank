package it.softwarelabs.bank.application.account;

import it.softwarelabs.bank.application.domain.account.AccountViewRepository;
import it.softwarelabs.bank.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountsController {

    private final AccountViewRepository accountViewRepository;

    @Autowired
    public AccountsController(AccountViewRepository accountViewRepository) {
        this.accountViewRepository = accountViewRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model, User user) {
        model.addAttribute("accounts", accountViewRepository.findForOwner(user.id()));
        return "account/list";
    }
}
