package applestore.domain;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.product.entity.*;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.catalog.repository.ProductIndexSolrRepository;
import applestore.domain.product.repository.ProductAttributeJpaRepository;
import applestore.domain.product.repository.ProductAttributeValueJpaRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.catalog.solr.ProductIndex;
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

    @Autowired
    ProductAttributeJpaRepository par;

    @Autowired
    ProductAttributeValueJpaRepository pavr;

    @Test
    public void createData() throws Exception {
        pr.deleteAll();
        cr.deleteAll();
        pavr.deleteAll();
        par.deleteAll();

        cr.flush();
        pr.flush();
        pavr.flush();
        par.flush();

        DisplayCategory ds1 = new DisplayCategory("mac", CATEGORY_IMAGE_URL_MAC);
        DisplayCategory ds2 = new DisplayCategory("iphone", CATEGORY_IMAGE_URL_IPHONE);
        DisplayCategory ds3 = new DisplayCategory("watch", CATEGORY_IMAGE_URL_WATCH);
        DisplayCategory ds4 = new DisplayCategory("pad", CATEGORY_IMAGE_URL_IPAD);

        // 우선 한 이미지로~~
        ds1.addProduct(new Product("M101", "MacBook Pro 2010 early")
                .addImage(new ProductImage(MACBOOK_IMAGE)));
        ds1.addProduct(new Product("M102", "MacBook Pro 2010 mid")
                .addImage(new ProductImage(MACBOOK_IMAGE)));
        ds1.addProduct(new Product("M103", "MacBook Pro 2011 mid")
                .addImage(new ProductImage(MACBOOK_IMAGE)));
        ds1.addProduct(new Product("M104", "MacBook Pro 2011 late")
                .addImage(new ProductImage(MACBOOK_IMAGE)));
        ds1.addProduct(new Product("M105", "MacBook Pro 2012 mid")
                .addImage(new ProductImage(MACBOOK_IMAGE)));
        ds1.addProduct(new Product("M106", "MacBook Pro 2013 early")
                .addImage(new ProductImage(MACBOOK_IMAGE)));
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

        // PV와 PAV 생성
        ProductAttribute attr1 = new ProductAttribute();
        attr1.setAttributeName("color");
        attr1.setLabel("색깔");

        ProductAttribute attr2 = new ProductAttribute();
        attr2.setAttributeName("size");
        attr2.setLabel("사이즈");

        // attr 생성
        par.save(attr1);
        par.save(attr2);
        par.flush();

        final ProductAttributeValue value1 = new ProductAttributeValue("red", "빨간색", attr1);
        final ProductAttributeValue value2 = new ProductAttributeValue("blue", "파랑색", attr1);
        final ProductAttributeValue value3 = new ProductAttributeValue("S", "Small", attr2);
        final ProductAttributeValue value4 = new ProductAttributeValue("M", "Medium", attr2);
        final ProductAttributeValue value5 = new ProductAttributeValue("L", "Large", attr2);

        // attr value 추가 (편하게 par로 저장)
        pavr.save(value1);
        pavr.save(value2);
        pavr.save(value3);
        pavr.save(value4);
        pavr.save(value5);
        pavr.flush();

    }
}
