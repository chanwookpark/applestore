package applestore.front.catalog;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.catalog.repository.ProductJpaRepository;
import applestore.front.AppleStoreFrontApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppleStoreFrontApp.class)
@Transactional
public class CatalogEntityTests {

    @Autowired
    ProductJpaRepository pr;

    @Autowired
    DisplayCategoryJpaRepository cr;

    @Test
    public void create() throws Exception {
        pr.deleteAll();
        cr.deleteAll();

        DisplayCategory ds = new DisplayCategory("mac", CatalogServiceTester.CATEGORY_IMAGE_URL_MAC);
        ds.addProduct(new Product("mac001", "macbook-pro-2010-mid"));
        ds.addProduct(new Product("mac002", "macbook-pro-2010-mid"));
        ds.addProduct(new Product("mac003", "macbook-pro-2010-mid"));
        ds.addProduct(new Product("mac004", "macbook-pro-2010-mid"));
        ds.addProduct(new Product("mac005", "macbook-pro-2010-mid"));
        ds.addProduct(new Product("mac006", "macbook-pro-2010-mid"));

        cr.saveAndFlush(ds);

        assertThat(pr.count(), is(6l));
        assertThat(cr.count(), is(1l));
    }
}
