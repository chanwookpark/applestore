package applestore.domain;

import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.ProductAttribute;
import applestore.domain.product.entity.ProductAttributeValue;
import applestore.domain.product.entity.Sku;
import applestore.domain.product.repository.ProductAttributeJpaRepository;
import applestore.domain.product.repository.ProductAttributeValueJpaRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.product.repository.SkuJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author chanwook
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppleStoreEntityTestApp.class)
@Transactional
public class ProductEntityTests {

    @Autowired
    ProductJpaRepository pr;

    @Autowired
    ProductAttributeJpaRepository par;

    @Autowired
    ProductAttributeValueJpaRepository pavr;

    @Autowired
    SkuJpaRepository sr;

    @Test
    public void entityWithDb() throws Exception {
        // PA와 PAV 생성
        ProductAttribute attr1 = new ProductAttribute();
        attr1.setAttributeName("color-xxx");
        attr1.setDisplayOrder(999999);
        attr1.setLabel("색깔");

        ProductAttribute attr2 = new ProductAttribute();
        attr2.setAttributeName("size-xxx");
        attr2.setLabel("사이즈");
        attr2.setDisplayOrder(999998);

        // attr 생성
        par.save(attr1);
        par.save(attr2);

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

        Product product = new Product("test001", "테스트 상품");
        product.addProductAttribute(attr1);
        product.addProductAttribute(attr2);

        // product에 2개 attr 추가
        pr.save(product);
        pr.flush();

        // Sku 생성 (우선 선택적으로 만들어보자)
        Sku sku1 = new Sku("sku1", 100, 50, 10, value1);
        Sku sku2 = new Sku("sku2", 200, 500, 30, value2);
        Sku sku3 = new Sku("sku3", 200, 500, 30, value3);

        sr.save(sku1);
        sr.save(sku2);
        sr.save(sku3);

        product.addSku(sku1);
        product.addSku(sku2);
        product.addSku(sku3);
        pr.save(product); // sku 생성 및 product 관계 설정

        final Product prdInDb = pr.findOne(product.getProductId());

        assertThat(prdInDb, notNullValue());
        assertThat(prdInDb.getSkuList().size(), is(3));
        assertThat(prdInDb.getAttributeList().size(), is(2));
        final Iterator<ProductAttribute> iterator = prdInDb.getAttributeList().iterator();
        assertThat(iterator.next().getAttrValueList().size(), is(2));
        assertThat(iterator.next().getAttrValueList().size(), is(3));
        assertThat(prdInDb.getSkuList().get(0).getAttributeValue().getValueId(), is(value1.getValueId()));
        assertThat(prdInDb.getSkuList().get(1).getAttributeValue().getValueId(), is(value2.getValueId()));
        assertThat(prdInDb.getSkuList().get(2).getAttributeValue().getValueId(), is(value3.getValueId()));
    }
}
