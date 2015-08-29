package applestore.domain;

import applestore.domain.product.entity.Product;
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

/**
 * @author chanwook
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EntityTestApp.class)
@Transactional
public class EntityEqualityTest {

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
        final Product p1 = pr.findOne("M101");

        assert p1.getSkuList().size() == 6;

        // SKU를 추가하려 했지만 Biz key가 동일하기에 추가 불가..
        final Sku currentSku = p1.getSkuList().iterator().next();
        final Sku newSku = new Sku();
        newSku.setProduct(p1);
        newSku.setAttributeValueList(currentSku.getAttributeValueList());
        newSku.setSkuName("신상옵션!");
        p1.addSku(newSku);

        assert currentSku.equals(newSku);
        assert p1.getSkuList().size() == 6;
    }
}
