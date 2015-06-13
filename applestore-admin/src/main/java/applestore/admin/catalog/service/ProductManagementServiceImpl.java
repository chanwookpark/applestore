package applestore.admin.catalog.service;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chanwook
 */
@Service
public class ProductManagementServiceImpl implements ProductManagementService {

    @Autowired
    private ProductJpaRepository pr;

    @Override
    public void createProduct(Product product) {
        pr.save(product);
    }

    @Transactional
    @Override
    public void save(ProductDataSet grid) {
        // 두 번 날리자~
        pr.save(grid.getCreateList());

        update(grid.getUpdateList());
    }

    private void update(List<Product> updateList) {
        // TODO in으로..
        for (Product t : updateList) {
            final Product persisted = pr.findOne(t.getProductId());
            //TODO 모델 매핑
            persisted.setProductName(t.getProductName());
            persisted.setStatus(t.getStatus());
            pr.save(persisted);
        }
    }
}
