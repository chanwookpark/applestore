package applestore.front.order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
public class CreateOrderRequestForm {

    private List<Long> orderItemIds = new ArrayList<Long>();

    private List<Integer> orderQuantities = new ArrayList<Integer>();

    public List<Long> getOrderItemIds() {
        return orderItemIds;
    }

    public void setOrderItemIds(List<Long> orderItemIds) {
        this.orderItemIds = orderItemIds;
    }

    public List<Integer> getOrderQuantities() {
        return orderQuantities;
    }

    public void setOrderQuantities(List<Integer> orderQuantities) {
        this.orderQuantities = orderQuantities;
    }

    @Override
    public String toString() {
        return "주문 생성 정보 {" +
                "orderItemIds=" + orderItemIds +
                ", orderQuantities=" + orderQuantities +
                '}';
    }
}
