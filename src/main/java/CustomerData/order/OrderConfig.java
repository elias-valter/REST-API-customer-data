package CustomerData.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    CommandLineRunner orderCommandLineRunner(OderRepository oderRepository){
        return args -> oderRepository.saveAll(oderRepository.findAll());
    }
}
