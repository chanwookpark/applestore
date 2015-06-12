package applestore.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@SpringBootApplication
@ComponentScan(basePackages = "applestore")
public class AppleStoreEntityTestApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppleStoreEntityTestApp.class, args);
    }
}
