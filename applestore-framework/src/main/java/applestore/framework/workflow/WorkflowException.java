package applestore.framework.workflow;

/**
 * @author chanwook
 */
public class WorkflowException extends RuntimeException {
    public WorkflowException(String message) {
        super(message);
    }

    public WorkflowException(String message, Throwable caused) {
        super(message, caused);
    }
}
