package it.softwarelabs.bank.application.account;

import it.softwarelabs.bank.application.domain.account.CreateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CreateAccountController {

    @Autowired
    private CreateAccount createAccount;

    @RequestMapping(value = "/account/create", method = RequestMethod.GET)
    public String create(CreateAccountForm form, Model model) {
        model.addAttribute("accountForm", form);
        return "account/create";
    }

    @RequestMapping(value = "/account/create", method = RequestMethod.POST)
    public String create(Model model, @Valid CreateAccountForm form, BindingResult result,
                         RedirectAttributes redirectAttributes, Principal principal) {
        if (!result.hasErrors()) {
            createAccount.open(principal.getName(), form.getBalance());
            redirectAttributes.addFlashAttribute("successMessage", "Account has been created.");
            return "redirect:/";
        }

        model.addAttribute("accountForm", form);
        model.addAttribute("bindingResult", result);
        return "account/create";
    }
}
