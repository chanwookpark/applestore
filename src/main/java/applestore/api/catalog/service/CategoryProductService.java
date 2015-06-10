package applestore.api.catalog.service;

import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author chanwook
 */
public interface CategoryProductService {
    List<Product> findProductList(DisplayCategory category, Pageable pageRequest);
}
