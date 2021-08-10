package cassandra.repository;

import cassandra.model.TeamReviewByReviewerTid;
import cassandra.pk.TeamReviewByReviewerTidPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface TeamReviewByReviewerTidRepository extends CassandraRepository<TeamReviewByReviewerTid, TeamReviewByReviewerTidPrimaryKey> {
    @Query(value = "select * from team_review_by_reviewer_tid where reviewer_tid = ?0 limit ?1")
    List<TeamReviewByReviewerTid> findTeamReviewByReviewerTid(String reviewerTid, int limit);
}
