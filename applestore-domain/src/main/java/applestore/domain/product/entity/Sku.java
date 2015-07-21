package applestore.domain.product.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chanwook
 */
@Entity
public class Sku {

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
    private boolean defaultSku = false;

    @Column(nullable = false)
    private SkuStatus status = SkuStatus.CLOSE;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "valueId")
    private ProductAttributeValue attributeValue;

    public Sku() {
    }

    public Sku(String skuName, long salesPrice, long salesStock) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.salesStock = salesStock;
    }

    public Sku(String skuName, long salesPrice, long retailPrice, long salesStock, ProductAttributeValue value) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.retailPrice = retailPrice;
        this.salesStock = salesStock;
        this.attributeValue = value;
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

    public ProductAttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(ProductAttributeValue attributeValue) {
        this.attributeValue = attributeValue;
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
}
