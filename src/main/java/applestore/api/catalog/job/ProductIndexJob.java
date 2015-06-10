package applestore.api.catalog.job;

import applestore.api.catalog.model.jpa.Product;
import applestore.api.catalog.model.solr.ProductIndex;
import applestore.api.catalog.repository.jpa.DisplayCategoryJpaRepository;
import applestore.api.catalog.repository.jpa.ProductJpaRepository;
import applestore.api.catalog.repository.solr.ProductIndexSolrRepository;
import applestore.api.framework.DateUtils;
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

    @Transactional
    @Scheduled(fixedDelay = 60000, initialDelay = 60000)
    public void indexing() {
        Date startDate = DateUtils.now();
        List<Product> productList = findUpdatedProduct();
        long beforeIndexCount = sr.count();

        int count = 0;
        for (Product p : productList) {
            // 기존 인덱스 정보가 있다면? 일단 지우자
            try {
                sr.deleteByProductId(p.getProductId());
            } catch (Throwable te) {
                logger.error(p.getProductId() + "에 해당하는 인덱스 정보 삭제 실패", te);
            }

            ProductIndex index =
                    new ProductIndex(String.valueOf(System.nanoTime()),
                            p.getProductId(), p.getDisplayCategory().getCategoryId());
            sr.save(index);

            if (logger.isInfoEnabled()) {
                logger.info("상품 인덱스 생성 완료 [" + index + "]");
            }

            ++count;
        }

        long afterIndexCount = sr.count();

        logger.info("상품 인덱스 생성 완료 [시작시간:" + startDate + ", 생성갯수(Count): " + count +
                ", 작업전 인덱스 갯수: " + beforeIndexCount + ", 작업후 인덱스 갯수: " + afterIndexCount + "]");
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
        return DateUtils.getDate("2015-01-01 09:00:00");
    }
}
