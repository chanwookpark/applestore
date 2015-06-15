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

    public static final String COMPILED_HTML = "(function(dust){dust.register(\"key1\",body_0);function body_0(chk,ctx){return chk.w(\"<h1>Hello!</h1><span>\").f(ctx.get([\"name\"], false),ctx,\"h\").w(\"</span>\");}body_0.__dustBody=!0;return body_0}(dust));";
    public static final String RENDER_HTML = "<h1>Hello!</h1><span>chanwook</span>";

    @Test
    public void start() throws Exception {
        RenderingEngineFactory ref = new RenderingEngineFactory();
        RenderingEngine re = ref.getObject();

        assertThat(re, is(notNullValue()));

        final String key = "key1";
        String template = re.compile(key, "<h1>Hello!</h1><span>{name}</span>");
        assertThat(template, is(COMPILED_HTML));
        re.load(template);
        final String rhtml = re.render(key, "{\"name\":\"chanwook\"}");
        assertThat(rhtml, is(RENDER_HTML));
    }
}
