package applestore.front.product;

/**
 * @author chanwook
 */
public class ProductFormForCart {

    private String productId;

    private int selectSkuId;

    private int selectItemCount;

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

    public int getSelectItemCount() {
        return selectItemCount;
    }

    public void setSelectItemCount(int selectItemCount) {
        this.selectItemCount = selectItemCount;
    }
}
