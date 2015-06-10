package applestore.admin.catalog.service;

import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chanwook
 */
@Service
@org.springframework.transaction.annotation.Transactional
public class ProductManagementServiceImpl implements ProductManagementService {

    @Autowired
    private ProductJpaRepository pr;

    @Override
    public void createProduct(Product product) {
        pr.save(product);
    }
}
