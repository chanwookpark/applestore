package applestore.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Entity
@Table(name = "PRD_PRODUCT_ATTR_VAL")
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
    @JsonIgnore //TODO 모델분리요~
    private ProductAttribute attribute;

    @ManyToMany(mappedBy = "attributeValueList")
    @JsonIgnore //TODO 모델분리요~
    private List<Sku> skuList = new ArrayList<Sku>();

    public ProductAttributeValue() {
    }

    public ProductAttributeValue(long valueId, String value, String label) {
        this.valueId = valueId;
        this.value = value;
        this.label = label;
    }

    public ProductAttributeValue(String value, String label) {
        this.value = value;
        this.label = label;
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

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public void addSku(Sku sku) {
        this.skuList.add(sku);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ProductAttributeValue)) return false;

        ProductAttributeValue compare = (ProductAttributeValue) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(valueId, compare.valueId);

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(valueId);
        return hcb.toHashCode();
    }
}
