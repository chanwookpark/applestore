package applestore.front.catalog;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.front.catalog.model.CategoryProductList;
import applestore.front.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chanwook
 */
@RestController
public class CatalogRestController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private DisplayCategoryJpaRepository categoryRepository;

    // 1. 카테고리 상품 리스트 가지고 오기 : Search Index + Product Detail
    @RequestMapping(value = "/api/category/{categoryId}", method = RequestMethod.GET)
    public CategoryProductList getList(@PathVariable long categoryId, Pageable pageRequest) {

        DisplayCategory category = categoryRepository.findOne(categoryId);
        Page<Product> productList = catalogService.findProductList(category, pageRequest);

        CategoryProductList list = new CategoryProductList(category, productList);
        return list;
    }

    // 2. 화면 꾸미기기 필요로한 리스트 가지고 오기: 배너형, 원하는대로형
}
