package applestore.api.catalog.repository.jpa;

import applestore.api.catalog.model.jpa.DisplayCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by chanwook on 2015. 6. 9..
 */
public interface DisplayCategoryJpaRepository extends JpaRepository<DisplayCategory, Long>, JpaSpecificationExecutor {
}
