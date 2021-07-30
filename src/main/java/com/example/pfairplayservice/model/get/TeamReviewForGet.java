package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.cassandra.model.TeamReviewByReviewerTid;
import com.example.pfairplayservice.cassandra.model.TeamReviewByTid;
import com.example.pfairplayservice.cassandra.model.TeamReviewCounter;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TeamReviewForGet {

    private String tid;

    private Date writeDate;

    private String reviewId;

    private String reviewerTid;

    private String reviewDetail;

    private Integer properTeamLevel;

    private Integer teamMannerPoint;

    private int thumbsUpCount;

    private int thumbsDownCount;

    public static TeamReviewForGet fromReviewAndCounter(TeamReviewByTid review, TeamReviewCounter counter) {
        return TeamReviewForGet.builder()
                .tid(review.getTeamReviewByTidPrimaryKey().getTid())
                .writeDate(review.getTeamReviewByTidPrimaryKey().getWriteDate())
                .reviewId(review.getReviewId())
                .reviewerTid(review.getReviewerTid())
                .reviewDetail(review.getReviewDetail())
                .properTeamLevel(review.getProperTeamLevel())
                .teamMannerPoint(review.getTeamMannerPoint())
                .thumbsDownCount(counter.getThumbsUpCount())
                .thumbsDownCount(counter.getThumbsDownCount())
                .build();
    }

    public static TeamReviewForGet fromReviewAndCounter(TeamReviewByReviewerTid review, TeamReviewCounter counter) {
        return TeamReviewForGet.builder()
                .tid(review.getTid())
                .writeDate(review.getTeamReviewByReviewerTidPrimaryKey().getWriteDate())
                .reviewId(review.getReviewId())
                .reviewerTid(review.getTeamReviewByReviewerTidPrimaryKey().getReviewerTid())
                .reviewDetail(review.getReviewDetail())
                .properTeamLevel(review.getProperTeamLevel())
                .teamMannerPoint(review.getTeamMannerPoint())
                .thumbsDownCount(counter.getThumbsUpCount())
                .thumbsDownCount(counter.getThumbsDownCount())
                .build();
    }
}
