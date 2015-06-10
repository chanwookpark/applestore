package applestore.api.catalog;

import applestore.api.AppleStoreApiApp;
import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;
import applestore.api.catalog.repository.jpa.DisplayCategoryJpaRepository;
import applestore.api.catalog.repository.jpa.ProductJpaRepository;
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
@SpringApplicationConfiguration(classes = AppleStoreApiApp.class)
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

        DisplayCategory ds = new DisplayCategory("mac");
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
