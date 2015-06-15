package applestore.framework.r2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import java.io.StringWriter;

/**
 * @author chanwook
 */
public class RenderingEngine {
    private final Logger logger = LoggerFactory.getLogger(RenderingEngine.class);

    private ScriptEngine scriptEngine;
    private String encoding = "UTF-8";

    public RenderingEngine(ScriptEngine engine) {
        this.scriptEngine = engine;
    }

    public String compile(String key, String html) {
        String compiled = "";
        try {
            final Object result = ((Invocable) scriptEngine).invokeFunction("dustCompile", key, html);
            if (logger.isDebugEnabled()) {
                logger.debug("템플릿 컴파일[key: " + key + "]\n컴파일전: " + html +
                        "컴파일후:" + result);
            }
            compiled = (String) result;
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("R2 렌더링 진행중 에러가 발생했습니다 (Compile 단계)", e);
            }
        }
        return compiled;
    }

    public void load(String template) {
        try {
            ((Invocable) scriptEngine).invokeFunction("dustLoad", template);
            if (logger.isDebugEnabled()) {
                logger.debug("템플릿 로딩[Template : " + template + "]");
            }
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("R2 렌더링 진행중 에러가 발생했습니다 (Load 단계)", e);
            }
        }
    }

    public String render(String key, String data) {
        String view = "";
        try {
            StringWriter successWriter = new StringWriter();
            StringWriter errorWriter = new StringWriter();
            if (logger.isDebugEnabled()) {
                logger.debug("최종 렌더링 요청[key: " + key + ", data: " + data + "]");
            }
            ((Invocable) scriptEngine).invokeFunction("dustRender", key, data, successWriter, errorWriter);

            view = new String(successWriter.getBuffer().toString().getBytes(encoding), encoding);
            if (logger.isDebugEnabled()) {
                logger.debug("최종 렌더링 HTML>> " + view);
            }
        } catch (Throwable e) {
            if (logger.isErrorEnabled()) {
                logger.error("R2 렌더링 진행중 에러가 발생했습니다 (Rendering 단계)", e);
            }
        }
        return view;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setScriptEngine(ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
    }
}
