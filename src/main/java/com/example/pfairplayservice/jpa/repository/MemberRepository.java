package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

//    @Query(value = "SELECT * FROM test.member m WHERE m.uid in (SELECT mt.uid FROM test.member_team_list mt where mt.tid = :tid)", nativeQuery = true)
    @Query(value = "SELECT * FROM member m WHERE m.uid in (SELECT mt.uid FROM member_team_list mt where mt.tid = :tid)", nativeQuery = true)
    List<MemberEntity> findByMemberTeamIdTid(@Param("tid") String tid);
    @Query(value = "SELECT m FROM member m where m.id = :memberId", nativeQuery = true)
    Optional<MemberEntity> findByMemberId(@Param("memberId") String memberId);

}
