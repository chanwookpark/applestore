package applestore.front.product;

import applestore.domain.catalog.entity.Product;

/**
 * @author chanwook
 */
public interface ProductService {
    Product getProduct(String productId);
}
