package com.example.pfairplayservice.cassandra.repository;

import com.example.pfairplayservice.cassandra.model.TeamReviewCounter;
import com.example.pfairplayservice.cassandra.pk.TeamReviewCounterPrimaryKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface TeamReviewCounterRepository extends CassandraRepository<TeamReviewCounter, TeamReviewCounterPrimaryKey> {

    @Query(value = "select * from team_review_counter where tid = ?0 and review_id in (?1)")
    List<TeamReviewCounter> findTeamReviewCounterByTid(String tid, String reviewIdCondition);

    @Query(value = "update team_review_counter set thumbs_up_count = thumbs_up_count + 1 where tid = ?0 and review_id = ?1")
    void increaseThumbsUpByTidAndReviewId(String tid, String reviewId);

    @Query(value = "update team_review_counter set thumbs_up_count = thumbs_up_count - 1 where tid = ?0 and review_id = ?1")
    void decreaseThumbsUpByTidAndReviewId(String tid, String reviewId);

    @Query(value = "update team_review_counter set thumbs_down_count = thumbs_down_count + 1 where tid = ?0 and review_id = ?1")
    void increaseThumbsDownByTidAndReviewId(String tid, String reviewId);

    @Query(value = "update team_review_counter set thumbs_down_count = thumbs_down_count - 1 where tid = ?0 and review_id = ?1")
    void decreaseThumbsDownByTidAndReviewId(String tid, String reviewId);
}
