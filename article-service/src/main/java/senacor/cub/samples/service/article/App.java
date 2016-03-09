package senacor.cub.samples.service.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Article Service
 */
@ComponentScan
@EnableAutoConfiguration
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}