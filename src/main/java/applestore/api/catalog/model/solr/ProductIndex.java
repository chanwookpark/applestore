package applestore.api.catalog.model.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@SolrDocument(solrCoreName = "collection1")
public class ProductIndex {

    //TODO Solr ID 자동생성할 수 있게..
    @Id
    @Field
    private String id;

    @Field("productId_s")
    private String productId;

    @Field("categoryId_l")
    private long categoryId;

    public ProductIndex() {
    }

    public ProductIndex(String id, String productId, long categoryId) {
        this.id = id;
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public ProductIndex(String productId, long categoryId) {
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ProductIndex{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
