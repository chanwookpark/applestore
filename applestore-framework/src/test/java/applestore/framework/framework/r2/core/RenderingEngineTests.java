package applestore.framework.framework.r2.core;

import applestore.framework.r2.core.RenderingEngine;
import applestore.framework.r2.core.RenderingEngineFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author chanwook
 */
public class RenderingEngineTests {

    @Test
    public void start() throws Exception {
        RenderingEngineFactory ref = new RenderingEngineFactory();
        RenderingEngine re = ref.getObject();

        assertThat(re, is(notNullValue()));

    }
}
