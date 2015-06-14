package applestore.admin.catalog.service;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.domain.catalog.entity.Product;

/**
 * @author chanwook
 */
public interface ProductManagementService {
    void createProduct(Product product);

    void flushUpdatedRow(ProductDataSet grid);
}
