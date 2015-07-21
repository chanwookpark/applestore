package applestore.domain.product.repository;

import applestore.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author chanwook
 */
public interface ProductJpaRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor {
    List<Product> findByProductIdIn(List<String> idList);
}
