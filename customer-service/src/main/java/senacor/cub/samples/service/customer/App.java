package senacor.cub.samples.service.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Customer Service
 */

@ComponentScan(basePackages = { "senacor.cub.samples.service", "senacor.cub.samples.technical" })
@EnableAutoConfiguration
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
