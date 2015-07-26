package applestore.domain.product.entity;

import applestore.domain.catalog.entity.DisplayCategory;
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

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column(length = 100, nullable = false)
    private String productName;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    //TODO 모델 분리요..
    @JsonIgnore
    private DisplayCategory displayCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> imageList = new ArrayList<ProductImage>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, targetEntity = Sku.class)
    private List<Sku> skuList = new ArrayList<Sku>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DEFAULT_SKU_ID")
    private Sku defaultSku;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "PRD_PRD_ATTRIBUTE_R",
            joinColumns = {@JoinColumn(name = "productId")},
            inverseJoinColumns = {@JoinColumn(name = "attributeId")})
    private List<ProductAttribute> attributeList = new ArrayList<ProductAttribute>();

    public Product() {
    }

    public Product(String productId, String productName) {
        this(productId, productName, ProductStatus.SALES);
    }

    public Product(String productId, String productName, ProductStatus status) {
        this.productId = productId;
        this.productName = productName;
        this.status = status;
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

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public static Specification hasCategory(final long categoryId) {
//        return new Specification() {
//            @Override
//            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
//                return cb.equal(root.join("displayCategory").get("categoryId"), categoryId);
//            }
//        };

        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.join("displayCategory").get("categoryId"), categoryId);
            }
        };
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public Product addImage(ProductImage image) {
        image.setProduct(this);
        this.imageList.add(image);
        return this;
    }

    public Product createImage(String imageUrl, int order) {
        ProductImage image = new ProductImage(imageUrl, order);
        image.setProduct(this);
        this.imageList.add(image);
        return this;
    }

    public boolean sellable() {
        //TODO 제고 등은 나중에 넣어야지~~
        if (ProductStatus.SALES.equals(this.status)) {
            return true;
        }
        return false;
    }

    public Product addSku(Sku sku) {
        this.skuList.add(sku);
        sku.setProduct(this);
        return this;
    }

    public List<ProductAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<ProductAttribute> attributeList) {
        this.attributeList = attributeList;
    }

    public void addProductAttribute(ProductAttribute attr) {
        this.attributeList.add(attr);
        attr.addProductList(this);
    }

    public void resetProductAttribute() {
        this.attributeList = new ArrayList<ProductAttribute>();
    }

    public Sku getSku(long skuId) {
        for (Sku sku : this.skuList) {
            if (skuId == sku.getSkuId()) {
                return sku;
            }
        }
        return null;
    }

    public Sku getDefaultSku() {
        return defaultSku;
    }

    public void setDefaultSku(Sku defaultSku) {
        this.defaultSku = defaultSku;
    }

}
