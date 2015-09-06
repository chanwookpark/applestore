package applestore.domain;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "applestore")
// for JPA Auditing
@EnableJpaAuditing
@EntityScan(basePackageClasses = { EntityTestApp.class, Jsr310JpaConverters.class })
public class EntityTestApp {
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(EntityTestApp.class, args);
//    }
}
