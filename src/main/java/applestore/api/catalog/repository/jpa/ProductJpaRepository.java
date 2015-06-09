package applestore.api.catalog.repository.jpa;

import applestore.api.catalog.model.jpa.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by chanwook on 2015. 6. 9..
 */
public interface ProductJpaRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor {
    List<Product> findByProductIdIn(List<String> idList);
}
