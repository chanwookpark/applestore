package applestore.framework.supports.dustjs;

import applestore.framework.r2.R2Exception;
import applestore.framework.r2.core.TemplateScriptLoader;

import javax.script.ScriptEngine;
import java.io.FileReader;

/**
 * https://github.com/linkedin/dustjs 지원 버전
 *
 * @author chanwook
 */
public class DustViewScriptLoader implements TemplateScriptLoader {
    @Override
    public void load(ScriptEngine engine) {
        try {
            engine.eval(new FileReader("/r2/dustjs/dust-full-2.7.2.js"));
        } catch (Throwable e) {
            new R2Exception("스크립트 파일 로딩 중 에러가 발생했습니다", e);
        }
    }
}
