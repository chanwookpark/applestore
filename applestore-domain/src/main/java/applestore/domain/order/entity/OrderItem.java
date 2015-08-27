package applestore.domain.order.entity;

import applestore.domain.product.entity.Sku;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chanwook
 */
@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private long orderItemId;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column(nullable = false)
    private int orderQuantity;

    @Column(nullable = false)
    private long orderPrice;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderSkuId", nullable = false, updatable = false)
    private Sku orderSku;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "orderId")
    private Order order;

    public OrderItem() {
    }

    public OrderItem(Sku orderSku, OrderItemStatus status) {
        this.orderSku = orderSku;
        this.status = status;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Sku getOrderSku() {
        return orderSku;
    }

    public void setOrderSku(Sku orderSku) {
        this.orderSku = orderSku;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
