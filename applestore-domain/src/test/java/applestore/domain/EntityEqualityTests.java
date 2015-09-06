package applestore.domain;

import applestore.domain.product.SkuException;
import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.ProductAttribute;
import applestore.domain.product.entity.ProductAttributeValue;
import applestore.domain.product.entity.Sku;
import applestore.domain.product.repository.ProductJpaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.fail;

/**
 * @author chanwook
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EntityTestApp.class)
@Transactional
public class EntityEqualityTests {

    @Autowired
    ProductJpaRepository pr;

    @Test
    public void case1() throws Exception {

        final Product p1 = pr.findOne("M101");
        final Product p2 = pr.findOne("M102");
        final Product p3 = pr.findOne("M103");

        Set<Product> set = new HashSet<Product>();
        set.add(p1);
        set.add(p2);
        set.add(p3);

        assert set.size() == 3;

        // case 1.
        set.add(p1);
        set.add(p2);
        set.add(p3);

        assert set.size() == 3;
    }

    @Test
    public void case2() throws Exception {
        // managed
        final Product p1 = pr.findOne("M101");

        // detached
        pr.flush();

        // managed by merge
        final Product l1 = pr.save(p1);

        assert l1.equals(p1);
    }

    @Test
    public void case3() throws Exception {
        // set member
        Product product = new Product();
        product.setProductId("test101");
        product.setProductName("비싼상품!");
        final ProductAttribute pa1 = new ProductAttribute("size0", "size0");
        final ProductAttribute pa2 = new ProductAttribute("color0", "color0");
        product.addProductAttribute(pa1);
        product.addProductAttribute(pa2);
        product.addSku(new Sku("sku1", new ProductAttributeValue("s", "size-s", pa1), new ProductAttributeValue("red", "color-red", pa2)));
        product.addSku(new Sku("sku2", new ProductAttributeValue("m", "size-m", pa1), new ProductAttributeValue("red", "color-red", pa2)));
        product.addSku(new Sku("sku3", new ProductAttributeValue("l", "size-l", pa1), new ProductAttributeValue("red", "color-red", pa2)));
        final Product p1 = pr.save(product);

        assert p1.getSkuList().size() == 3;

        // SKU를 추가하려 했지만 Biz key가 동일하기에 추가 불가..
        final Sku currentSku = p1.getSkuList().iterator().next();
        final Sku newSku = new Sku();
        newSku.setProduct(p1);
        newSku.setAttributeValueList(currentSku.getAttributeValueList());
        newSku.setSkuName("신상옵션!");
        try {
            p1.addSku(newSku);
            fail();
        } catch (SkuException se) {
            // OK!
        }

        assert currentSku.equals(newSku);
        assert p1.getSkuList().size() == 3; // sku는 그대로!s
    }
}
