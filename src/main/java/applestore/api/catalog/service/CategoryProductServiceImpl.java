package applestore.api.catalog.service;

import applestore.api.catalog.model.solr.CategoryProduct;
import applestore.api.catalog.repository.solr.CategoryProductSolrCustom;
import applestore.api.catalog.model.jpa.Product;
import applestore.api.catalog.repository.jpa.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanwook on 2015. 6. 9..
 */
@Service
public class CategoryProductServiceImpl implements CategoryProductService {
    @Autowired
    private CategoryProductSolrCustom categoryProductSolrRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public List<Product> findProductList(long categoryId, Pageable pageRequest) {
        final List<CategoryProduct> categoryProductList =
                categoryProductSolrRepository.findByCategoryId(categoryId, pageRequest).getContent();

        List<Product> productList =
                productJpaRepository.findByProductIdIn(toProductIdList(categoryProductList));
        return productList;
    }

    private List<String> toProductIdList(List<CategoryProduct> list) {
        List<String> idList = new ArrayList<String>(list.size());
        for (CategoryProduct cp : list) {
            idList.add(cp.getProductId());
        }
        return idList;
    }
}
