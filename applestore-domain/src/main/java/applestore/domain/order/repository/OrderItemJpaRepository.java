package applestore.domain.order.repository;

import applestore.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author chanwook
 */
public interface OrderItemJpaRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderItemIdIn(List<Long> idList);
}
