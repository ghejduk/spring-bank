package it.softwarelabs.bank.application.config;

import it.softwarelabs.bank.application.domain.account.AccountIdPermissionEvaluator;
import it.softwarelabs.bank.application.domain.account.AccountViewRepository;
import it.softwarelabs.bank.application.domain.user.BCryptEncoder;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/static/**", "/webjars/**", "/login", "/register").permitAll()
            .antMatchers("/**").authenticated();

        http.formLogin().loginPage("/login").defaultSuccessUrl("/");
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true);
    }

    @Bean
    public AccountIdPermissionEvaluator accountIdPermissionEvaluator(
        AccountViewRepository accountViewRepository,
        UserRepository userRepository
    ) {
        return new AccountIdPermissionEvaluator(accountViewRepository, userRepository);
    }

    @Bean
    public MethodSecurityExpressionHandler expressionHandler(AccountIdPermissionEvaluator evaluator) {
        DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
        handler.setPermissionEvaluator(evaluator);
        return handler;
    }

    @Bean
    public BCryptEncoder passwordEncoder() {
        return new BCryptEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(13);
    }
}
