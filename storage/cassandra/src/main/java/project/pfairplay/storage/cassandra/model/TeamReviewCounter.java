package project.pfairplay.storage.cassandra.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import project.pfairplay.storage.cassandra.pk.TeamReviewCounterPrimaryKey;

@Data
@Builder
@Table(value = "team_review_by_tid")
public class TeamReviewCounter {

    @PrimaryKey
    private TeamReviewCounterPrimaryKey teamReviewCounterPrimaryKey;

    @Column(value = "thumbs_up_count")
    private Integer thumbsUpCount;

    @Column(value = "thumbs_down_count")
    private Integer thumbsDownCount;

}
