package applestore.domain.product;

import applestore.framework.ApplicationException;

/**
 * @author chanwook
 */
public class ProductException extends ApplicationException {
    public ProductException(String message) {
        super(message);
    }
}
