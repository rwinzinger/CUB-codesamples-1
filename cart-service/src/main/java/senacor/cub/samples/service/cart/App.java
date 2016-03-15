package senacor.cub.samples.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import senacor.cub.samples.technical.es.EventHandlerRegistry;

/**
 * Cart Service
 */
@ComponentScan(basePackages = { "senacor.cub.samples.service", "senacor.cub.samples.technical" })
@EnableAutoConfiguration
public class App {
    @Autowired
    EventHandlerRegistry eventHandlerRegistry;

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
