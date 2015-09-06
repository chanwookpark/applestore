package applestore.domain.cart.repository;

import applestore.domain.cart.document.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chanwook
 */
public interface CartMongoRepository extends MongoRepository<Cart, String>, CartMongoRepositoryCustom {

}
