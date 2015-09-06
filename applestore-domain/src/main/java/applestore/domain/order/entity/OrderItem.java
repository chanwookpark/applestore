package applestore.domain.order.entity;

import applestore.domain.common.AbstractEntity;
import applestore.domain.product.entity.Sku;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * @author chanwook
 */
@Entity
@Table(name = "ORD_ORDER_ITEM")
@Getter
@Setter
@AllArgsConstructor
@Slf4j
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
