package it.softwarelabs.bank.domain.user;

//import javax.mail.internet.AddressException;
//import javax.mail.internet.InternetAddress;

public class Email {

    private String email;

    protected Email() {
    }

    public Email(String email) {
//        try {
//            InternetAddress internetAddress = new InternetAddress(email);
//            internetAddress.validate();
//        } catch (AddressException e) {
//            throw new IllegalArgumentException(String.format("Given email address '%s' is invalid.", email));
//        }

        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}
