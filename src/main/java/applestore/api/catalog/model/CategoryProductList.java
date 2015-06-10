package applestore.api.catalog.model;

import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * @author chanwook
 */
public class CategoryProductList implements Serializable {
    private long categoryId;
    private String categoryName;

    private final Page<Product> productList;

    public CategoryProductList(DisplayCategory category, Page<Product> productList) {
        //TODO model converting
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
        this.productList = productList;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Page<Product> getProductList() {
        return productList;
    }
}
