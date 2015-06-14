package applestore.framework.r2.core;

import javax.script.ScriptEngine;

/**
 * @author chanwook
 */
public class RenderingEngine {
    private final ScriptEngine scriptEngine;

    public RenderingEngine(ScriptEngine engine) {
        this.scriptEngine = engine;
    }
}
