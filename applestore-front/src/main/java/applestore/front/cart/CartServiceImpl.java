package applestore.front.cart;

import applestore.domain.order.entity.OrderItem;
import applestore.domain.order.repository.OrderItemJpaRepository;
import applestore.framework.workflow.ProcessEngine;
import applestore.framework.workflow.RequestMap;
import applestore.framework.workflow.ResponseMap;
import applestore.front.product.ProductForCartRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chanwook
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ProcessEngine engineBean;

    @Autowired
    OrderItemJpaRepository oir;

    @Transactional
    @Override
    public OrderItem createOrderItem(ProductForCartRequest cartRequest) {

        // 우선 1개 activity로 만들어 보자
        final RequestMap requestMap = new RequestMap();
        requestMap.put("cartRequest", cartRequest);

        final ResponseMap response = engineBean.process("addItemToCart", requestMap);
        OrderItem orderItem = (OrderItem) response.get("createOrderItem");
        return orderItem;
    }

    @Override
    public List<OrderItem> getOrderItem(List<Long> idList) {
        List<OrderItem> itemList = oir.findByOrderItemIdIn(idList);
        return itemList;
    }

}
