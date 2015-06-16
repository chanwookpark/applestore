package applestore.domain.catalog;

import applestore.domain.AppleStoreEntityTestApp;
import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.catalog.repository.ProductJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static applestore.domain.catalog.CatalogEntityDataTester.CATEGORY_IMAGE_URL_MAC;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppleStoreEntityTestApp.class)
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

        DisplayCategory ds = new DisplayCategory("mac", CATEGORY_IMAGE_URL_MAC);
        final Product p1 = new Product("mac001", "macbook-pro-2010-mid");
        final Product p2 = new Product("mac002", "macbook-pro-2010-mid");
        final Product p3 = new Product("mac003", "macbook-pro-2010-mid");
        final Product p4 = new Product("mac004", "macbook-pro-2010-mid");
        final Product p5 = new Product("mac005", "macbook-pro-2010-mid");
        final Product p6 = new Product("mac006", "macbook-pro-2010-mid");
        ds.addProduct(p1);
        ds.addProduct(p2);
        ds.addProduct(p3);
        ds.addProduct(p4);
        ds.addProduct(p5);
        ds.addProduct(p6);

        cr.saveAndFlush(ds);

        assertThat(pr.count(), is(6l));
        assertThat(cr.count(), is(1l));
/*
        ObjectMapper m = new ObjectMapper();
        List<Product> list = new ArrayList<Product>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);
        list.add(p6);

        ModelMap model = new ModelMap();
        model.put("category", ds);
        model.put("productList", new PageImpl<Product>(list));

        final String json = m.writeValueAsString(model);
        System.out.println(">>" + json);
*/
    }
}
