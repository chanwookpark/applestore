package applestore.front.product;

import applestore.domain.product.entity.Product;

/**
 * @author chanwook
 */
public interface ProductService {
    Product getProduct(String productId);
}
