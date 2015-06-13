package applestore.admin.catalog.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chanwook
 */
public class ProductGridForm {
    private List<ProductGridRow> rowList = new ArrayList<ProductGridRow>();

    public List<ProductGridRow> getRowList() {
        return rowList;
    }

    public void setRowList(List<ProductGridRow> rowList) {
        this.rowList = rowList;
    }
}
