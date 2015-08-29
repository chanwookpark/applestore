package applestore.front.cart.service;

import applestore.domain.order.entity.OrderItem;
import applestore.front.cart.ProductOrderRequest;

import java.util.List;

/**
 * @author chanwook
 */
public interface CartService {

    OrderItem createOrderItem(ProductOrderRequest request);

    List<OrderItem> getOrderItem(List<Long> idList);

}
