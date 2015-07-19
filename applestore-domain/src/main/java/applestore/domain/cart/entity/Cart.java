package applestore.domain.cart.entity;

import applestore.domain.order.OrderItem;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chanwook
 */
@Document
public class Cart {

    @Id
    private String id;

    private String owner;

    private List<OrderItem> itemList = new ArrayList<OrderItem>();

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
        this.itemList.add(item);
    }

    public List<OrderItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItem> itemList) {
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
}
