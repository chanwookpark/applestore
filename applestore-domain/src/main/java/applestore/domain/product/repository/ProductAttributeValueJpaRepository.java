package applestore.domain.product.repository;

import applestore.domain.product.entity.ProductAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chanwook
 */
public interface ProductAttributeValueJpaRepository extends JpaRepository<ProductAttributeValue, Long> {
}
