package it.softwarelabs.bank.application.registration;

import it.softwarelabs.bank.application.domain.user.constraints.UniqueUserEmail;
import it.softwarelabs.constraints.FieldMatch;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(field = "repeatPassword", matchWith = "password", message = "Passwords does not match.")
})
public final class RegisterForm {

    @Size(min = 5, max = 100)
    @Email
    @UniqueUserEmail
    protected String email;

    @Size(min = 5, max = 100)
    protected String password;

    @Size(min = 5, max = 100)
    protected String repeatPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
