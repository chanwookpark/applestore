package applestore.front.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author chanwook
 */
@Configuration
@EnableRedisHttpSession
public class SessionClusterConfig {
    @Bean
    public JedisConnectionFactory connectionFactory() {
        final JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName("pub-redis-14669.us-east-1-3.6.ec2.redislabs.com");
        connectionFactory.setPort(14669);
        connectionFactory.setPassword("1234");
        return connectionFactory;
    }
}
