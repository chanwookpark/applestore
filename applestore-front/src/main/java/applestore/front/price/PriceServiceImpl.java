package applestore.front.price;

import applestore.domain.order.entity.OrderItem;
import applestore.domain.product.entity.Product;
import applestore.domain.product.entity.Sku;
import org.springframework.stereotype.Service;

/**
 * @author chanwook
 */
@Service
public class PriceServiceImpl implements PricingService {
    @Override
    public long getPrice(Product product, OrderItem orderItem) {
        final Sku orderSku = orderItem.getOrderSku();
        final long currentSalesPrice = orderSku.getSalesPrice();
        //TODO 다양한 가격 계산 로직 넣기
        return currentSalesPrice * orderItem.getOrderQuantity();
    }
}
