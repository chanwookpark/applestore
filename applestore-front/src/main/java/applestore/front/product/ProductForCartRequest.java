package applestore.front.product;

/**
 * @author chanwook
 */
public class ProductForCartRequest {

    private String productId;

    private int selectSkuId;

    private int selectItemQuantity;

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

    public int getSelectItemQuantity() {
        return selectItemQuantity;
    }

    public void setSelectItemQuantity(int selectItemQuantity) {
        this.selectItemQuantity = selectItemQuantity;
    }
}
