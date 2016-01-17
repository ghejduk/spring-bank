package it.softwarelabs.bank.application.transaction;

import it.softwarelabs.bank.domain.account.*;
import it.softwarelabs.bank.domain.account.Number;
import it.softwarelabs.bank.domain.transaction.TransactionRepository;
import it.softwarelabs.bank.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TransactionController {

    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String make(Model model, TransactionForm transactionForm, User user) {
        model.addAttribute("transactionForm", transactionForm);
        model.addAttribute("accounts", accountRepository.findByOwner(user).all());
        return "transaction/create";
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public String save(@Valid TransactionForm transactionForm, BindingResult bindingResult, Model model,
                       User user, RedirectAttributes redirectAttributes) throws AccountException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("transactionForm", transactionForm);
            model.addAttribute("accounts", accountRepository.findByOwner(user).all());
            model.addAttribute("bindingResult", bindingResult);
            return "transaction/create";
        }

        Account account = accountRepository.singleByNumber(new Number(transactionForm.getSender()));
        Money amount = new Money(Double.valueOf(transactionForm.getAmount()));
        Number recipient = new Number(transactionForm.getReceiver());
        transactionRepository.add(account.transferFunds(amount, recipient));

        redirectAttributes.addFlashAttribute("successMessage", "Transaction has been booked.");
        return "redirect:/account/" + transactionForm.getSender();
    }
}
