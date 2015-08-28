package applestore.front.cart.service;

import applestore.domain.order.entity.OrderItem;
import applestore.front.product.ProductForCartRequest;

import java.util.List;

/**
 * @author chanwook
 */
public interface CartService {

    OrderItem createOrderItem(ProductForCartRequest request);

    List<OrderItem> getOrderItem(List<Long> idList);

}
