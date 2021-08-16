package project.pfairplay.api.model.get;

import lombok.Builder;
import lombok.Data;
import project.pfairplay.storage.cassandra.model.TeamReviewByTid;
import project.pfairplay.storage.cassandra.model.TeamReviewCounter;
import project.pfairplay.storage.mysql.model.TeamReviewCounterEntity;
import project.pfairplay.storage.mysql.model.TeamReviewEntity;

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

    private Integer thumbsUpCount;

    private Integer thumbsDownCount;

    public static TeamReviewForGet fromReviewAndCounter(TeamReviewByTid review, TeamReviewCounter counter) {

        if (counter != null) {
            return TeamReviewForGet.builder()
                    .tid(review.getTeamReviewByTidPrimaryKey().getTid())
                    .writeDate(review.getTeamReviewByTidPrimaryKey().getWriteDate())
                    .reviewId(review.getTeamReviewByTidPrimaryKey().getReviewId())
                    .reviewerTid(review.getReviewerTid())
                    .reviewDetail(review.getReviewDetail())
                    .properTeamLevel(review.getProperTeamLevel())
                    .teamMannerPoint(review.getTeamMannerPoint())
                    .thumbsUpCount(counter.getThumbsUpCount())
                    .thumbsDownCount(counter.getThumbsDownCount())
                    .build();
        }

        return TeamReviewForGet.builder()
                .tid(review.getTeamReviewByTidPrimaryKey().getTid())
                .writeDate(review.getTeamReviewByTidPrimaryKey().getWriteDate())
                .reviewId(review.getTeamReviewByTidPrimaryKey().getReviewId())
                .reviewerTid(review.getReviewerTid())
                .reviewDetail(review.getReviewDetail())
                .properTeamLevel(review.getProperTeamLevel())
                .teamMannerPoint(review.getTeamMannerPoint())
                .build();

    }

    public static TeamReviewForGet fromReviewAndCounter(TeamReviewEntity review, TeamReviewCounterEntity counter) {

        if (counter != null) {
            return TeamReviewForGet.builder()
                    .tid(review.getTid())
                    .writeDate(review.getWriteDate())
                    .reviewId(review.getReviewId())
                    .reviewerTid(review.getReviewerTid())
                    .reviewDetail(review.getReviewDetail())
                    .properTeamLevel(review.getProperTeamLevel())
                    .teamMannerPoint(review.getTeamMannerPoint())
                    .thumbsUpCount(counter.getThumbsUpCount())
                    .thumbsDownCount(counter.getThumbsDownCount())
                    .build();
        }

        return TeamReviewForGet.builder()
                .tid(review.getTid())
                .writeDate(review.getWriteDate())
                .reviewId(review.getReviewId())
                .reviewerTid(review.getReviewerTid())
                .reviewDetail(review.getReviewDetail())
                .properTeamLevel(review.getProperTeamLevel())
                .teamMannerPoint(review.getTeamMannerPoint())
                .build();

    }
}
