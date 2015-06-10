package applestore.api.catalog.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Entity
public class Product {

    @Id
    private String productId;

    @Column
    private String productName;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    //TODO 모델 분리요..
    @JsonIgnore
    private DisplayCategory displayCategory;

    public Product() {
    }

    public Product(String productId, String productName) {
        this.productId = productId;
        this.productName = productName;
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

    public DisplayCategory getDisplayCategory() {
        return displayCategory;
    }

    public void setDisplayCategory(DisplayCategory displayCategory) {
        this.displayCategory = displayCategory;
    }
}
