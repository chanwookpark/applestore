package applestore.api.catalog.repository.solr;

import applestore.api.catalog.model.solr.CategoryProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * @author chanwook
 */
public interface CategoryProductSolrRepository extends SolrCrudRepository<CategoryProduct, String> {
    Page<CategoryProduct> findByCategoryId(long categoryId, Pageable request);
}
