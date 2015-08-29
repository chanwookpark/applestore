package applestore.domain.product.repository;

import applestore.domain.product.entity.Sku;
import applestore.domain.product.entity.SkuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author chanwook
 */
public interface SkuJpaRepository extends JpaRepository<Sku, Long> {

    Set<Sku> findByProductProductIdAndStatus(String productId, SkuStatus status);

    List<Sku> findBySkuIdIn(List<Long> skuIdList);
}
