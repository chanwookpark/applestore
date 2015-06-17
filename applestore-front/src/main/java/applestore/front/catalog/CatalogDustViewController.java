package applestore.front.catalog;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.catalog.repository.ProductJpaRepository;
import applestore.framework.r2.dustjs.spring.DustjsView;
import applestore.front.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static applestore.domain.catalog.entity.Product.hasCategory;

/**
 * 1. 카테고리 상품 리스트 가지고 오기 : Search Index + Product Detail
 *
 * @author chanwook
 */
@Controller
public class CatalogDustViewController {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private DisplayCategoryJpaRepository categoryRepository;

    @Autowired
    private ProductJpaRepository productRepository;

    @RequestMapping(value = "/d/category/{categoryId}", method = RequestMethod.GET)
    public String viewCategoryPage(@PathVariable long categoryId, Pageable pageRequest, ModelMap model) {

        DisplayCategory category = categoryRepository.findOne(categoryId);
        long totalCount = productRepository.count(hasCategory(categoryId));

        //TODO 파라미터로 변환해 받기~
        final DustjsView.DustRenderModel dustRenderModel = new DustjsView.DustRenderModel();
        dustRenderModel.put("category", category);
        dustRenderModel.put("totalProductCount", totalCount);
        dustRenderModel.put("pageRequest", pageRequest);

        model.put("dustmodel", dustRenderModel);
        return "category";
    }

    @RequestMapping(value = "/d/category/{categoryId}/productList", method = RequestMethod.GET)
    public String getProductList(@PathVariable long categoryId, Pageable pageRequest, ModelMap model) {

        DisplayCategory category = categoryRepository.findOne(categoryId);
        Page<Product> productList = catalogService.findProductList(category, pageRequest);

        //TODO 파라미터로 변환해 받기~
        final DustjsView.DustRenderModel dustRenderModel = new DustjsView.DustRenderModel();
        dustRenderModel.put("category", category);
        dustRenderModel.put("productList", productList);

        model.put("dustmodel", dustRenderModel);
        return "category_productList";
    }
}
