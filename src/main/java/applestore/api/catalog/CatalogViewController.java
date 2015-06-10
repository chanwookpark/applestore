package applestore.api.catalog;

import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;
import applestore.api.catalog.repository.jpa.DisplayCategoryJpaRepository;
import applestore.api.catalog.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author chanwook
 */
@Controller
public class CatalogViewController {

    @Autowired
    private CategoryProductService categoryProductService;

    @Autowired
    private DisplayCategoryJpaRepository categoryRepository;

    // 1. 카테고리 상품 리스트 가지고 오기 : Search Index + Product Detail
    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public String getList(@PathVariable long categoryId, Pageable pageRequest, ModelMap model) {

        DisplayCategory category = categoryRepository.findOne(categoryId);
        Page<Product> productList = categoryProductService.findProductList(category, pageRequest);

        model.put("category", category);
        model.put("productList", productList);
        return "category";
    }
}
