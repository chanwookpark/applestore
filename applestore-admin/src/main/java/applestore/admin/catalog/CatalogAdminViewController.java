package applestore.admin.catalog;

import applestore.admin.catalog.model.ProductForm;
import applestore.admin.catalog.service.ProductManagementService;
import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.entity.ProductStatus;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.catalog.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author chanwook
 */
@Controller
public class CatalogAdminViewController {

    @Autowired
    private ProductJpaRepository pr;

    @Autowired
    private DisplayCategoryJpaRepository cr;

    @Autowired
    private ProductManagementService ps;

    @RequestMapping(value = "/a/catalog", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.put("productList", pr.findAll());
        model.put("allCategories", cr.findAll());
        return "catalog";
    }

    @RequestMapping(value = "/a/catalog/add", method = RequestMethod.POST)
    public String addProduct(ProductForm form) {
        final Product product = createProductByForm(form);
        ps.createProduct(product);

        return "redirect:/a/catalog";
    }

    private Product createProductByForm(ProductForm form) {
        Product p = new Product();
        p.setProductId(form.getProductId());
        p.setProductName(form.getProductName());
        p.createImage(form.getMainImageUrl(), 0);
        final ProductStatus status = ProductStatus.valueOf(form.getProductStatus());
        p.setStatus(status);

        DisplayCategory category = cr.findOne(form.getCategoryId());
        p.setDisplayCategory(category);
        return p;
    }
}
