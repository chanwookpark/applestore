package applestore.domain.product.entity;

import applestore.domain.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Table(name = "PRD_PRODUCT_ATTR")
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class ProductAttribute extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long attributeId;

    @Column(nullable = false, length = 50, unique = true)
    private String attributeName;

    @Column(nullable = false, length = 100)
    private String label;

    @ManyToMany(mappedBy = "attributeList")
    private List<Product> productList = new ArrayList<Product>();

    @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL)
    private List<ProductAttributeValue> attrValueList = new ArrayList<ProductAttributeValue>();

    public ProductAttribute() {
    }

    public ProductAttribute(String attributeName, String label) {
        this.attributeName = attributeName;
        this.label = label;
    }

    public ProductAttribute(long attributeId, String attributeName, String label) {
        this.attributeId = attributeId;
        this.attributeName = attributeName;
        this.label = label;
    }

    public void addValue(ProductAttributeValue value) {
        this.attrValueList.add(value);
        value.setAttribute(this);
    }

    public void addProductList(Product product) {
        this.productList.add(product);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Product)) return false;
        ProductAttribute compare = (ProductAttribute) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(attributeId, compare.attributeId);
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(attributeId);
        return hcb.toHashCode();
    }
}
