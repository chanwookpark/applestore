package applestore.admin.catalog.index;

import applestore.domain.catalog.entity.Product;
import applestore.domain.catalog.repository.DisplayCategoryJpaRepository;
import applestore.domain.catalog.repository.ProductIndexSolrRepository;
import applestore.domain.catalog.repository.ProductJpaRepository;
import applestore.domain.catalog.solr.ProductIndex;
import applestore.framework.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author chanwook
 */
@Component
public class ProductIndexJob {

    private final Logger logger = LoggerFactory.getLogger(ProductIndexJob.class);

    @Autowired
    ProductIndexSolrRepository sr;

    @Autowired
    ProductJpaRepository pr;

    @Autowired
    DisplayCategoryJpaRepository cr;

    //TODO 나중에 DB로 넣든 어디로 빼든 하자~
    private Date recentIndexingTime;

    @Transactional
    @Scheduled(fixedDelay = 15000, initialDelay = 30000)
    public void indexing() {
        Date startTime = DateUtils.now();
        List<Product> productList = findUpdatedProduct();
        long currentIndexCount = sr.count();

        int count = 0;
        for (Product p : productList) {

            final long categoryId = p.getDisplayCategory().getCategoryId();
            final String productId = p.getProductId();

            // 이미 존재하는 index(category - product 매핑)면 pass
            if (hasIndex(categoryId, productId)) {
                continue;
            }
            createIndex(categoryId, productId);

            ++count;
        }

        Date endTime = DateUtils.now();

        // Solr repository로 save를 하더라도 commit 전(tx 종료 전)이라면 sr.count()를 해도 새로 추가된 row가 반영된 숫자가 나오지 않는다.
        // 그래서 before나 after나 숫자가 동일하다. 다음에 돌려야 제대로 반영된 숫자가 나옴

        logger.info("상품 인덱스 JOB 실행 완료 [시작시간:" + startTime + ", 종료시간:" + endTime +
                ", 신규 인덱스 생성(Count): " + count + ", 작업전 인덱스 갯수: " + currentIndexCount + "]");

        this.recentIndexingTime = endTime;
    }

    private void createIndex(long categoryId, String productId) {
        ProductIndex index =
                new ProductIndex(String.valueOf(System.nanoTime()), productId, categoryId);
        sr.save(index);

        if (logger.isInfoEnabled()) {
            logger.info("상품 인덱스 생성 [" + index + "]");
        }
    }

    private boolean hasIndex(long categoryId, String productId) {
        return sr.findByCategoryIdAndProductId(categoryId, productId) != null;
    }

    private List<Product> findUpdatedProduct() {
        return pr.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.greaterThanOrEqualTo(root.get("updated"), getRecentIndexingTime());
            }
        });
    }

    private Date getRecentIndexingTime() {
        return recentIndexingTime;
    }
}
