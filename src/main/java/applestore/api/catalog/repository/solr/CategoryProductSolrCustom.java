package applestore.api.catalog.repository.solr;

import applestore.api.catalog.model.solr.CategoryProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by chanwook on 2015. 6. 9..
 */
public interface CategoryProductSolrCustom {
    Page<CategoryProduct> findByCategoryId(long categoryId, Pageable request);
}
