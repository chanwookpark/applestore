package applestore.admin.product;

import applestore.admin.product.service.ProductManagementService;
import applestore.domain.product.entity.Product;
import applestore.domain.product.repository.ProductJpaRepository;
import applestore.domain.product.entity.ProductAttribute;
import applestore.domain.product.repository.ProductAttributeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/product/{productId}/attribute", method = RequestMethod.GET)
    public String attributeFrom(@PathVariable String productId, ModelMap model) {
        final Product product = pr.findOne(productId);
        final List<Long> list = toIdList(product);
        List<ProductAttribute> attrList;
        if (list.size() > 0) {
            attrList = par.findByAttributeIdNotIn(list);
        } else {
            attrList = par.findAll();
        }

        model.put("product", product);
        model.put("attrList", attrList);
        model.put("productId", productId);

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

        return "redirect:/product/" + productId + "/attribute";
    }
}
