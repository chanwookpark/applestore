package applestore.admin.product.service;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.admin.product.ProductAttributeFormRequest;
import applestore.domain.product.entity.Product;

/**
 * @author chanwook
 */
public interface ProductManagementService {
    void createProduct(Product product);

    void flushUpdatedRow(ProductDataSet grid);

    void refreshAttribute(String productId, ProductAttributeFormRequest formRequest);

    void createSku(String productId, boolean shiftable);
}
