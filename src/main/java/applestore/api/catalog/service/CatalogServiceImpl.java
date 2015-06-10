package applestore.api.catalog.service;

import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;
import applestore.api.catalog.model.solr.ProductIndex;
import applestore.api.catalog.repository.jpa.ProductJpaRepository;
import applestore.api.catalog.repository.solr.ProductIndexSolrRepository;
import applestore.api.framework.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static applestore.api.catalog.model.jpa.Product.hasCategory;

/**
 * @author chanwook
 */
@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    private ProductIndexSolrRepository produtIndexRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @Override
    public Page<Product> findProductList(final DisplayCategory category, Pageable pageRequest) {
        List<String> idList = getProductIdList(category, pageRequest);
        List<Product> productList = productRepository.findByProductIdIn(idList);

        long totalCount = productRepository.count(hasCategory(category.getCategoryId()));

        return new PageImpl<Product>(productList, pageRequest, totalCount);
    }

    private List<String> getProductIdList(DisplayCategory category, Pageable pageRequest) {
        if (category == null) {
            throw new ApplicationException("카테고리 정보가 필수로 필요합니다!");
        }

        Page<ProductIndex> page = produtIndexRepository.findByCategoryId(category.getCategoryId(), pageRequest);
        if (page.getContent() == null) {
            throw new ApplicationException(category.getCategoryId() + "에 해당하는 상품 정보가 한 건도 등록되어 있지 않습니다!");
        }

        return toProductIdList(page.getContent());
    }

    private List<String> toProductIdList(List<ProductIndex> list) {
        List<String> idList = new ArrayList<String>(list.size());
        for (ProductIndex cp : list) {
            idList.add(cp.getProductId());
        }
        return idList;
    }
}
