package project.pfairplay.storage.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import project.pfairplay.storage.cassandra.model.TeamReviewByTid;
import project.pfairplay.storage.cassandra.pk.TeamReviewByTidPrimaryKey;

import java.util.List;

public interface TeamReviewByTidRepository extends CassandraRepository<TeamReviewByTid, TeamReviewByTidPrimaryKey> {
    @Query(value = "select * from team_review_by_tid where tid = ?0 limit ?1")
    List<TeamReviewByTid> findTeamReviewByTid(String tid, int limit);
}
