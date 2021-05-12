package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {

    @Query(value = "SELECT * FROM team t join member m on t.team_lead_member_uid = m.uid where t.tid in (SELECT mt.tid FROM member_team mt where mt.uid = :uid)", nativeQuery = true)
    List<TeamEntity> findByMemberTeamIdUid(@Param("uid") String uid);

    @Query(value = "SELECT * FROM team t where t.team_name = :teamName", nativeQuery = true)
    List<TeamEntity> findByTeamName(@Param("teamName") String teamName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE team t set t.team_name  = :teamName where t.tid = :tid", nativeQuery = true)
    void updateTeamNameByTid(@Param("tid") String tid, @Param("teamName") String teamName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE team t set t.activity_area_address  = :activityAreaAddress where t.tid = :tid", nativeQuery = true)
    void updateActivityAreaAddressByTid(@Param("tid") String tid, @Param("activityAreaAddress") String activityAreaAddress);

    @Modifying
    @Transactional
    @Query(value = "UPDATE team t set t.found_date  = :foundDate where t.tid = :tid", nativeQuery = true)
    void updateFoundDateByTid(@Param("tid") String tid, @Param("foundDate") Date foundDate);
}
