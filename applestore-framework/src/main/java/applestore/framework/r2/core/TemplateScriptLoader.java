package applestore.framework.r2.core;

import javax.script.ScriptEngine;

/**
 * @author chanwook
 */
public interface TemplateScriptLoader {
    /**
     * 스크립트 파일 로딩
     * @param engine
     */
    void load(ScriptEngine engine);

}
