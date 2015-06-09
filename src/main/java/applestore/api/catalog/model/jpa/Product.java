package applestore.api.catalog.model.jpa;

import javax.persistence.*;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Entity
public class Product {

    @Id
    private String productId;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private DisplayCategory displayCategory;

    public Product() {
    }

    public Product(String productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DisplayCategory getDisplayCategory() {
        return displayCategory;
    }

    public void setDisplayCategory(DisplayCategory displayCategory) {
        this.displayCategory = displayCategory;
    }
}
