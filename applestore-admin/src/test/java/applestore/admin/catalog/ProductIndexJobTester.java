package applestore.admin.catalog;

import applestore.admin.AppleStoreAdminApp;
import applestore.admin.catalog.index.ProductIndexJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chanwook
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppleStoreAdminApp.class)
public class ProductIndexJobTester {

    @Autowired
    ProductIndexJob job;

    @Test
    public void createSolrProductIndex() throws Exception {
        //TODO 전후 처리~~훌라~~
        job.indexing();
    }
}
