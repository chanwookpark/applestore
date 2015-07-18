package applestore.front.product;

import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chanwook
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductJpaRepository pr;

    @Override
    public Product getProduct(String productId) {
        return pr.findOne(productId);
    }

}
