package applestore.front.catalog;

import applestore.domain.catalog.entity.DisplayCategory;
import applestore.domain.catalog.repository.ProductIndexSolrRepository;
import applestore.domain.catalog.solr.ProductIndex;
import applestore.framework.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author chanwook
 */
@Service
public class ProductIndexServiceImpl implements ProductIndexService {

    private final Logger logger = LoggerFactory.getLogger(ProductIndexServiceImpl.class);

    @Autowired
    private ProductIndexSolrRepository productIndexRepository;

    @Override
    public List<String> getProductIndex(DisplayCategory category, Pageable pageRequest) {
        if (category == null) {
            throw new ApplicationException("카테고리 정보가 필수로 필요합니다!");
        }

        Page<ProductIndex> page =
                productIndexRepository.findByCategoryId(category.getCategoryId(), getIndexStrategy(pageRequest));

        if (logger.isDebugEnabled()) {
            logger.debug(">>상품 인덱스 조회" + page + "\n" + page.getContent());
        }
        if (page.getContent() == null) {
            throw new ApplicationException(category.getCategoryId() + "에 해당하는 상품 정보가 한 건도 등록되어 있지 않습니다!");
        }

        return toProductIdList(page.getContent());
    }

    private PageRequest getIndexStrategy(Pageable pageRequest) {
        // score 순으로 목록 반환
        return new PageRequest(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                new Sort(DESC, "score_l"));
    }

    private List<String> toProductIdList(List<ProductIndex> list) {
        List<String> idList = new ArrayList<String>(list.size());
        for (ProductIndex cp : list) {
            idList.add(cp.getProductId());
        }
        return idList;
    }
}
