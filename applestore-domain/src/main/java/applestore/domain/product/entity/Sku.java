package applestore.domain.product.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private long salesPrice;

    @Column(nullable = false)
    private long retailPrice;

    @Column(nullable = true)
    private long salesStock; //기본 sku는 가격을 관리만 하고 재고는 없을 수 있기 때문에

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private boolean attributeAware;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = ProductAttributeValue.class)
    @JoinTable(name = "SKU_PRODUCT_ATTRIBUTE_VALUE_R",
            joinColumns = {@JoinColumn(name = "skuId")},
            inverseJoinColumns = {@JoinColumn(name = "valueId")})
    private List<ProductAttributeValue> attributeValueList = new ArrayList<ProductAttributeValue>();

    public Sku() {
    }

    public Sku(String skuName, long salesPrice, long salesStock) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.salesStock = salesStock;
    }

    public Sku(String skuName, long salesPrice, long retailPrice, long salesStock) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.retailPrice = retailPrice;
        this.salesStock = salesStock;
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

    public void addAttributeValue(ProductAttributeValue attrValue) {
        this.attributeValueList.add(attrValue);

        for (Sku sku : attrValue.getSkuList()) {
            if (skuId == sku.getSkuId()) {
                return;
            }
        }
        attrValue.addSku(this);
    }

    public boolean isAttributeAware() {
        return attributeAware;
    }

    public void setAttributeAware(boolean attributeAware) {
        this.attributeAware = attributeAware;
    }
}
