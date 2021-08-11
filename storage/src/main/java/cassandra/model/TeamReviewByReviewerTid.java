package cassandra.model;

import cassandra.pk.TeamReviewByReviewerTidPrimaryKey;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@Table(value = "team_review_by_reviewer_tid")
public class TeamReviewByReviewerTid {

    @PrimaryKey
    private TeamReviewByReviewerTidPrimaryKey teamReviewByReviewerTidPrimaryKey;

    @Column(value = "tid")
    private String tid;

}
