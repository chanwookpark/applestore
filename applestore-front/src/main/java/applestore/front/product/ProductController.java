package applestore.front.product;

import applestore.domain.product.entity.Product;
import applestore.framework.ApplicationException;
import applestore.front.product.dto.ProductDTO;
import applestore.front.product.dto.ProductImageDTO;
import applestore.front.product.dto.SkuDTO;
import applestore.front.product.service.ProductService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import r2.dustjs.spring.DustModel;

import java.util.List;

/**
 * @author chanwook
 */
@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // TODO 가격은 기본 sku로 하고, sku에 추가로 가격이 적혀 있으면 그걸로 판다.
    // 없으면 기본 sku 사용
    @RequestMapping("/product/{productId}")
    public String getView(@PathVariable String productId, DustModel model) {

        // 상품 validation: status,
        Product product = productService.getProduct(productId);

        if (!product.salesEnable()) {
            throw new ApplicationException("구매가 불가능한 상품입니다!");
        }

        model.bind("product", product, ProductDTO.class);
        model.bind("defaultSku", product.getDefaultSku(), SkuDTO.class);
        model.bind("imageList", product.getImageList(), new TypeToken<List<ProductImageDTO>>(){}); // 더 간소화 할 방법은?@@
        model.bind("skuList", product.getSkuList(), new TypeToken<List<SkuDTO>>(){}); // 더 간소화 할 방법은?@@

        return "product";
    }
}
