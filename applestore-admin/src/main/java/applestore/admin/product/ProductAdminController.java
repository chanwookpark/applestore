package applestore.admin.product;

import applestore.admin.product.service.ProductManagementService;
import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.ProductAttribute;
import applestore.domain.product.repository.ProductAttributeJpaRepository;
import applestore.domain.product.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Controller
public class ProductAdminController {

    @Autowired
    ProductJpaRepository pr;

    @Autowired
    ProductAttributeJpaRepository par;

    @Autowired
    ProductManagementService service;

    @RequestMapping(value = "/product/{productId}/detail", method = RequestMethod.GET)
    public String attributeFrom(@PathVariable String productId, ModelMap model) {
        Product product = pr.findOne(productId);
        List<ProductAttribute> attrList;
        if (product.getAttributeList() != null && product.getAttributeList().size() > 0) {
            attrList = par.findByAttributeIdNotIn(toIdList(product));
        } else {
            attrList = par.findAll();
        }

        model.put("product", product);
        model.put("attrList", attrList);
        model.put("productMainForm", new ProductMainFormRequest(product));
        model.put("skuForm", new SkuFormRequest(product.getSkuList()));

        return "prdAttribute";
    }

    private List<Long> toIdList(Product product) {
        List<Long> idList = new ArrayList<Long>();
        for (ProductAttribute attr : product.getAttributeList()) {
            idList.add(attr.getAttributeId());
        }
        return idList;
    }

    @RequestMapping(value = "/product/{productId}/attribute", method = RequestMethod.POST)
    public String mappingAttributeToProduct(@PathVariable String productId,
                                            ProductAttributeFormRequest formRequest) {
        service.refreshAttribute(productId, formRequest);

        return redirectTo(productId);
    }

    @RequestMapping("/product/{productId}/sku/create")
    public String createSku(@PathVariable String productId, boolean shiftable) {

        service.createSku(productId, shiftable);

        return redirectTo(productId);
    }

    @RequestMapping("/sku/update")
    public String updateSku(String productId, @ModelAttribute("skuForm") SkuFormRequest formRequest) {
        service.updateSku(productId, formRequest.getSkuList());
        return redirectTo(productId);
    }

    @RequestMapping("/product/main")
    public String updateProductMain(ProductMainFormRequest formRequest) {

        service.updateProductMain(formRequest);

        return redirectTo(formRequest.getProductId());
    }

    private String redirectTo(@RequestParam String productId) {
        return "redirect:/product/" + productId + "/detail";
    }
}
