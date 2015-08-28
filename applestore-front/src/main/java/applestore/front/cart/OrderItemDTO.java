package applestore.front.cart;

/**
 * @author chanwook
 */
public class OrderItemDTO {

    private long orderItemId;

    private int orderQuantity;

    private long orderPrice;

    //FIXME 흠..맘에 들지는 않는데.. 그래도 DTO 매핑할 때 코드를 안넣기 위해서 이렇게 맞춰둠..어떻게 개선하지..??
    private String skuProductProductName;

    private String orderSkuLabel;

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderSkuLabel() {
        return orderSkuLabel;
    }

    public void setOrderSkuLabel(String orderSkuLabel) {
        this.orderSkuLabel = orderSkuLabel;
    }

    public String getSkuProductProductName() {
        return skuProductProductName;
    }

    public void setSkuProductProductName(String skuProductProductName) {
        this.skuProductProductName = skuProductProductName;
    }
}
