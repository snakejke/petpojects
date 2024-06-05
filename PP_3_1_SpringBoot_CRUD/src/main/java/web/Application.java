package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "web")
@EnableTransactionManagement
@EntityScan(basePackages = "web.model")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
