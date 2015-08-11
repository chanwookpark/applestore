package applestore.domain.cart.entity;

/**
 * @author chanwook
 */
public class CartException extends RuntimeException {
    public CartException(String message) {
        super(message);
    }
}
