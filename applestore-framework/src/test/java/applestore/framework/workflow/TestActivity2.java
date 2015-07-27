package applestore.framework.workflow;

import org.springframework.stereotype.Component;

/**
 * @author chanwook
 */
@Component
public class TestActivity2 implements Activity {
    @Override
    public String getProcessId() {
        return "test";
    }

    @Override
    public void execute(RequestMap requestMap, ResponseMap responseMap) {
        responseMap.put("activity2", "2");
    }
}
