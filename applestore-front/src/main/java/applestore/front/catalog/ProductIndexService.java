package applestore.front.catalog;

import applestore.domain.catalog.entity.DisplayCategory;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author chanwook
 */
public interface ProductIndexService {
    List<String> getProductIndex(DisplayCategory category, Pageable pageRequest);
}
