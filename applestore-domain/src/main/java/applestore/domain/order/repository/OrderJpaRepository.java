package applestore.domain.order.repository;

import applestore.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author chanwook
 */
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
