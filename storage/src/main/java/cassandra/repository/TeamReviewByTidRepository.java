package cassandra.repository;

import cassandra.model.TeamReviewByTid;
import cassandra.pk.TeamReviewByTidPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface TeamReviewByTidRepository extends CassandraRepository<TeamReviewByTid, TeamReviewByTidPrimaryKey> {
    @Query(value = "select * from team_review_by_tid where tid = ?0 limit ?1")
    List<TeamReviewByTid> findTeamReviewByTid(String tid, int limit);
}
