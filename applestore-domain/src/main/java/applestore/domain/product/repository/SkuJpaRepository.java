package applestore.domain.product.repository;

import applestore.domain.product.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chanwook
 */
public interface SkuJpaRepository extends JpaRepository<Sku, Long> {
}
