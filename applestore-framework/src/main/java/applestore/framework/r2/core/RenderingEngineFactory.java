package applestore.framework.r2.core;

import applestore.framework.r2.R2Exception;
import applestore.framework.supports.dustjs.DustViewScriptLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;

/**
 * @author chanwook
 */
public class RenderingEngineFactory {
    private final Logger logger = LoggerFactory.getLogger(RenderingEngineFactory.class);

    private TemplateScriptLoader scriptLoader = new DustViewScriptLoader();

    public RenderingEngine getObject() {
        try {
            final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

            loadScript(engine);

            if (logger.isInfoEnabled()) {
                logger.info("스크립트 엔진 초기화 완료");
                engine.eval("print('스크립트 엔진 로딩...');");
            }

            return new RenderingEngine(engine);
        } catch (Throwable e) {
            throw new R2Exception("스크립트 엔진 초기화 중 에러가 발생했습니다.", e);
        }
    }

    protected void loadScript(ScriptEngine engine) throws FileNotFoundException, ScriptException {
        scriptLoader.load(engine);
    }
}
