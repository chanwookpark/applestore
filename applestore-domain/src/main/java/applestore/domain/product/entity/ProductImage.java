package applestore.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @JsonIgnore // TODO 제거
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
}