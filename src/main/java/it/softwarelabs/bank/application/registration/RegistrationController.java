package it.softwarelabs.bank.application.registration;

import it.softwarelabs.bank.domain.user.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private RegisterUser registerUser;

    @Autowired
    public RegistrationController(RegisterUser registerUser) {
        this.registerUser = registerUser;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model, RegisterForm form) {
        model.addAttribute("registerForm", form);
        return "register/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, @Valid RegisterForm form,
                           BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("registerForm", form);
            model.addAttribute("bindingResult", result);
            return "register/register";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Super masz konto, tera się loguj :)");
        registerUser.with(form.getEmail(), form.getPassword());
        return "redirect:/login";
    }
}
