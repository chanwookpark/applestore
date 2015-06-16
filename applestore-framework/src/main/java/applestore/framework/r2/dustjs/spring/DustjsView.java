package applestore.framework.r2.dustjs.spring;

import applestore.framework.r2.R2Exception;
import applestore.framework.r2.dustjs.core.RenderingEngine;
import applestore.framework.r2.dustjs.core.RenderingEngineFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.view.InternalResourceView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {@link org.springframework.web.servlet.view.JstlView}를 확장해 Dust.js로 렌더링하는 HTML을
 * {@link org.springframework.web.servlet.ModelAndView}로 저장해주는 역할 수행
 *
 * </pre>
 *
 * @author chanwook
 */
public class DustjsView extends InternalResourceView { //FIXME AbstractView로 해야할까..

    private String viewHtmlKey = "_view_html";
    private String jsonDataKey = "_view_data";
    private String templateHtmlKey = "_view_template";

    //TODO 개선요
    private RenderingEngine renderingEngine = new RenderingEngineFactory().getObject();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected Map<String, Object> createMergedOutputModel(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        final Map<String, Object> mergedOutputModel = super.createMergedOutputModel(model, request, response);

        DustRenderModel dustRenderModel = (DustRenderModel) model.get("dustmodel");

        final String templateKey = getUrl();
        final String template = getTemplate(templateKey);
        final String compiled = renderingEngine.compile(templateKey, template);
        renderingEngine.load(compiled);
        final String json = toJson(dustRenderModel);
        final String view = renderingEngine.render(templateKey, json);

        mergedOutputModel.put(templateHtmlKey, compiled);
        mergedOutputModel.put(jsonDataKey, json);
        mergedOutputModel.put(viewHtmlKey, view);

        return mergedOutputModel;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //TODO jacksonview 참조해 createTemporaryOutputStream() 재정의 해야하는 케이스 구현 (for IE)
        OutputStream stream = response.getOutputStream();
        StreamUtils.copy((String) model.get(viewHtmlKey), getCharset(), stream);
    }

    protected Charset getCharset() {
        //TODO 속성으로..
        return Charset.forName("UTF-8");
    }

    @Override
    protected void prepareResponse(HttpServletRequest request, HttpServletResponse response) {
        setResponseContentType(request, response);

        //TODO 구현 jacksonview 참조
        super.prepareResponse(request, response);

    }

    private String toJson(DustRenderModel renderModel) {
        try {
            return objectMapper.writeValueAsString(renderModel);
        } catch (JsonProcessingException e) {
            throw new R2Exception("데이터 모델 변환 중 에러가 발생했습니다.", e);
        }
    }

    protected String getTemplate(String templatePath) {
        //TODO 개선요..
        try {
            String template = new String(Files.readAllBytes(Paths.get(new ClassPathResource(templatePath).getURI())));
            return template;
        } catch (IOException e) {
            throw new R2Exception("템플릿 파일 로딩 중 에러가 발생했습니다.", e);
        }
    }


    public static class DustRenderModel extends HashMap<String, Object> {

    }

    public void setRenderingEngine(RenderingEngine renderingEngine) {
        this.renderingEngine = renderingEngine;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setJsonDataKey(String jsonDataKey) {
        this.jsonDataKey = jsonDataKey;
    }

    public void setViewHtmlKey(String viewHtmlKey) {
        this.viewHtmlKey = viewHtmlKey;
    }

    public void setTemplateHtmlKey(String templateHtmlKey) {
        this.templateHtmlKey = templateHtmlKey;
    }
}
