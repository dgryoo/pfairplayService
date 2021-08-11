package cassandra.model;

import cassandra.pk.TeamReviewByTidPrimaryKey;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@Table(value = "team_review_by_tid")
public class TeamReviewByTid {

    @PrimaryKey
    private TeamReviewByTidPrimaryKey teamReviewByTidPrimaryKey;

    @Column(value = "reviewer_tid")
    private String reviewerTid;

    @Column(value = "review_detail")
    private String reviewDetail;

    @Column(value = "proper_team_level")
    private int properTeamLevel;

    @Column(value = "team_manner_point")
    private int teamMannerPoint;

}
