package it.softwarelabs.bank.application.domain.user;

import it.softwarelabs.bank.domain.user.Email;
import it.softwarelabs.bank.domain.user.User;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class BankUserDetails implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public BankUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(new Email(username));

        if (null == user) {
            throw new UsernameNotFoundException("Invalid credentials.");
        }

        Collection<SimpleGrantedAuthority> granted = new ArrayList<SimpleGrantedAuthority>();
        granted.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
            user.email().toString(), user.passwordHash().toString(), granted
        );
    }
}
