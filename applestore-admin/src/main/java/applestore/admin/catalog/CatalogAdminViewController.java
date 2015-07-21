package applestore.admin.catalog;

import applestore.admin.catalog.model.ProductDataSet;
import applestore.admin.catalog.model.ProductGridForm;
import applestore.admin.catalog.model.ProductGridRow;
import applestore.admin.product.service.ProductManagementService;
import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.product.entity.Product;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.put("productList", pr.findAll());
        model.put("allCategories", cr.findAll());
        return "catalog";
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("grid") ProductGridForm grid) {
        final ProductDataSet ds = createDataSet(grid.getRowList());
        ps.flushUpdatedRow(ds);

        return "redirect:/catalog";
    }

    private ProductDataSet createDataSet(List<ProductGridRow> formList) {
        ProductDataSet ds = new ProductDataSet();
        for (ProductGridRow form : formList) {
            Product p = form.toProduct();
            DisplayCategory category = cr.findByCategoryName(form.getCategoryName());
            p.setDisplayCategory(category);

            if ("U".equals(form.getRowStatus())) {
                ds.addUpdate(p);
            } else if ("C".equals(form.getRowStatus())) {
                ds.addCreate(p);
            }
        }
        return ds;
    }
}
