package applestore.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@SpringBootApplication
@ComponentScan(basePackages = "applestore")
@RestController
public class AppleStoreAdminApp extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppleStoreAdminApp.class, args);
    }

    @RequestMapping("/")
    public String greeting() {
        return "Hi~!";
    }
}
