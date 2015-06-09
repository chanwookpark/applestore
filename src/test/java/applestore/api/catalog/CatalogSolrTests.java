package applestore.api.catalog;

import applestore.api.AppleStoreApiApp;
import applestore.api.catalog.model.solr.CategoryProduct;
import applestore.api.catalog.repository.solr.CategoryProductSolrCustom;
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
@SpringApplicationConfiguration(classes = AppleStoreApiApp.class)
@Transactional
public class CatalogSolrTests {

    @Autowired
    CategoryProductSolrCustom cr;

    @Autowired
    SolrTemplate template;

    @Test
    public void findCategorizedProduct() throws Exception {
        template.saveBean(new CategoryProduct("1", "M101", 1));
        template.saveBean(new CategoryProduct("2", "M102", 1));
        template.saveBean(new CategoryProduct("3", "M103", 1));
        template.saveBean(new CategoryProduct("4", "M104", 1));
        template.saveBean(new CategoryProduct("5", "M105", 1));
        template.saveBean(new CategoryProduct("6", "P101", 2));
        template.saveBean(new CategoryProduct("7", "W101", 3));
        template.commit();

        Page<CategoryProduct> page = cr.findByCategoryId(1, new PageRequest(0, 3));
        assertThat(page.getSize(), is(3));
        assertThat(page.getContent().get(0).getId(), is("1"));
        assertThat(page.getContent().get(1).getId(), is("2"));
        assertThat(page.getContent().get(2).getId(), is("3"));
    }

    @Test
    public void sandbox() throws Exception {
//        cr.deleteAll();
//
//        cr.save(new CategoryProduct("1", "mac001", 1));
//        cr.save(new CategoryProduct("2", "mac002", 1));
//        cr.save(new CategoryProduct("3", "mac003", 1));
//        cr.save(new CategoryProduct("4", "mac004", 2));
//        cr.save(new CategoryProduct("5", "mac005", 3));
//
//        for (CategoryProduct cp : cr.findAll()) {
//            System.out.println("CP: " + cp);
//        }

//        template.getSolrServer().addBean(new CategoryProduct("1", "mac001", 1l));
        template.saveBean(new CategoryProduct("1", "mac001", 1l));
        template.commit();

//        Criteria criteria = new Criteria("productId_s").contains("mac");
        Criteria criteria = new Criteria("categoryId_l").is(1);
        SimpleQuery query = new SimpleQuery(criteria);
        query.setOffset(0);
        query.setRows(10);
//        query.setOffset(1);
//        query.setRows(10);

        CategoryProduct cp = template.queryForObject(query, CategoryProduct.class);
        System.out.println(cp.getId());
    }
}
