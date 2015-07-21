package applestore.domain.product.repository;

import applestore.domain.product.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chanwook
 */
public interface ProductAttributeJpaRepository extends JpaRepository<ProductAttribute, Long> {
}
