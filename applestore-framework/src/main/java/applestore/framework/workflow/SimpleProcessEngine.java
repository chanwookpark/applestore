package applestore.framework.workflow;

import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chanwook
 */
public class SimpleProcessEngine implements ProcessEngine {

    private Map<String, List<Activity>> activities = new ConcurrentHashMap<String, List<Activity>>();

    @Override
    public void addActivity(Activity activity) {
        String processId = activity.getProcessId();

        if (!activities.containsKey(processId)) {
            ArrayList<Activity> list = new ArrayList<Activity>();
            activities.put(processId, list);
        }
        activities.get(processId).add(activity);
    }

    public Map<String, List<Activity>> getActivities() {
        return activities;
    }

    @Override
    public ResponseMap process(String processId, RequestMap requestMap) {
        if (!activities.containsKey(processId)) {
            throw new WorkflowException("Not found PID! (" + processId + ")");
        }

        final List<Activity> activityList = this.activities.get(processId);

        ProcessingContext context = new ProcessingContext(processId, requestMap, new ResponseMap(), activityList);
        try {
            for (Activity activity : activityList) {
                context.executeAt(activity);

                activity.execute(context.getRequestMap(), context.getResponseMap());
            }
        } catch (Throwable t) {
            throw new WorkflowException("Exception thrown when executing process!(activity: " + context.getCurrentStep(), t);
        }

        return context.getResponseMap();
    }

    @Override
    public void init(ApplicationContext context) {
        final Map<String, Activity> beans = context.getBeansOfType(Activity.class);

        for (Map.Entry<String, Activity> e : beans.entrySet()) {
            this.addActivity(e.getValue());
        }
    }
}
