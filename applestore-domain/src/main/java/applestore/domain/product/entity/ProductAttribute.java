package applestore.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Entity
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long attributeId;

    @Column(nullable = false, length = 50, unique = true)
    private String attributeName;

    @Column(nullable = false, length = 100)
    private String label;

    @ManyToMany(mappedBy = "attributeList")
    @JsonIgnore //TODO 모델분리요~
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


    public long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(long attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ProductAttributeValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<ProductAttributeValue> attrValueList) {
        this.attrValueList = attrValueList;
    }

    public void addValue(ProductAttributeValue value) {
        this.attrValueList.add(value);
        value.setAttribute(this);
    }

    public void addProductList(Product product) {
        this.productList.add(product);
    }

    public int getValueSize() {
        return this.attrValueList.size();
    }
}
