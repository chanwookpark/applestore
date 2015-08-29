package applestore.domain.product.entity;

import applestore.domain.order.entity.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chanwook
 */
@Entity
@Table(name = "PRD_SKU")
public class Sku {

    public static final String DEFAULT_SKU_NAME_POSTFIX = "_DEFAULT_SKU";
    public static final int DEFAULT_SKU_ORDER = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long skuId;

    @Column(length = 100, nullable = false)
    private String skuName;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

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

    public Sku(ProductAttributeValue... values) {
        replaceProductAttributeValue(values);
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
        replaceProductAttributeValue(values);

    }

    public void replaceProductAttributeValue(ProductAttributeValue[] values) {
        List<ProductAttributeValue> valueList = new ArrayList<ProductAttributeValue>(values.length);
        for (ProductAttributeValue value : values) {
            valueList.add(value);
        }
        this.attributeValueList = valueList;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public long getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(long salesPrice) {
        this.salesPrice = salesPrice;
    }

    public long getSalesStock() {
        return salesStock;
    }

    public void setSalesStock(long salesStock) {
        this.salesStock = salesStock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(long retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductAttributeValue> getAttributeValueList() {
        return attributeValueList;
    }

    public void setAttributeValueList(List<ProductAttributeValue> attributeValueList) {
        this.attributeValueList = attributeValueList;
    }

    public boolean isDefaultSku() {
        return defaultSku;
    }

    public void setDefaultSku(boolean defaultSku) {
        this.defaultSku = defaultSku;
    }

    public SkuStatus getStatus() {
        return status;
    }

    public void setStatus(SkuStatus status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Sku)) return false;

        Sku compare = (Sku) obj;
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
