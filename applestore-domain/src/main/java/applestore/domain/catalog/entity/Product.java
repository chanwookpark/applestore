package applestore.domain.catalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> imageList = new ArrayList<ProductImage>();

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

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

    public List<ProductImage> getImageList() {
        return imageList;
    }

    public void setImageList(List<ProductImage> imageList) {
        this.imageList = imageList;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public static Specification hasCategory(final long categoryId) {
        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.join("displayCategory").get("categoryId"), categoryId);
            }
        };
    }

    public Product addImage(ProductImage image) {
        this.imageList.add(image);
        image.setProduct(this);
        return this;
    }
}
