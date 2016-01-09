package it.softwarelabs.bank.application.domain.user.constraints;

import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniqueEmail {

    private static UserRepository userRepository;

    @Autowired
    public UniqueEmail(UserRepository userRepository) {
        UniqueEmail.userRepository = userRepository;
    }

    public static boolean isAvailable(String email) {
        return null == userRepository.findByEmail(new Email(email));
    }
}
