package applestore.front.catalog;

import applestore.domain.catalog.repository.ProductIndexSolrRepository;
import applestore.domain.catalog.solr.ProductIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chanwook
 */
@Service
@Transactional
public class CatalogIndexServiceImpl implements CatalogIndexService {
    private final Logger logger = LoggerFactory.getLogger(CatalogIndexServiceImpl.class);

    @Autowired
    private ProductIndexSolrRepository sr;

    @Autowired
    private SolrTemplate t;

    @Override
    public void addClientEvent(long categoryId, String productId) {
        ProductIndex index = sr.findByCategoryIdAndProductId(categoryId, productId);
        if (index != null) {
            // score 갱신하고 다시 저장
            PartialUpdate pu = new PartialUpdate("id", index.getId());
            pu.add("score_l", index.getScore() + 1);
            t.saveBean(pu);

            if (logger.isDebugEnabled()) {
                logger.debug("카테고리의 상품 클릭 이벤트 기록(" + index.getId() + ", " + categoryId + ", " + productId + ")");
            }
        } else {
            logger.warn("상품 인덱스 정보가 없어 클릭 이벤트 기록을 못했습니다(" + categoryId + ", " + productId + ")");
        }
    }
}
