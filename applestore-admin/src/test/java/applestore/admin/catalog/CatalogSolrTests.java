package applestore.admin.catalog;

import applestore.admin.AppleStoreAdminApp;
import applestore.domain.catalog.repository.ProductIndexSolrRepository;
import applestore.domain.catalog.solr.ProductIndex;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppleStoreAdminApp.class)
@Transactional
public class CatalogSolrTests {

    @Autowired
    ProductIndexSolrRepository sr;

    @Autowired
    SolrTemplate template;

    @Ignore("ID 자동생성이 되지 않아서 일단 무시..")
    @Test
    public void generateId() throws Exception {
        final ProductIndex cp = new ProductIndex("???", "XXX", 100);
        final UpdateResponse response = template.saveBean(cp);
        System.out.println("id: " + response.getResponse().get("id"));
        template.commit();
    }

    @Test
    public void findCategorizedProduct() throws Exception {
        template.saveBean(new ProductIndex("100", "M101", 1000));
        template.saveBean(new ProductIndex("200", "M102", 1000));
        template.saveBean(new ProductIndex("300", "M103", 1000));
        template.saveBean(new ProductIndex("400", "M104", 1000));
        template.saveBean(new ProductIndex("500", "M105", 1000));
        template.saveBean(new ProductIndex("600", "P101", 1002));
        template.saveBean(new ProductIndex("700", "W101", 1003));
        template.commit();

        Page<ProductIndex> page = sr.findByCategoryId(1000, new PageRequest(0, 3));
        assertThat(page.getSize(), is(3));
        assertThat(page.getContent().get(0).getId(), is("100"));
        assertThat(page.getContent().get(1).getId(), is("200"));
        assertThat(page.getContent().get(2).getId(), is("300"));
    }

    @Test
    public void sandbox() throws Exception {
//        sr.deleteAll();
//
//        sr.save(new CategoryProduct("1", "mac001", 1));
//        sr.save(new CategoryProduct("2", "mac002", 1));
//        sr.save(new CategoryProduct("3", "mac003", 1));
//        sr.save(new CategoryProduct("4", "mac004", 2));
//        sr.save(new CategoryProduct("5", "mac005", 3));
//
//        for (CategoryProduct cp : sr.findAll()) {
//            System.out.println("CP: " + cp);
//        }

//        template.getSolrServer().addBean(new CategoryProduct("1", "mac001", 1l));
        template.saveBean(new ProductIndex("1", "mac001", 1l));
        template.commit();

//        Criteria criteria = new Criteria("productId_s").contains("mac");
        Criteria criteria = new Criteria("categoryId_l").is(1);
        SimpleQuery query = new SimpleQuery(criteria);
        query.setOffset(0);
        query.setRows(10);
//        query.setOffset(1);
//        query.setRows(10);

        ProductIndex cp = template.queryForObject(query, ProductIndex.class);
        System.out.println(cp.getId());
    }
}
