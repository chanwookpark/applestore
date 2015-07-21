package applestore.admin.product.service;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.admin.product.ProductAttributeFormRequest;
import applestore.domain.product.entity.Product;
import applestore.domain.product.repository.ProductAttributeJpaRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author chanwook
 */
@Service
public class ProductManagementServiceImpl implements ProductManagementService {

    @Autowired
    ProductJpaRepository pr;

    @Autowired
    ProductAttributeJpaRepository par;

    @Override
    public void createProduct(Product product) {
        pr.save(product);
    }

    @Transactional
    @Override
    public void flushUpdatedRow(ProductDataSet grid) {

        save(grid.getCreateList());

        update(grid.getUpdateList());
    }

    @Transactional
    @Override
    public void refreshAttribute(String productId, ProductAttributeFormRequest formRequest) {
        final Product product = pr.findOne(productId);

        // 전체 리셋
        product.resetProductAttribute();
        if (formRequest.getSelectAttrId() != null) {
            for (Long attrId : formRequest.getSelectAttrId()) {
                product.addProductAttribute(par.findOne(attrId));
            }
        }
    }

    private void save(List<Product> createList) {
        //TODO 한번에 할까?
        for (Product p : createList) {
            if (StringUtils.hasText(p.getProductId())) {
                pr.save(createList);
            }
        }
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
