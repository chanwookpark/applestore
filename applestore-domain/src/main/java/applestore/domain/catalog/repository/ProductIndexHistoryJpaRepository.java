package applestore.domain.catalog.repository;

import applestore.domain.catalog.entity.ProductIndexHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chanwook
 */
public interface ProductIndexHistoryJpaRepository extends JpaRepository<ProductIndexHistory, Long> {
}
