package applestore.domain.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Configuration
@EnableSolrRepositories(basePackages = "applestore",
        repositoryImplementationPostfix = "Impl",
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "applestore..*SolrRepository"))
public class SolrConfig {

    @Autowired
    Environment env;

    @Bean
    public SolrServer solrServer() {
        String host = env.getRequiredProperty("spring.data.solr.host");
        return new HttpSolrServer(host);
    }

    /*
    @Bean
    public SolrServer solrServer() throws Exception {
        EmbeddedSolrServerFactory factory =
                new EmbeddedSolrServerFactory("classpath:/solr");
        return factory.getSolrServer();
    }
    */

    @Bean
    public SolrTemplate solrTemplate() throws Exception {
        return new SolrTemplate(solrServer());
    }
}
