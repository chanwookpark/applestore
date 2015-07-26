package applestore.admin.product.service;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.admin.product.ProductAttributeFormRequest;
import applestore.admin.product.ProductMainFormRequest;
import applestore.domain.product.entity.*;
import applestore.domain.product.repository.ProductAttributeJpaRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.product.repository.SkuJpaRepository;
import org.joda.time.DateTimeUtils;
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

    @Transactional
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
        List<Sku> createSkuList = createNewSkuList(product);

        for (Sku sku : createSkuList) {
            //FIXME 이름은 어떻게 생성할까...
            sku.setSkuName(product.getProductName() + "sku (created" + DateTimeUtils.currentTimeMillis() + ")");
            sku.setStatus(SkuStatus.OPEN);
            sku.setProduct(product);
            StringBuilder labelBuilder = new StringBuilder();
            for (ProductAttributeValue pav : sku.getAttributeValueList()) {
                labelBuilder.append(pav.getLabel());
                labelBuilder.append("/");
            }
            sku.setLabel(labelBuilder.toString());

            if (shiftable) {
                Sku before = resolveBeforeSku(current, sku);
                if (before != null) {
                    sku.setDefaultSku(before.isDefaultSku());
                    sku.setDescription(before.getDescription());
                    sku.setLabel(before.getLabel());
                    sku.setRetailPrice(before.getRetailPrice());
                    sku.setSalesPrice(before.getSalesPrice());
                    sku.setSalesStock(before.getSalesStock());
                }
            }
        }

        if (createSkuList.size() > 0) {
            sr.save(createSkuList);
        }
    }

    public List<Sku> createNewSkuList(Product product) {
        List<Sku> createSkuList = new ArrayList<Sku>();

        // 첫 번째 pa로 기준을 잡자
        final List<ProductAttribute> list = product.getAttributeList();
        final int masterIndex = 0;
        final ProductAttribute master = list.remove(masterIndex);

        for (ProductAttributeValue pav : master.getAttrValueList()) {
            final List<ProductAttributeValue> values = new ArrayList<ProductAttributeValue>();
            values.add(pav);
            loop(list, masterIndex, createSkuList, values);
        }
        return createSkuList;
    }

    private void loop(List<ProductAttribute> list, int masterIndex, List<Sku> skuList, List<ProductAttributeValue> values) {
        if (list.size() > masterIndex) {
            for (ProductAttributeValue pav : list.get(masterIndex++).getAttrValueList()) {
                final ArrayList<ProductAttributeValue> dest = new ArrayList<ProductAttributeValue>();
                dest.addAll(values);
                dest.add(pav);
                loop(list, masterIndex, skuList, dest);
            }
        } else {
            skuList.add(new Sku(values));
        }
    }

    @Transactional
    @Override
    public void updateSku(String productId, List<Sku> updateSkuList) {
        final Product product = pr.findOne(productId);

        for (Sku updateSku : updateSkuList) {
            Sku persisted = product.getSku(updateSku.getSkuId());
            if (persisted != null) {
                persisted.setDefaultSku(updateSku.isDefaultSku());
                persisted.setDescription(updateSku.getDescription());
                persisted.setLabel(updateSku.getLabel());
                persisted.setRetailPrice(updateSku.getRetailPrice());
                persisted.setSalesPrice(updateSku.getSalesPrice());
                persisted.setSalesStock(updateSku.getSalesStock());
                persisted.setSkuName(updateSku.getSkuName());
                persisted.setStatus(updateSku.getStatus());
            }
        }
    }

    @Transactional
    @Override
    public void updateProductMain(ProductMainFormRequest formRequest) {
        Product product = pr.findOne(formRequest.getProductId());
        Sku defaultSku = product.getDefaultSku();
        if (product.getDefaultSku() == null) {
            defaultSku = new Sku();
            defaultSku.setDefaultSku(true);
            product.setDefaultSku(defaultSku);
        }
        bindingSku(formRequest, defaultSku);
    }

    private void bindingSku(ProductMainFormRequest formRequest, Sku defaultSku) {
        defaultSku.setSkuName(formRequest.getSkuName());
        defaultSku.setLabel(formRequest.getSkuLabel());
        defaultSku.setDescription(formRequest.getDescription());
        defaultSku.setRetailPrice(formRequest.getRetailPrice());
        defaultSku.setSalesPrice(formRequest.getSalesPrice());
        defaultSku.setSalesStock(formRequest.getSalesStock());
    }

    private Sku resolveBeforeSku(List<Sku> list, Sku target) {
        for (Sku sku : list) {
            final List<ProductAttributeValue> pav1 = sku.getAttributeValueList();
            final List<ProductAttributeValue> pav2 = target.getAttributeValueList();

            if (pav1.size() == pav1.size()) {
                int match = 0;
                for (ProductAttributeValue v1 : pav1) {
                    for (ProductAttributeValue v2 : pav2) {
                        if (v1.getValueId() == v2.getValueId()) {
                            ++match;
                            break;
                        }
                    }
                }
                if (match == pav1.size()) {
                    return sku;
                }
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
