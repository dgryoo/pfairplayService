package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {

    @Query(value = "SELECT * FROM team t join member m on t.team_lead_member_uid = m.uid where t.tid in (SELECT mt.tid FROM member_team_list mt where mt.uid = :uid)", nativeQuery = true)
    List<TeamEntity> findByMemberTeamIdUid(@Param("uid") String uid);

}
