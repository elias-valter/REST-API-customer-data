package CustomerData.customer.config;

import CustomerData.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig {

    /**
     * creats a @Bean which refers to an object that’s managed by the Spring Inversion of Control (IoC) container
     * @param customerRepository customerRepository to manage all the http requests
     * @return CommandLineRunnerFunction to manage all the http request
     */
    @Bean
    CommandLineRunner customerCommandLineRunner(CustomerRepository customerRepository){
        return args -> customerRepository.saveAll(customerRepository.findAll());
    }

}
