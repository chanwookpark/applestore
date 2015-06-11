package applestore.front.catalog;

import applestore.front.catalog.service.CatalogIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chanwook
 */
@RestController
public class CatalogIndexRestController {

    @Autowired
    private CatalogIndexService is;

    @RequestMapping(value = "/c/category/{categoryId}/{productId}", method = RequestMethod.POST)
    public void clickInCategory(@PathVariable("categoryId") long categoryId,
                                @PathVariable("productId") String productId) {
        is.addClientEvent(categoryId, productId);
    }
}
