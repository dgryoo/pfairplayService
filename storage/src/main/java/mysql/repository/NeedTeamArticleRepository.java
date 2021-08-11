package mysql.repository;

import mysql.model.NeedTeamArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface NeedTeamArticleRepository extends JpaRepository<NeedTeamArticleEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE need_team_article nta set nta.subject = :subject where nta.article_no = :articleNo", nativeQuery = true)
    void updateSubjectByArticleNo(@Param("articleNo")int articleNo, @Param("subject")String subject);

    @Modifying
    @Transactional
    @Query(value = "UPDATE need_team_article nta set nta.detail = :detail where nta.article_no = :articleNo", nativeQuery = true)
    void updateDetailByArticleNo(@Param("articleNo")int articleNo, @Param("detail")String detail);

    @Modifying
    @Transactional
    @Query(value = "UPDATE need_team_article nta set nta.need_position = :needPosition where nta.article_no = :articleNo", nativeQuery = true)
    void updateNeedPositionByArticleNo(@Param("articleNo")int articleNo, @Param("needPosition")int needPosition);

    @Modifying
    @Transactional
    @Query(value = "UPDATE need_team_article nta set nta.modified_date = now() where nta.article_no = :articleNo", nativeQuery = true)
    void updateModifiedDateByArticleNo(@Param("articleNo")int articleNo);
}
