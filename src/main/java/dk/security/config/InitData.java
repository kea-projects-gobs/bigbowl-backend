package dk.security.config;

import dk.security.entity.Customer;
import dk.security.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder pwEncoder;

    public InitData(CustomerRepository customerRepository, PasswordEncoder pwEncoder) {
        this.customerRepository = customerRepository;
        this.pwEncoder = pwEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer();
        customer.setEmail("test@email.dk");
        customer.setUsername("test");
        customer.setPassword(pwEncoder.encode("password"));
        customerRepository.save(customer);



    }
}
