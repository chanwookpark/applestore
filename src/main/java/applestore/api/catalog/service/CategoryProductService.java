package applestore.api.catalog.service;

import applestore.api.catalog.model.jpa.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by chanwook on 2015. 6. 9..
 */
public interface CategoryProductService {
    List<Product> findProductList(long categoryId, Pageable pageRequest);
}
