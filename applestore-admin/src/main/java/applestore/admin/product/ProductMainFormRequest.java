package applestore.admin.product;

import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.ProductStatus;
import applestore.domain.product.entity.Sku;

/**
 * @author chanwook
 */
public class ProductMainFormRequest {

    private String productId;

    private String productName;

    private ProductStatus status;

    private long categoryId;

    private String categoryName;

    //TODO 이미지 추가
    private long skuId;

    private String skuName;

    private String skuLabel;

    private long salesPrice;

    private long retailPrice;

    private long salesStock;

    private String description;

    public ProductMainFormRequest() {
    }

    public ProductMainFormRequest(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.status = product.getStatus();
        this.categoryId = product.getDisplayCategory().getCategoryId();
        this.categoryName = product.getDisplayCategory().getCategoryName();

        if (product.getDefaultSku() == null) {
            final Sku defaultSku = new Sku();
            defaultSku.setDefaultSku(true);
            defaultSku.setSkuName(product.getProductId() + "_DEFAULT_SKU");
            product.setDefaultSku(defaultSku);
        }
        this.skuId = product.getDefaultSku().getSkuId();
        this.skuName = product.getDefaultSku().getSkuName();
        this.skuLabel = product.getDefaultSku().getLabel();
        this.salesPrice = product.getDefaultSku().getSalesPrice();
        this.retailPrice = product.getDefaultSku().getRetailPrice();
        this.salesStock = product.getDefaultSku().getSalesStock();
        this.description = product.getDefaultSku().getDescription();
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

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(long salesPrice) {
        this.salesPrice = salesPrice;
    }

    public long getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(long retailPrice) {
        this.retailPrice = retailPrice;
    }

    public long getSalesStock() {
        return salesStock;
    }

    public void setSalesStock(long salesStock) {
        this.salesStock = salesStock;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuLabel() {
        return skuLabel;
    }

    public void setSkuLabel(String skuLabel) {
        this.skuLabel = skuLabel;
    }
}
