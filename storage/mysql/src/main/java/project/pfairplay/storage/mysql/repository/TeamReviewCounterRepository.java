package project.pfairplay.storage.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.pfairplay.storage.mysql.model.MatchEntity;
import project.pfairplay.storage.mysql.model.TeamReviewCounterEntity;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TeamReviewCounterRepository extends JpaRepository<TeamReviewCounterEntity, String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE team_review_counter trc set trc.thumbs_up_count = trc.thumbs_up_count + 1 where trc.review_id = :reviewId", nativeQuery = true)
    void increaseThumbsUpByReviewId(@Param("reviewId") String reviewId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE team_review_counter trc set trc.thumbs_up_count = trc.thumbs_up_count - 1 where trc.review_id = :reviewId", nativeQuery = true)
    void decreaseThumbsUpByReviewId(@Param("reviewId") String reviewId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE team_review_counter trc set trc.thumbs_down_count = trc.thumbs_down_count + 1 where trc.review_id = :reviewId", nativeQuery = true)
    void increaseThumbsDownByReviewId(@Param("reviewId") String reviewId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE team_review_counter trc set trc.thumbs_down_count = trc.thumbs_down_count - 1 where trc.review_id = :reviewId", nativeQuery = true)
    void decreaseThumbsDownByReviewId(@Param("reviewId") String reviewId);

    @Query(value = "SELECT * FROM team_review_counter trc  where trc.review_id in (:reviewIdCondition)", nativeQuery = true)
    List<TeamReviewCounterEntity> findTeamReviewByReviewIdList(@Param("reviewIdCondition") List<String> reviewIdCondition);
}
