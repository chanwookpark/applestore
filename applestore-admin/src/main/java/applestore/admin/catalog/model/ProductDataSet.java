package applestore.admin.catalog.model;

import applestore.domain.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
public class ProductDataSet {
    private List<Product> createList = new ArrayList<Product>();

    private List<Product> updateList = new ArrayList<Product>();

    public ProductDataSet() {
    }

    public ProductDataSet(List<Product> createList, List<Product> updateList) {
        this.createList = createList;
        this.updateList = updateList;
    }

    public List<Product> getCreateList() {
        return createList;
    }

    public void setCreateList(List<Product> createList) {
        this.createList = createList;
    }

    public List<Product> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<Product> updateList) {
        this.updateList = updateList;
    }

    public void addUpdate(Product p) {
        this.updateList.add(p);
    }

    public void addCreate(Product p) {
        this.createList.add(p);
    }
}
