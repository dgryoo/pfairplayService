package com.example.pfairplayservice.cassandra.pk;

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
public class TeamReviewByReviewerTidPrimaryKey implements Serializable {

    @PrimaryKeyColumn(name = "reviewer_tid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String reviewerTid;

    @PrimaryKeyColumn(name = "write_date", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date writeDate;

}
