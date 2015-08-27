package applestore.front.cart;

import applestore.domain.order.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
public class OrderItemViewModel {
    private String productName;
    private OrderItem orderItem;

    public OrderItemViewModel() {
    }

    public OrderItemViewModel(OrderItem orderItem) {
        this.orderItem = orderItem;
        this.productName = orderItem.getOrderSku().getProduct().getProductName();
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public String getProductName() {
        return productName;
    }

    public static List<OrderItemViewModel> createViewModel(List<OrderItem> itemList) {
        List<OrderItemViewModel> viewModel = new ArrayList<OrderItemViewModel>();
        for (OrderItem oi : itemList) {
            OrderItemViewModel oivm = new OrderItemViewModel(oi);
            viewModel.add(oivm);
        }
        return viewModel;
    }
}
