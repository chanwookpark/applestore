package applestore.front.config;

import applestore.framework.workflow.ProcessEngine;
import applestore.framework.workflow.SimpleProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chanwook
 */
@Configuration
public class FlowConfig {
    @Autowired
    ApplicationContext context;

    @Bean
    public ProcessEngine processEngine() {
        final SimpleProcessEngine engine = new SimpleProcessEngine();
        engine.init(context);
        return engine;
    }
}
