package project.pfairplay.storage.cassandra.repository;



import project.pfairplay.storage.cassandra.model.TeamReviewByReviewerTid;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import project.pfairplay.storage.cassandra.pk.TeamReviewByReviewerTidPrimaryKey;

import java.util.List;

public interface TeamReviewByReviewerTidRepository extends CassandraRepository<TeamReviewByReviewerTid, TeamReviewByReviewerTidPrimaryKey> {
    @Query(value = "select * from team_review_by_reviewer_tid where reviewer_tid = ?0 limit ?1")
    List<TeamReviewByReviewerTid> findTeamReviewByReviewerTid(String reviewerTid, int limit);
}
