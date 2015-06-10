package applestore.api.catalog;

import applestore.api.catalog.model.jpa.DisplayCategory;
import applestore.api.catalog.model.jpa.Product;
import applestore.api.catalog.repository.jpa.DisplayCategoryJpaRepository;
import applestore.api.catalog.repository.jpa.ProductJpaRepository;
import applestore.api.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static applestore.api.catalog.model.jpa.Product.hasCategory;

/**
 * 1. 카테고리 상품 리스트 가지고 오기 : Search Index + Product Detail
 *
 * @author chanwook
 */
@Controller
public class CatalogViewController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private DisplayCategoryJpaRepository categoryRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public String viewCategoryPage(@PathVariable long categoryId, Pageable pageRequest, ModelMap model) {

        DisplayCategory category = categoryRepository.findOne(categoryId);
        long totalCount = productRepository.count(hasCategory(categoryId));

        model.put("category", category);
        model.put("totalProductCount", totalCount);
        model.put("pageRequest", pageRequest);
        return "category";
    }

    @RequestMapping(value = "/category/{categoryId}/productList", method = RequestMethod.GET)
    public String getProductList(@PathVariable long categoryId, Pageable pageRequest, ModelMap model) {

        DisplayCategory category = categoryRepository.findOne(categoryId);
        Page<Product> productList = catalogService.findProductList(category, pageRequest);

        model.put("category", category);
        model.put("productList", productList);
        return "category_productList";
    }
}
