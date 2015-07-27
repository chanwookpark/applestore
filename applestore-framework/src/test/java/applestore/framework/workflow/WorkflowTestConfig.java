package applestore.framework.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chanwook
 */
@Configuration
@ComponentScan
public class WorkflowTestConfig {

    @Autowired
    ApplicationContext context;

    @Bean
    public ProcessEngine processEngine() {
        final SimpleProcessEngine engine = new SimpleProcessEngine();
        engine.init(context);
        return engine;
    }
}
