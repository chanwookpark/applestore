package applestore.front.catalog.service;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static applestore.domain.catalog.entity.Product.hasCategory;

/**
 * @author chanwook
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private ProductIndexService indexService;

    @Autowired
    private ProductJpaRepository productRepository;

    @Override
    public Page<Product> findProductList(final DisplayCategory category, Pageable pageRequest) {
        List<String> productIndex = indexService.getProductIndex(category, pageRequest);

        List<Product> productList = productRepository.findByProductIdIn(productIndex);
        long totalCount = productRepository.count(hasCategory(category.getCategoryId()));

        return new PageImpl<Product>(productList, pageRequest, totalCount);
    }

}
