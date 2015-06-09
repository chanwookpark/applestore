package applestore.api.catalog.repository.solr;

import applestore.api.catalog.model.solr.CategoryProduct;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * Created by chanwook on 2015. 6. 9..
 */
public interface CategoryProductSolrRepository extends SolrCrudRepository<CategoryProduct, String>, CategoryProductSolrCustom {
}
