package project.pfairplay.storage.cassandra.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import project.pfairplay.storage.cassandra.pk.TeamReviewByReviewerTidPrimaryKey;

@Data
@Builder
@Table(value = "team_review_by_reviewer_tid")
public class TeamReviewByReviewerTid {

    @PrimaryKey
    private TeamReviewByReviewerTidPrimaryKey teamReviewByReviewerTidPrimaryKey;

    @Column(value = "tid")
    private String tid;

}
