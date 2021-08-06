package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.cassandra.model.TeamReviewByReviewerTid;
import com.example.pfairplayservice.cassandra.model.TeamReviewByTid;
import com.example.pfairplayservice.cassandra.pk.TeamReviewByReviewerTidPrimaryKey;
import com.example.pfairplayservice.cassandra.pk.TeamReviewByTidPrimaryKey;
import com.example.pfairplayservice.jpa.model.TeamReviewCounterEntity;
import com.example.pfairplayservice.jpa.model.TeamReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamReviewForPost {

    private String tid;

    private String reviewerTid;

    private String reviewDetail;

    private Integer properTeamLevel;

    private Integer teamMannerPoint;

    public TeamReviewByTid toTeamReviewByTid(Date writeDate, String reviewId) {
        return TeamReviewByTid.builder()
                .teamReviewByTidPrimaryKey(TeamReviewByTidPrimaryKey.builder()
                        .tid(tid)
                        .writeDate(writeDate)
                        .reviewId(reviewId)
                        .build())
                .reviewerTid(reviewerTid)
                .reviewDetail(reviewDetail)
                .properTeamLevel(properTeamLevel)
                .teamMannerPoint(teamMannerPoint)
                .build();
    }

    public TeamReviewByReviewerTid toTeamReviewByReviewerTid(Date writeDate, String reviewId) {
        return TeamReviewByReviewerTid.builder()
                .teamReviewByReviewerTidPrimaryKey(TeamReviewByReviewerTidPrimaryKey.builder()
                        .reviewerTid(reviewerTid)
                        .writeDate(writeDate)
                        .reviewId(reviewId)
                        .build())
                .tid(tid)
                .build();
    }

    public TeamReviewEntity toTeamReviewEntity(String reviewId) {
        return TeamReviewEntity.builder()
                .reviewId(reviewId)
                .tid(tid)
                .writeDate(new Date())
                .reviewerTid(reviewerTid)
                .reviewDetail(reviewDetail)
                .properTeamLevel(properTeamLevel)
                .teamMannerPoint(teamMannerPoint)
                .build();
    }

    public TeamReviewCounterEntity toTeamReviewCounterEntity(String reviewId) {
        return TeamReviewCounterEntity.builder()
                .reviewId(reviewId)
                .thumbsUpCount(0)
                .thumbsDownCount(0)
                .build();
    }
}
