package com.example.pfairplayservice.cassandra.model;

import com.example.pfairplayservice.cassandra.pk.TeamReviewByReviewerTidPrimaryKey;
import com.example.pfairplayservice.cassandra.pk.TeamReviewByTidPrimaryKey;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table(value = "team_review_by_reviewer_tid")
public class TeamReviewByReviewerTid {

    @PrimaryKey
    private TeamReviewByReviewerTidPrimaryKey teamReviewByReviewerTidPrimaryKey;

    @Column(value = "review_id")
    private String reviewId;

    @Column(value = "tid")
    private String tid;

    @Column(value = "review_detail")
    private String reviewDetail;

    @Column(value = "proper_team_level")
    private int properTeamLevel;

    @Column(value = "team_manner_point")
    private int teamMannerPoint;

}
