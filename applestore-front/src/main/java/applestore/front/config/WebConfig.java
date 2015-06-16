package applestore.front.config;

import applestore.framework.r2.dustjs.spring.DustjsView;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author chanwook
 */
@Configuration
@EnableSpringDataWebSupport
public class WebConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Override
    public InternalResourceViewResolver defaultViewResolver() {
        //TODO 개선 필요.. 우선 동작하게..
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        viewResolver.setViewClass(DustjsView.class);

        return viewResolver;
    }

}
