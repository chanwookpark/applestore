package applestore.front.product;

import applestore.domain.catalog.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import r2.dustjs.spring.DustModel;

/**
 * @author chanwook
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/product/{productId}")
    public String getView(@PathVariable String productId, DustModel model) {

        Product product = productService.getProduct(productId);
        model.put("product", product);

        return "product";
    }
}
