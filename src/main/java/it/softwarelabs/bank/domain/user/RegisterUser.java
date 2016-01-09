package it.softwarelabs.bank.domain.user;

import it.softwarelabs.bank.domain.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RegisterUser {

    private PasswordEncoder passwordEncoder;
    private UserFactory userFactory;
    private UserRepository userRepository;

    @Autowired
    public RegisterUser(PasswordEncoder passwordEncoder, UserFactory userFactory, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userFactory = userFactory;
        this.userRepository = userRepository;
    }

    public User with(String email, String password) {
        User user = userFactory.create(email);
        user.updatePassword(password, passwordEncoder);
        userRepository.add(user);
        return user;
    }
}
