package applestore.front.product;

import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.Sku;
import applestore.domain.product.entity.SkuStatus;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.product.repository.SkuJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        final List<Sku> sku =
                sr.findByProductProductIdAndStatus(productId, SkuStatus.OPEN);
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
