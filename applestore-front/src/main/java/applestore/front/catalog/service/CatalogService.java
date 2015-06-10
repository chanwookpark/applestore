package applestore.front.catalog.service;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author chanwook
 */
public interface CatalogService {
    Page<Product> findProductList(DisplayCategory category, Pageable pageRequest);
}
