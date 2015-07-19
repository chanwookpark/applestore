package applestore.domain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author chanwook
 */
@Configuration
@EnableMongoRepositories(basePackages = "applestore.domain")
public class MongoConfig {
}
