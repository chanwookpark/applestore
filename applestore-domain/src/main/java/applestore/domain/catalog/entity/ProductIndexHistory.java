package applestore.domain.catalog.entity;

import applestore.domain.common.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * @author chanwook
 */
@Entity
@Table(name = "CAT_PRD_INDEX_HISTORY")
@Getter
@Setter
@AllArgsConstructor
@ToString
@Slf4j
public class ProductIndexHistory extends AbstractEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date execute;

    @Column
    private long count;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ProductIndexHistory)) return false;

        ProductIndexHistory compare = (ProductIndexHistory) obj;
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
