package applestore.domain.cart;

import applestore.domain.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
public class Cart {

    private String owner;
    private List<OrderItem> itemList = new ArrayList<OrderItem>();

    public Cart() {
    }

    public Cart(String owner) {
        this.owner = owner;
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
}
