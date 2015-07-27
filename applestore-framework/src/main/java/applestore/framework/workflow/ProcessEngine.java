package applestore.framework.workflow;

import org.springframework.context.ApplicationContext;

/**
 * @author chanwook
 */
public interface ProcessEngine {
    void addActivity(Activity activity);

    ResponseMap process(String groupName, RequestMap requestMap);

    void init(ApplicationContext context);
}
