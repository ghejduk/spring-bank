package it.softwarelabs.bank.application.account;

import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.eventstore.Aggregate;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.collection.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountsController {

    private AccountRepository accountRepository;

    @Autowired
    public AccountsController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(Model model, User user) {
        Collection<Aggregate> collection = accountRepository.findByOwner(user);
        model.addAttribute("accounts", collection.all());
        return "account/list";
    }
}
