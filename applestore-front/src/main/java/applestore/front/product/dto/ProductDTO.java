package applestore.front.product.dto;

/**
 * @author chanwook
 */
public class ProductDTO {

    private String productId;

    private String productName;

    private String displayName;

    private long displayCategoryId;

    private String displayCategoryName;

    private int orderEnable = 10;

    public long getDisplayCategoryId() {
        return displayCategoryId;
    }

    public void setDisplayCategoryId(long displayCategoryId) {
        this.displayCategoryId = displayCategoryId;
    }

    public String getDisplayCategoryName() {
        return displayCategoryName;
    }

    public void setDisplayCategoryName(String displayCategoryName) {
        this.displayCategoryName = displayCategoryName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOrderEnable() {
        return orderEnable;
    }

    public void setOrderEnable(int orderEnable) {
        this.orderEnable = orderEnable;
    }
}
