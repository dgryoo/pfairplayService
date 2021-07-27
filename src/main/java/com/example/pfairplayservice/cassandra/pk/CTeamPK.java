package com.example.pfairplayservice.cassandra.pk;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

@PrimaryKeyClass
@EqualsAndHashCode
@Data
@Builder
public class CTeamPK implements Serializable {

    @PrimaryKeyColumn(name = "activity_area_address", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String activityAreaAddress;

    @PrimaryKeyColumn(name = "tid", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private String tid;

    @PrimaryKeyColumn(name = "recommend_count", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private int recommendCount;

}
