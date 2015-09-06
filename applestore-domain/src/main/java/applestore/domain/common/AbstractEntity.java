package applestore.domain.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author chanwook
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @CreatedDate
    protected LocalDateTime createdDate;

    @LastModifiedDate
    @Version
    protected Date modifiedDate;

    protected AbstractEntity() {
    }
//    LocalDateTime modifiedDate; // VersionType으로 아직 지원하지 않아서 일단 Date로 사용함
}
