package applestore.api.catalog;

import applestore.api.catalog.model.CategoryProductList;
import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;
import applestore.api.catalog.repository.jpa.DisplayCategoryJpaRepository;
import applestore.api.catalog.service.CategoryProductService;
import applestore.api.framework.PageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chanwook
 */
@RestController
public class CatalogRestController {

    @Autowired
    private CategoryProductService categoryProductService;

    @Autowired
    private DisplayCategoryJpaRepository categoryRepository;

    // 1. 카테고리 상품 리스트 가지고 오기 : Search Index + Product Detail
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public PageResource<CategoryProductList> getList(@PathVariable long categoryId, Pageable pageRequest) {

        DisplayCategory category = categoryRepository.findOne(categoryId);
        List<Product> productList = categoryProductService.findProductList(category, pageRequest);

        final CategoryProductList list = new CategoryProductList(category, productList);
        return new PageResource<CategoryProductList>(list, pageRequest);
    }

    // 2. 화면 꾸미기기 필요로한 리스트 가지고 오기: 배너형, 원하는대로형
}
