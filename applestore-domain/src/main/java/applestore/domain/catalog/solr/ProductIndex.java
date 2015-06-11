package applestore.domain.catalog.solr;

import applestore.framework.DateUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

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

    @Field("create_dt")
    private Date created;

    @Field("score_l")
    private long score;

    public ProductIndex() {
    }

    public ProductIndex(String id, String productId, long categoryId) {
        this.id = id;
        this.productId = productId;
        this.categoryId = categoryId;
        this.created = DateUtils.now();
        this.score = 0l;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ProductIndex{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", categoryId=" + categoryId +
                ", created=" + created +
                ", score=" + score +
                '}';
    }
}
