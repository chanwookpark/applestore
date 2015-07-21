package applestore.domain.product.entity;

import applestore.domain.catalog.entity.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chanwook
 */
@Entity
@Table(name = "PRD_SKU")
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

    @Column(nullable = false)
    private long salesStock; //판매가능수량

    @Column(nullable = true)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            targetEntity = ProductAttributeValue.class)
    @JoinTable(name = "SKU_PRODUCT_ATTRIBUTE_VALUE_R",
            joinColumns = {@JoinColumn(name = "skuId")},
            inverseJoinColumns = {@JoinColumn(name = "valueId")})
    private Set<ProductAttributeValue> attributeValueList = new HashSet<ProductAttributeValue>();

    public Sku() {
    }

    public Sku(String skuName, long salesPrice, long salesStock) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.salesStock = salesStock;
    }

    public Sku(String skuName, long salesPrice, long retailPrice, long salesStock, Product product, ProductAttributeValue... values) {
        this.skuName = skuName;
        this.salesPrice = salesPrice;
        this.retailPrice = retailPrice;
        this.salesStock = salesStock;
        this.product = product;

        addValues(values);
    }

    private void addValues(ProductAttributeValue[] values) {
        for (ProductAttributeValue v : values) {
            this.attributeValueList.add(v);
            v.addSku(this);
        }
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

    public Set<ProductAttributeValue> getAttributeValueList() {
        return attributeValueList;
    }

    public void setAttributeValueList(Set<ProductAttributeValue> attributeValueList) {
        this.attributeValueList = attributeValueList;
    }

    public void addAttributeValue(ProductAttributeValue attrValue) {
        this.attributeValueList.add(attrValue);
        if (!attrValue.getSkuList().contains(this)) {
            attrValue.addSku(this);
        }
    }
}
