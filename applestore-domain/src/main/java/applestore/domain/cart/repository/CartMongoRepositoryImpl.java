package applestore.domain.cart.repository;

import applestore.domain.cart.document.Cart;
import applestore.domain.cart.document.CartStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author chanwook
 */
public class CartMongoRepositoryImpl implements CartMongoRepositoryCustom {
    @Autowired
    MongoTemplate template;

    @Override
    public Cart findOpenCart(String userId) {
        final Query query =
                query(where("owner").is(userId)
                        .and("status").is(CartStatus.OPEN));
        Cart cart = template.findOne(query, Cart.class);
        return cart;
    }
}
