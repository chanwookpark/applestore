package applestore.api.catalog.model.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Entity
@SequenceGenerator(initialValue = 1, name = "DISPLAY_CATEGORY_SEQ")
public class DisplayCategory {

    @Id
    @GeneratedValue(generator = "DISPLAY_CATEGORY_SEQ")
    private long categoryId;

    @Column(length = 100, nullable = false)
    private String categoryName;

    @Column
    private String categoryImageUrl;

    @OneToMany(mappedBy = "displayCategory", cascade = CascadeType.PERSIST)
    private List<Product> productList = new ArrayList<Product>();

    public DisplayCategory() {
    }

    public DisplayCategory(String categoryName, String categoryImageUrl) {
        this.categoryName = categoryName;
        this.categoryImageUrl = categoryImageUrl;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(String categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

    public DisplayCategory addProduct(Product product) {
        this.productList.add(product);
        product.setDisplayCategory(this);
        return this;
    }
}
