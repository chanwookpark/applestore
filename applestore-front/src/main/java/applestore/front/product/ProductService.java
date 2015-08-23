package applestore.front.product;

import applestore.domain.product.entity.Product;

import java.util.List;

/**
 * @author chanwook
 */
public interface ProductService {
    Product getProduct(String productId);

    void checkProductQuantity(List<Long> skuIdList, List<Integer> orderQuantities);
}
