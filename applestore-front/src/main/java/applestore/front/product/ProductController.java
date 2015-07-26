package applestore.front.product;

import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import r2.dustjs.spring.DustModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // TODO 가격은 기본 sku로 하고, sku에 추가로 가격이 적혀 있으면 그걸로 판다. 없으면 기본 sku 사용
    @RequestMapping("/product/{productId}")
    public String getView(@PathVariable String productId, DustModel model) {

        // 상품 validation: status,
        Product product = productService.getProduct(productId);
        model.put("product", product);
        model.put("mergedSku", mergeSku(product));

        return "product";
    }

    private List<Sku> mergeSku(Product product) {
        List<Sku> allSku = new ArrayList<Sku>(product.getSkuList());
        allSku.add(product.getDefaultSku());
        //TODO 전시 순서 추가 (sort)
        return allSku;
    }
}
