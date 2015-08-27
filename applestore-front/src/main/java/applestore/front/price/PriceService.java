package applestore.front.price;

import applestore.domain.order.entity.OrderItem;
import applestore.domain.product.entity.Product;

import java.util.List;

/**
 * @author chanwook
 */
public interface PriceService {
    long getPrice(Product product, OrderItem orderQuantity);

    Long getTotalOrderAmount(List<OrderItem> itemList);
}
