package applestore.front.product;

import applestore.domain.catalog.repository.ProductJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author chanwook
 */
@Controller
public class ProductJspViewController {

    @Autowired
    private ProductJpaRepository pr;

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public String view(@PathVariable String productId, ModelMap model) {
        model.put("product", pr.getOne(productId));
        return "jsp/product";
    }
}
