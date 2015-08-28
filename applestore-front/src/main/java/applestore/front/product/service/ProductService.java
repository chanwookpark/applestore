package applestore.front.product.service;

import applestore.domain.product.entity.Product;

import java.util.List;

/**
 * @author chanwook
 */
public interface ProductService {
    Product getProduct(String productId);

    void checkProductQuantity(List<Long> skuIdList, List<Integer> orderQuantities);
}
