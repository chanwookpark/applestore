package applestore.api.catalog.repository.jpa;

import applestore.api.catalog.model.jpa.ProductIndexHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chanwook
 */
public interface ProductIndexHistoryJpaRepository extends JpaRepository<ProductIndexHistory, Long> {
}
