package applestore.admin.product.service;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.admin.product.ProductAttributeFormRequest;
import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.Sku;

import java.util.List;

/**
 * @author chanwook
 */
public interface ProductManagementService {
    void createProduct(Product product);

    void flushUpdatedRow(ProductDataSet grid);

    void refreshAttribute(String productId, ProductAttributeFormRequest formRequest);

    void createSku(String productId, boolean shiftable);

    void updateSku(String productId, List<Sku> skuList);
}
