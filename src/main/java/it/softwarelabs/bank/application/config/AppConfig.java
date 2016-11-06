package it.softwarelabs.bank.application.config;

import it.softwarelabs.bank.application.domain.account.AccountPrediction;
import it.softwarelabs.bank.application.domain.account.AccountViewRepository;
import it.softwarelabs.bank.domain.account.AccountRepository;
import it.softwarelabs.bank.domain.eventbus.EventBus;
import it.softwarelabs.bank.domain.eventbus.EventSubscriber;
import it.softwarelabs.bank.domain.eventbus.SimpleEventBus;
import it.softwarelabs.bank.domain.stereotype.Factory;
import it.softwarelabs.bank.domain.stereotype.Service;
import it.softwarelabs.bank.domain.transaction.BookTransaction;
import it.softwarelabs.bank.domain.transaction.TransactionRepository;
import it.softwarelabs.bank.domain.user.PasswordEncoder;
import it.softwarelabs.bank.domain.user.RegisterUser;
import it.softwarelabs.bank.domain.user.UserFactory;
import it.softwarelabs.bank.domain.user.UserRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/db/jdbc.properties")
@Import({WebConfig.class, SecurityConfig.class})
@ComponentScan(
    basePackages = {"it.softwarelabs.bank.application", "it.softwarelabs.bank.domain"},
    includeFilters = {
        @ComponentScan.Filter(value = {Service.class, Factory.class}, type = FilterType.ANNOTATION)
    }
)
public class AppConfig {

    @Value("${jdbc.driver}")
    private String jdbcDriverClass;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user}")
    private String jdbcUser;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(destroyMethod = "close")
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(jdbcDriverClass);
        dataSource.setUsername(jdbcUser);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMappingResources(
            "/hibernate/User.hbm.xml",
            "/hibernate/Transaction.hbm.xml"
        );
        sessionFactory.afterPropertiesSet();
        return sessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public BookTransaction bookTransaction(AccountRepository accountRepository,
                                           TransactionRepository transactionRepository) {
        return new BookTransaction(accountRepository, transactionRepository);
    }

    @Bean
    public RegisterUser registerUser(
        PasswordEncoder passwordEncoder,
        UserFactory userFactory,
        UserRepository userRepository
    ) {
        return new RegisterUser(passwordEncoder, userFactory, userRepository);
    }

    @Bean
    public AccountPrediction accountPrediction(AccountViewRepository accountViewRepository) {
        return new AccountPrediction(accountViewRepository);
    }

    @Bean
    public EventBus eventBus(List<EventSubscriber> eventSubscribers) {
        return new SimpleEventBus(eventSubscribers);
    }
}
