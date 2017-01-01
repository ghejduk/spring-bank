package it.softwarelabs.bank.application.transaction;

import it.softwarelabs.bank.application.domain.account.AccountViewRepository;
import it.softwarelabs.bank.domain.account.AccountId;
import it.softwarelabs.bank.domain.account.Money;
import it.softwarelabs.bank.domain.transaction.BookTransaction;
import it.softwarelabs.bank.domain.transaction.BookingFailed;
import it.softwarelabs.bank.domain.transaction.CannotCompleteTransaction;
import it.softwarelabs.bank.domain.transaction.Transaction;
import it.softwarelabs.bank.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TransactionController {

    private final AccountViewRepository accountViewRepository;
    private final BookTransaction bookTransaction;

    @Autowired
    public TransactionController(AccountViewRepository accountViewRepository, BookTransaction bookTransaction) {
        this.accountViewRepository = accountViewRepository;
        this.bookTransaction = bookTransaction;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public String make(Model model, TransactionForm transactionForm, User user) {
        model.addAttribute("transactionForm", transactionForm);
        model.addAttribute("accounts", accountViewRepository.findForOwner(user.id()));

        return "transaction/create";
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public String save(@Valid TransactionForm transactionForm, BindingResult bindingResult, Model model, User user,
                       RedirectAttributes redirectAttributes) throws BookingFailed, CannotCompleteTransaction {
        if (bindingResult.hasErrors()) {
            model.addAttribute("transactionForm", transactionForm);
            model.addAttribute("accounts", accountViewRepository.findForOwner(user.id()));
            model.addAttribute("bindingResult", bindingResult);

            return "transaction/create";
        }

        Transaction transaction = Transaction.create(
            new AccountId(transactionForm.getSender()),
            new AccountId(transactionForm.getReceiver()),
            new Money(Double.valueOf(transactionForm.getAmount()))
        );
        bookTransaction.book(transaction);

        redirectAttributes.addFlashAttribute("successMessage", "Transaction has been booked.");

        return "redirect:/account/" + transactionForm.getSender();
    }
}
