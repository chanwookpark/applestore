package applestore.domain.product.entity;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.product.ProductException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Entity
@Table(name = "PRD_PRODUCT_M", indexes = {@Index(name = "prd-name", columnList = "productName")})
public class Product {

    @Id
    private String productId;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column(length = 100, nullable = false, unique = true)
    private String productName;

    @Column(length = 200)
    private String displayName;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DS_CATEGORY_ID")
    private DisplayCategory displayCategory;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> imageList = new ArrayList<ProductImage>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, targetEntity = Sku.class)
    private Set<Sku> skuList = new HashSet<Sku>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "DEFAULT_SKU_ID")
    private Sku defaultSku;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    public Set<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(Set<Sku> skuList) {
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean hasSku(long selectSkuId) {
        if (defaultSku == null) {
            throw new ProductException("상품 정보가 잘못되었습니다(기본 SKU 생성 누락)");
        }
        if (defaultSku.getSkuId() == selectSkuId) {
            return true;
        }
        for (Sku sku : this.skuList) {
            if (sku.getSkuId() == selectSkuId) {
                return true;
            }
        }
        return false;
    }

    public boolean salesEnable() {
        return ProductStatus.SALES.equals(status);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Product)) return false;

        Product compare = (Product) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(productName, compare.productName);
        eb.append(attributeList, compare.attributeList);

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(productName);
        hcb.append(attributeList);
        return hcb.toHashCode();
    }
}
