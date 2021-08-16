package project.pfairplay.storage.cassandra.pk;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Date;

@PrimaryKeyClass
@EqualsAndHashCode
@Data
@Builder
public class TeamReviewByTidPrimaryKey implements Serializable {

    @PrimaryKeyColumn(name = "tid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String tid;

    @PrimaryKeyColumn(name = "write_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date writeDate;

    @PrimaryKeyColumn(name = "review_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String reviewId;

}
