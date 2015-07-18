package applestore.domain.order;

/**
 * @author chanwook
 */
public class OrderItem {

    private String productId;
    private int skuId;
    private int orderCount;

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public String getProductId() {
        return productId;
    }

    public int getSkuId() {
        return skuId;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
