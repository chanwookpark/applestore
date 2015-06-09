package applestore.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Configuration
@EnableJpaRepositories(basePackages = "applestore.api",
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "applestore..*JpaRepository"))
public class JpaConfig {
}
