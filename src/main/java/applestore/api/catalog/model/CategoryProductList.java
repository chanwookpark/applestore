package applestore.api.catalog.model;

import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;

import java.io.Serializable;
import java.util.List;

/**
 * @author chanwook
 */
public class CategoryProductList implements Serializable {
    private long categoryId;
    private String categoryName;

    private final List<Product> productList;

    public CategoryProductList(DisplayCategory category, List<Product> productList) {
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

    public List<Product> getProductList() {
        return productList;
    }
}
