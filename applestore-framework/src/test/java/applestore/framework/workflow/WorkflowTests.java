package applestore.framework.workflow;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author chanwook
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WorkflowTestConfig.class)
public class WorkflowTests {

    @Test
    public void config() throws Exception {
        SimpleProcessEngine engine = new SimpleProcessEngine();
        engine.addActivity(new TestActivity1());
        engine.addActivity(new TestActivity2());

        assertThat(engine.getActivities().size(), is(1));
        assertThat(engine.getActivities().get("test").size(), is(2));
    }

    @Test
    public void execute() throws Exception {
        ProcessEngine engine = new SimpleProcessEngine();
        engine.addActivity(new TestActivity1());
        engine.addActivity(new TestActivity2());

        final ResponseMap response = engine.process("test", new RequestMap());

        assertionToExecute(response);
    }


    @Autowired
    ProcessEngine engineBean;

    @Test
    public void withSpring() throws Exception {
        final ResponseMap response = engineBean.process("test", new RequestMap());
        assertionToExecute(response);
    }

    private void assertionToExecute(ResponseMap response) {
        assertThat(response.size(), is(2));
        assertThat(response.get("activity1"), CoreMatchers.<Object>is("1"));
        assertThat(response.get("activity2"), CoreMatchers.<Object>is("2"));
    }
}
