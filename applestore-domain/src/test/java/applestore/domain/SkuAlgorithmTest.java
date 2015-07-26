package applestore.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chanwook
 */
public class SkuAlgorithmTest {

    @Test
    public void createSku() throws Exception {
        Attr[] attrList = new Attr[]{
                new Attr(1, Arrays.asList(new Value(1, "1"), new Value(2, "2"))),
                new Attr(2, Arrays.asList(new Value(3, "3"), new Value(4, "4"), new Value(5, "5")))};

        magic(attrList);
    }

    @Test
    public void magic2() throws Exception {
        Attr[] attrList = new Attr[]{new Attr(1, Arrays.asList(new Value(1, "1"), new Value(2, "2"))),
                new Attr(2, Arrays.asList(new Value(3, "3"), new Value(4, "4"), new Value(5, "5")))};

        List<List<Long>> result = new ArrayList<List<Long>>();
        for (Attr a : attrList) {
            final List<Value> vl = a.getValueList();
            for (Value v : vl) {
                for (List<Long> list : result) {
                    list.add(v.getValueId());
                }
            }
        }
        System.out.println(result.toString());

    }

    private void magic(Attr[] attrList) {

        for (Attr attr : attrList) {
            loop(attr);
        }
    }

    private void loop(Attr attr) {
        for (Value value : attr.getValueList()) {

        }
    }

    static class Attr {
        long attrId;

        List<Value> valueList = new ArrayList<Value>();

        public Attr(long attrId, List<Value> valueList) {
            this.attrId = attrId;
            this.valueList = valueList;
        }

        public long getAttrId() {
            return attrId;
        }

        public void setAttrId(long attrId) {
            this.attrId = attrId;
        }

        public List<Value> getValueList() {
            return valueList;
        }

        public void setValueList(List<Value> valueList) {
            this.valueList = valueList;
        }
    }

    static class Value {
        long valueId;

        String value;

        public Value(long valueId, String value) {
            this.valueId = valueId;
            this.value = value;
        }

        public long getValueId() {
            return valueId;
        }

        public void setValueId(long valueId) {
            this.valueId = valueId;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
