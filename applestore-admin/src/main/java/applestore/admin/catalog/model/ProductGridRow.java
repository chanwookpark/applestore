package applestore.admin.catalog.model;

import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.ProductStatus;

import java.io.Serializable;

/**
 * @author chanwook
 */
public class ProductGridRow implements Serializable {

    private String productId;

    private String productName;

    private String mainImageUrl;

    private String productStatus;

    private String rowStatus;

    private String categoryName;

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

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(String rowStatus) {
        this.rowStatus = rowStatus;
    }

    public Product toProduct() {
        Product p = new Product();
        p.setProductId(getProductId());
        p.setProductName(getProductName());
        p.createImage(getMainImageUrl(), 0);
        final ProductStatus status = ProductStatus.valueOf(getProductStatus());
        p.setStatus(status);
        return p;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
