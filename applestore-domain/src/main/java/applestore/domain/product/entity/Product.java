package applestore.domain.product.entity;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.common.AbstractEntity;
import applestore.domain.product.ProductException;
import applestore.domain.product.SkuException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Entity
@Table(name = "PRD_PRODUCT", indexes = {@Index(name = "prd-name", columnList = "productName")})
@Getter
@Setter
@AllArgsConstructor
@ToString
@Slf4j
public class Product extends AbstractEntity {

    @Id
    private String productId;

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
        if (sku.getProduct() != null && this.skuList.contains(sku)) {
            throw new SkuException("Already sales SKU! (" + sku + ")");
        }
        sku.setProduct(this);
        this.skuList.add(sku);
        return this;
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
