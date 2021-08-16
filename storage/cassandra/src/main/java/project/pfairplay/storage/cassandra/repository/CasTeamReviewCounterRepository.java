package project.pfairplay.storage.cassandra.repository;


import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import project.pfairplay.storage.cassandra.model.TeamReviewCounter;
import project.pfairplay.storage.cassandra.pk.TeamReviewCounterPrimaryKey;

import java.util.List;
import java.util.Optional;

public interface CasTeamReviewCounterRepository extends CassandraRepository<TeamReviewCounter, TeamReviewCounterPrimaryKey> {

    @Query(value = "select * from team_review_counter where tid = ?0 and review_id in ?1")
    List<TeamReviewCounter> findTeamReviewCounterByTidAndReviewIdList(String tid, List<String> reviewIdCondition);

    @Query(value = "select * from team_review_counter where tid = ?0 and review_id = ?1")
    Optional<TeamReviewCounter> findTeamReviewCounterByTidAndReviewId(String tid, String reviewId);

}
