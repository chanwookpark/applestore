package applestore.front.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author chanwook
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(SecurityConfig.class, SessionClusterConfig.class);
    }
}
