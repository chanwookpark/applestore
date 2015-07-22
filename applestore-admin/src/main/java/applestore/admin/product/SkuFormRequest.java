package applestore.admin.product;

import applestore.domain.product.entity.Sku;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
public class SkuFormRequest {
    List<Sku> skuList = new ArrayList<Sku>();

    public SkuFormRequest() {
    }

    public SkuFormRequest(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
