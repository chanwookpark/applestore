package applestore.domain.product.entity;

import applestore.domain.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * @author chanwook
 */
@Entity
@Table(name = "PRD_PRODUCT_IMG")
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class ProductImage extends AbstractEntity {

    @Id
    @GeneratedValue
    private long imageId;

    @Column
    private String imageUrl;

    @Column
    private int imageOrder;

    public ProductImage() {
    }

    public ProductImage(String imageUrl) {
        this.imageUrl = imageUrl;
        this.imageOrder = 0;
    }

    public ProductImage(String imageUrl, int order) {
        this.imageUrl = imageUrl;
        this.imageOrder = order;
    }

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ProductImage)) return false;

        ProductImage compare = (ProductImage) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(imageId, compare.imageId);

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(imageId);
        return hcb.toHashCode();
    }
}
