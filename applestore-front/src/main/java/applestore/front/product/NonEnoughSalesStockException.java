package applestore.front.product;

import applestore.domain.product.ProductException;

/**
 * @author chanwook
 */
public class NonEnoughSalesStockException extends ProductException {
    public NonEnoughSalesStockException(long skuId, long salesStock, Integer orderQuantity) {
        super("SKU ID: " + skuId + ", 현재재고: " + salesStock + ", 구매요청 상품수: " + orderQuantity);
    }
}
