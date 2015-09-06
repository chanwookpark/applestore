package applestore.domain.order.entity;

import applestore.domain.common.AbstractEntity;
import applestore.domain.product.entity.Sku;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * @author chanwook
 */
@Entity
@Table(name = "ORD_ORDER_ITEM")
public class OrderItem extends AbstractEntity {

    @Id
    @GeneratedValue
    private long orderItemId;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof OrderItem)) return false;

        OrderItem compare = (OrderItem) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(orderItemId, compare.orderItemId);
        eb.append(orderSku.getSkuId(), compare.orderSku.getSkuId());

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(orderItemId);
        hcb.append(orderSku.getSkuId());
        return hcb.toHashCode();
    }
}
