package applestore.api.catalog.model.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Entity
public class DisplayCategory {

    @Id
    @GeneratedValue
    private long categoryId;

    @Column(length = 100)
    private String categoryName;

    @OneToMany(mappedBy = "displayCategory", cascade = CascadeType.PERSIST)
    private List<Product> productList = new ArrayList<Product>();

    public DisplayCategory() {
    }

    public DisplayCategory(String categoryName) {
        this.categoryName = categoryName;
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

    public DisplayCategory addProduct(Product product) {
        this.productList.add(product);
        product.setDisplayCategory(this);
        return this;
    }
}
