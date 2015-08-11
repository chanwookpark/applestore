package applestore.front.price;

import applestore.domain.order.entity.OrderItem;
import applestore.domain.product.entity.Product;

/**
 * @author chanwook
 */
public interface PricingService {
    long getPrice(Product product, OrderItem orderQuantity);
}
