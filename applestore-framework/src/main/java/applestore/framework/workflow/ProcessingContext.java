package applestore.framework.workflow;

import java.util.List;

/**
 * @author chanwook
 */
public class ProcessingContext {
    private final String processId;
    private final RequestMap requestMap;
    private final List<Activity> activityList;
    private final ResponseMap responseMap;
    private Activity currentStep;

    public ProcessingContext(String processId, RequestMap requestMap, ResponseMap responseMap, List<Activity> activityList) {
        this.processId = processId;
        this.requestMap = requestMap;
        this.activityList = activityList;
        this.responseMap = responseMap;
    }

    public void executeAt(Activity activity) {
        this.currentStep = activity;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public Activity getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Activity currentStep) {
        this.currentStep = currentStep;
    }

    public String getProcessId() {
        return processId;
    }

    public RequestMap getRequestMap() {
        return requestMap;
    }

    public ResponseMap getResponseMap() {
        return responseMap;
    }
}
