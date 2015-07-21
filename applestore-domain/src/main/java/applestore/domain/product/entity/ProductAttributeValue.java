package applestore.domain.product.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chanwook
 */
@Entity
public class ProductAttributeValue {

    @Id
    @GeneratedValue
    private long valueId;

    @Column(nullable = false, length = 100)
    private String value;

    @Column(nullable = false, length = 100)
    private String label;

    @ManyToOne
    @JoinColumn(name = "attributeId")
    private ProductAttribute attribute;

    @ManyToMany(mappedBy = "attributeValueList")
    private Set<Sku> skuList = new HashSet<Sku>();

    public ProductAttributeValue() {
    }

    public ProductAttributeValue(String value, String label, ProductAttribute attr) {
        this.value = value;
        this.label = label;

        this.attribute = attr;
        attr.addValue(this);
    }

    public long getValueId() {
        return valueId;
    }

    public void setValueId(long valueId) {
        this.valueId = valueId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setAttribute(ProductAttribute attribute) {
        this.attribute = attribute;
    }

    public ProductAttribute getAttribute() {
        return attribute;
    }

    public Set<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(Set<Sku> skuList) {
        this.skuList = skuList;
    }

    public void addSku(Sku sku) {
        this.skuList.add(sku);
        if (!sku.getAttributeValueList().contains(this)) {
            sku.getAttributeValueList().add(this);
        }
    }
}
