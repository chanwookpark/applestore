package applestore.domain.order.entity;

import applestore.domain.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
@Entity
@Table(name = "ORD_ORDER")
@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue
    private long orderId;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    public Order() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Order)) return false;

        Order compare = (Order) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(orderId, compare.orderId);
        eb.append(super.createdDate, compare.createdDate);

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(orderId);
        hcb.append(super.createdDate);
        return hcb.toHashCode();
    }
}
