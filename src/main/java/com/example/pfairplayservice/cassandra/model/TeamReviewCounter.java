package com.example.pfairplayservice.cassandra.model;

import com.example.pfairplayservice.cassandra.pk.TeamReviewByTidPrimaryKey;
import com.example.pfairplayservice.cassandra.pk.TeamReviewCounterPrimaryKey;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table(value = "team_review_by_tid")
public class TeamReviewCounter {

    @PrimaryKey
    private TeamReviewCounterPrimaryKey teamReviewCounterPrimaryKey;

    @Column(value = "thumbs_up_count")
    private int thumbsUpCount;

    @Column(value = "thumbs_down_count")
    private int thumbsDownCount;

}
