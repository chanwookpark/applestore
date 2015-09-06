package applestore.domain.product.entity;

import applestore.domain.common.AbstractEntity;
import applestore.domain.order.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Entity
@Table(name = "PRD_SKU")
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class Sku extends AbstractEntity {

    public static final String DEFAULT_SKU_NAME_POSTFIX = "_DEFAULT_SKU";
    public static final int DEFAULT_SKU_ORDER = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long skuId;

    @Column(length = 100, nullable = false)
    private String skuName;

    @Column(length = 100)
    private String label;

    @Column
    private long salesPrice;

    @Column
    private long retailPrice;

    @Column
    private long salesStock; //기본 sku는 가격을 관리만 하고 재고는 없을 수 있기 때문에

    @Column
    private String description;

    @Column(nullable = false)
    private int displayOrder = 1;

    @Column(nullable = false)
    private boolean defaultSku = false;

    @Column(nullable = false)
    private SkuStatus status = SkuStatus.CLOSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "SKU_PRD_ATTR_VALUE_R",
            joinColumns = {@JoinColumn(name = "skuId")},
            inverseJoinColumns = {@JoinColumn(name = "valueId")})
    private List<ProductAttributeValue> attributeValueList = new ArrayList<ProductAttributeValue>();

    @OneToMany(mappedBy = "orderSku", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    public Sku() {
    }

    public Sku(String skuName, ProductAttributeValue... attributeValues) {
        this.skuName = skuName;
        convertAndSetProductAttributeValueList(attributeValues);
    }

    public Sku(ProductAttributeValue... values) {
        convertAndSetProductAttributeValueList(values);
    }

    public Sku(List<ProductAttributeValue> values) {
        this.attributeValueList = values;
    }

    public Sku(String skuName, long salesPrice, long salesStock) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.salesStock = salesStock;
    }

    public Sku(String skuName, long salesPrice, long retailPrice, long salesStock, ProductAttributeValue... values) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.retailPrice = retailPrice;
        this.salesStock = salesStock;
        convertAndSetProductAttributeValueList(values);

    }

    public void convertAndSetProductAttributeValueList(ProductAttributeValue[] values) {
        List<ProductAttributeValue> valueList = new ArrayList<ProductAttributeValue>(values.length);
        for (ProductAttributeValue value : values) {
            valueList.add(value);
        }
        this.attributeValueList = valueList;
    }

    public static Sku createDefaultSku(Product product) {
        Sku defaultSku = new Sku();
        defaultSku.setDefaultSku(true);
        defaultSku.setSkuName(product.getProductId() + DEFAULT_SKU_NAME_POSTFIX);
        defaultSku.setProduct(product);
        defaultSku.setDisplayOrder(DEFAULT_SKU_ORDER);
        defaultSku.setStatus(SkuStatus.OPEN);
        product.setDefaultSku(defaultSku);
        return defaultSku;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Sku)) return false;

        Sku compare = (Sku) obj;
        if (product == null) return false; // product가 설정되어 있지 않다면 같은 SKU로 판단할 수가 없음!

        EqualsBuilder eb = new EqualsBuilder();
        eb.append(product.getProductId(), compare.product.getProductId());
        eb.append(attributeValueList, compare.attributeValueList);

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(product.getProductId());
        hcb.append(attributeValueList);
        return hcb.toHashCode();
    }
}
