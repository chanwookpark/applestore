package applestore.domain.product.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * @author chanwook
 */
@Entity
public class ProductImage {

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

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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
