package applestore.admin.product.service;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.admin.product.ProductAttributeFormRequest;
import applestore.domain.product.entity.*;
import applestore.domain.product.repository.ProductAttributeJpaRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.product.repository.SkuJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    @Autowired
    SkuJpaRepository sr;

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

    @Override
    public void createSku(String productId, boolean shiftable) {
        List<Sku> current = sr.findByProductProductIdAndStatus(productId, SkuStatus.OPEN);

        // sku 상태 변경
        if (current != null) {
            for (Sku sku : current) {
                sku.setStatus(SkuStatus.CLOSE);
            }
            sr.save(current);
        }

        //자, 이제 생성하자
        final Product product = pr.findOne(productId);

        List<Sku> createSkuList = new ArrayList<Sku>();
        for (ProductAttribute attr : product.getAttributeList()) {
            for (ProductAttributeValue attrValue : attr.getAttrValueList()) {
                Sku createSku = new Sku();
                createSku.setAttributeValue(attrValue);
                //이름은 나중에 필요시 변경하는 걸로..
                createSku.setSkuName(product.getProductName() + "-" + attr.getAttributeName() + "-" + attrValue.getValue());
                createSku.setStatus(SkuStatus.OPEN);
                createSku.setProduct(product);

                // 이전 sku의 값을 이어갈 것인가?
                if (shiftable) {
                    Sku before = resolveBeforeSku(current, attrValue);
                    if (before != null) {
                        createSku.setDefaultSku(before.isDefaultSku());
                        createSku.setDescription(before.getDescription());
                        createSku.setLabel(before.getLabel());
                        createSku.setRetailPrice(before.getRetailPrice());
                        createSku.setSalesPrice(before.getSalesPrice());
                        createSku.setSalesStock(before.getSalesStock());
                    }
                }
                createSkuList.add(createSku);
            }
        }

        if (createSkuList.size() > 0) {
            sr.save(createSkuList);
        }

        //TODO 동일한 attr-attrValue면 유지하게?!!
    }

    private Sku resolveBeforeSku(List<Sku> list, ProductAttributeValue attrValue) {
        for (Sku sku : list) {
            if (sku.getAttributeValue().getValueId() == attrValue.getValueId()) {
                return sku;
            }
        }
        return null;
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
