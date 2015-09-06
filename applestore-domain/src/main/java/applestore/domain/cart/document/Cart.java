package applestore.domain.cart.document;

import applestore.domain.cart.CartException;
import applestore.domain.order.entity.OrderItem;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chanwook
 */
@Document
public class Cart implements Serializable {

    @Id
    private String id;

    private String owner;

    private List<Long> itemList = new ArrayList<Long>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd hh:mm")
    private Date updated;

    private CartStatus status;

    public Cart() {
    }

    public Cart(String owner, CartStatus status, Date date) {
        this.owner = owner;
        this.status = status;
        this.updated = date;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new CartException("카트에 추가하는 아이템이 비어있습니다!");
        }
        this.itemList.add(item.getOrderItemId());
    }

    public List<Long> getItemList() {
        return itemList;
    }

    public void setItemList(List<Long> itemList) {
        this.itemList = itemList;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Cart)) return false;

        Cart compare = (Cart) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(id, compare.id);

        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(id);
        return hcb.toHashCode();
    }
}
