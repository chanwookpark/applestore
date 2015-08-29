package applestore.front.product.service;

import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.Sku;
import applestore.domain.product.entity.SkuStatus;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.product.repository.SkuJpaRepository;
import applestore.framework.ApplicationException;
import applestore.front.product.NonEnoughSalesStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author chanwook
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductJpaRepository pr;

    @Autowired
    private SkuJpaRepository sr;

    @Override
    public Product getProduct(String productId) {
        final Product product = pr.findOne(productId);
        // 오픈된 sku만 조회해서 설정
        final Set<Sku> sku =
                sr.findByProductProductIdAndStatus(productId, SkuStatus.OPEN);

        if (sku == null || sku.size() == 0) {
            throw new ApplicationException("판매 가능한 상품 상세 정보가 없습니다!");
        }
        product.setSkuList(sku);
        return product;
    }

    @Override
    public void checkProductQuantity(List<Long> skuIdList, List<Integer> orderQuantities) {
        List<Sku> skuList = sr.findBySkuIdIn(skuIdList);
        for (int index = 0; index < skuList.size(); index++) {
            final Sku sku = skuList.get(index);
            if (sku.getSalesStock() < orderQuantities.get(index)) {
                throw new NonEnoughSalesStockException(sku.getSkuId(), sku.getSalesStock(), orderQuantities.get(index));
            }
        }
    }

}
