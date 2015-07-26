package applestore.admin.product;

import applestore.admin.product.service.ProductManagementServiceImpl;
import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.ProductAttribute;
import applestore.domain.product.entity.ProductAttributeValue;
import applestore.domain.product.entity.Sku;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author chanwook
 */
public class ProductManagementServiceTests {

    @Test
    public void createSkuByPav() throws Exception {
        Product product = new Product();

        ProductAttribute attr1 = new ProductAttribute(1, "color", "색상");
        attr1.addValue(new ProductAttributeValue(1, "blue", "파랑"));
        attr1.addValue(new ProductAttributeValue(2, "red", "빨강"));

        ProductAttribute attr2 = new ProductAttribute(2, "size", "크기");
        attr2.addValue(new ProductAttributeValue(3, "L", "L(100)"));
        attr2.addValue(new ProductAttributeValue(4, "XL", "XL(105)"));
        attr2.addValue(new ProductAttributeValue(5, "XXL", "XXL(110)"));

        product.addProductAttribute(attr1);
        product.addProductAttribute(attr2);

        final ProductManagementServiceImpl service = new ProductManagementServiceImpl();

        final List<Sku> list = service.createNewSkuList(product);

        assertThat(list.size(), is(6));

        assertThat(list.get(0).getAttributeValueList().get(0).getValueId(), is(1L));
        assertThat(list.get(0).getAttributeValueList().get(1).getValueId(), is(3L));
        assertThat(list.get(1).getAttributeValueList().get(0).getValueId(), is(1L));
        assertThat(list.get(1).getAttributeValueList().get(1).getValueId(), is(4L));
        assertThat(list.get(2).getAttributeValueList().get(0).getValueId(), is(1L));
        assertThat(list.get(2).getAttributeValueList().get(1).getValueId(), is(5L));

        assertThat(list.get(3).getAttributeValueList().get(0).getValueId(), is(2L));
        assertThat(list.get(3).getAttributeValueList().get(1).getValueId(), is(3L));
        assertThat(list.get(4).getAttributeValueList().get(0).getValueId(), is(2L));
        assertThat(list.get(4).getAttributeValueList().get(1).getValueId(), is(4L));
        assertThat(list.get(5).getAttributeValueList().get(0).getValueId(), is(2L));
        assertThat(list.get(5).getAttributeValueList().get(1).getValueId(), is(5L));

    }

    @Test
    public void createSkuByPav2() throws Exception {
        Product product = new Product();

        ProductAttribute attr1 = new ProductAttribute(1, "color", "색상");
        attr1.addValue(new ProductAttributeValue(1, "blue", "파랑"));
        attr1.addValue(new ProductAttributeValue(2, "red", "빨강"));

        ProductAttribute attr2 = new ProductAttribute(2, "size", "크기");
        attr2.addValue(new ProductAttributeValue(3, "L", "L(100)"));
        attr2.addValue(new ProductAttributeValue(4, "XL", "XL(105)"));
        attr2.addValue(new ProductAttributeValue(5, "XXL", "XXL(110)"));

        ProductAttribute attr3 = new ProductAttribute(3, "mac", "제조사");
        attr3.addValue(new ProductAttributeValue(6, "A", "A회사"));
        attr3.addValue(new ProductAttributeValue(7, "B", "B회사"));

        product.addProductAttribute(attr1);
        product.addProductAttribute(attr2);
        product.addProductAttribute(attr3);

        final ProductManagementServiceImpl service = new ProductManagementServiceImpl();

        final List<Sku> list = service.createNewSkuList(product);

        assertThat(list.size(), is(12));
    }
}
