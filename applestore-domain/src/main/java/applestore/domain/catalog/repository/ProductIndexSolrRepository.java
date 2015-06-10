package applestore.domain.catalog.repository;

import applestore.domain.catalog.solr.ProductIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

/**
 * @author chanwook
 */
public interface ProductIndexSolrRepository extends SolrCrudRepository<ProductIndex, String> {
    Page<ProductIndex> findByCategoryId(long categoryId, Pageable request);

    void deleteByProductId(String productId);
}
