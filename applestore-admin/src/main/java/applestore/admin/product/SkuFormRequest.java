package applestore.admin.product;

import applestore.domain.product.entity.Sku;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chanwook
 */
public class SkuFormRequest {
    Set<Sku> skuList = new HashSet<Sku>();

    public SkuFormRequest() {
    }

    public SkuFormRequest(Set<Sku> skuList) {
        this.skuList = skuList;
    }

    public Set<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(Set<Sku> skuList) {
        this.skuList = skuList;
    }
}
