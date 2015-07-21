package applestore.domain;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.ProductImage;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.catalog.repository.ProductIndexSolrRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.catalog.solr.ProductIndex;
import applestore.domain.product.entity.Sku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppleStoreEntityTestApp.class)
//@Transactional
public class CatalogEntityDataTester {

    public static final String MACBOOK_IMAGE = "http://store.storeimages.cdn-apple.com/4598/as-images.apple.com/is/image/AppleInc/aos/published/images/m/ac/macbook/select/macbook-select-gold-201501";

    public static final String CATEGORY_IMAGE_URL_MAC = "http://store.storeimages.cdn-apple.com/4599/store.apple.com/rs-web/rel/assets/as-toolkit/lateralnav/icons/mac.svg";
    public static final String CATEGORY_IMAGE_URL_IPHONE = "http://store.storeimages.cdn-apple.com/4599/store.apple.com/rs-web/rel/assets/as-toolkit/lateralnav/icons/iphone.svg";
    public static final String CATEGORY_IMAGE_URL_WATCH = "http://store.storeimages.cdn-apple.com/4599/store.apple.com/rs-web/rel/assets/as-toolkit/lateralnav/icons/watch.svg";
    public static final String CATEGORY_IMAGE_URL_IPAD = "http://store.storeimages.cdn-apple.com/4599/store.apple.com/rs-web/rel/assets/as-toolkit/lateralnav/icons/ipad.svg";

    @Autowired
    ProductJpaRepository pr;

    @Autowired
    DisplayCategoryJpaRepository cr;

    @Autowired
    ProductIndexSolrRepository sr;

    @Test
    public void createData() throws Exception {
        pr.deleteAll();
        cr.deleteAll();

        cr.flush();
        pr.flush();

        DisplayCategory ds1 = new DisplayCategory("mac", CATEGORY_IMAGE_URL_MAC);
        DisplayCategory ds2 = new DisplayCategory("iphone", CATEGORY_IMAGE_URL_IPHONE);
        DisplayCategory ds3 = new DisplayCategory("watch", CATEGORY_IMAGE_URL_WATCH);
        DisplayCategory ds4 = new DisplayCategory("pad", CATEGORY_IMAGE_URL_IPAD);

        // 우선 한 이미지로~~
        ds1.addProduct(new Product("M101", "MacBook Pro 2010 early")
                .addImage(new ProductImage(MACBOOK_IMAGE))
                .addSku(new Sku("Gold model", 1000, 100))
                .addSku(new Sku("Silver model", 1000, 200)));
        ds1.addProduct(new Product("M102", "MacBook Pro 2010 mid")
                .addImage(new ProductImage(MACBOOK_IMAGE))
                .addSku(new Sku("Gold model", 2000, 100))
                .addSku(new Sku("Silver model", 2000, 200)));
        ds1.addProduct(new Product("M103", "MacBook Pro 2011 mid")
                .addImage(new ProductImage(MACBOOK_IMAGE))
                .addSku(new Sku("Gold model", 3000, 100))
                .addSku(new Sku("Silver model", 3000, 200)));
        ds1.addProduct(new Product("M104", "MacBook Pro 2011 late")
                .addImage(new ProductImage(MACBOOK_IMAGE))
                .addSku(new Sku("Gold model", 4000, 100))
                .addSku(new Sku("Silver model", 4000, 200)));
        ds1.addProduct(new Product("M105", "MacBook Pro 2012 mid")
                .addImage(new ProductImage(MACBOOK_IMAGE))
                .addSku(new Sku("Gold model", 5000, 100))
                .addSku(new Sku("Silver model", 5000, 200)));
        ds1.addProduct(new Product("M106", "MacBook Pro 2013 early")
                .addImage(new ProductImage(MACBOOK_IMAGE))
                .addSku(new Sku("Gold model", 6000, 100))
                .addSku(new Sku("Silver model", 6000, 200)));
        cr.saveAndFlush(ds1);

        ds2.addProduct(new Product("P101", "iPhone 3"));
        ds2.addProduct(new Product("P102", "iPhone 3S"));
        ds2.addProduct(new Product("P103", "iPhone 4"));
        ds2.addProduct(new Product("P104", "iPhone 4S"));
        ds2.addProduct(new Product("P105", "iPhone 5"));
        ds2.addProduct(new Product("P106", "iPhone 5S"));
        ds2.addProduct(new Product("P107", "iPhone 6"));
        ds2.addProduct(new Product("P108", "iPhone 6 PLUS"));
        cr.saveAndFlush(ds2);

        ds3.addProduct(new Product("W101", "Watch Sports"));
        ds3.addProduct(new Product("W102", "Watch"));
        ds3.addProduct(new Product("W103", "Watch Edition"));
        cr.saveAndFlush(ds3);

        cr.saveAndFlush(ds4);

        assertThat(pr.count(), is(17l));
        assertThat(cr.count(), is(4l));

        sr.deleteAll();

        int index = 0;
        for (Product p : pr.findAll()) {
            ProductIndex cp = new ProductIndex(String.valueOf(System.nanoTime()), p.getProductId(), p.getDisplayCategory().getCategoryId());
            sr.save(cp);
            ++index;
            System.out.println("[데이터 생성]" + cp);
        }

        System.out.println(">> 인덱스 생성: " + index + "개의 인덱스를 생성했습니다.");
    }
}
