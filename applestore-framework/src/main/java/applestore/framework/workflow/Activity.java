package applestore.framework.workflow;

/**
 * @author chanwook
 */
public interface Activity {
    String getProcessId();

    void execute(RequestMap requestMap, ResponseMap responseMap);
}
