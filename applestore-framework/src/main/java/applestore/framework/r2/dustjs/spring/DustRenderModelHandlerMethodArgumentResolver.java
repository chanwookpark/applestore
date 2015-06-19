package applestore.framework.r2.dustjs.spring;

import applestore.framework.r2.dustjs.spring.DustModel;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static applestore.framework.r2.dustjs.spring.DustModel.*;

/**
 * @author chanwook
 */
public class DustRenderModelHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(DustModel.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final DustModel dm = new DustModel();
        mavContainer.addAttribute(MODEL_KEY, dm);
        return dm;
    }
}
