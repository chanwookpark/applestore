package applestore.front.product;

/**
 * @author chanwook
 */
public class ProductForCartRequest {

    private String productId;

    private int selectSkuId;

    private int orderQuantity;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getSelectSkuId() {
        return selectSkuId;
    }

    public void setSelectSkuId(int selectSkuId) {
        this.selectSkuId = selectSkuId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
