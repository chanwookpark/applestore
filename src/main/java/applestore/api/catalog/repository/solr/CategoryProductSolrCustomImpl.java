package applestore.api.catalog.repository.solr;

import applestore.api.catalog.model.solr.CategoryProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;

/**
 * Created by chanwook on 2015. 6. 9..
 */
public class CategoryProductSolrCustomImpl implements CategoryProductSolrCustom {

    @Autowired
    SolrTemplate solrTemplate;

    @Override
    public Page<CategoryProduct> findByCategoryId(long categoryId, Pageable request) {
        Criteria criteria = new Criteria("categoryId_l").is(categoryId);
        SimpleQuery query = new SimpleQuery(criteria);
        query.setPageRequest(request);
        return solrTemplate.queryForPage(query, CategoryProduct.class);
    }

}
