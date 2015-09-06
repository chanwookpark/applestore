package applestore.domain.cart.repository;

import applestore.domain.cart.document.Cart;

/**
 * @author chanwook
 */
public interface CartMongoRepositoryCustom {

    Cart findOpenCart(String userId);
}
