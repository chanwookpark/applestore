package applestore.api.catalog.model.jpa;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chanwook
 */
@Entity
public class ProductIndexHistory {
    @Id
    @GeneratedValue
    private long id;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date execute;

    @Column
    private long count;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getExecute() {
        return execute;
    }

    public void setExecute(Date execute) {
        this.execute = execute;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
