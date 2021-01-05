package shopApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import shopApp.repository.UserRepository;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})

public class MyShopApplication {

    public static void main(String[] args){
        SpringApplication.run(MyShopApplication.class, args);
    }
}
