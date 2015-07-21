package applestore.domain.product.repository;

import applestore.domain.product.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author chanwook
 */
public interface ProductAttributeJpaRepository extends JpaRepository<ProductAttribute, Long>, JpaSpecificationExecutor<ProductAttribute> {
    List<ProductAttribute> findByAttributeIdNotIn(List<Long> list);
}
