package applestore.domain.order;

/**
 * @author chanwook
 */
public class OrderItem {

    private String productId;
    private int skuId;
    private int orderQuantity;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getSkuId() {
        return skuId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }
}
