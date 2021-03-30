package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

//    @Query(value = "SELECT * FROM test.member m WHERE m.uid in (SELECT mt.uid FROM test.member_team_list mt where mt.tid = :tid)", nativeQuery = true)
    @Query(value = "SELECT * FROM member m WHERE m.uid in (SELECT mt.uid FROM member_team_list mt where mt.tid = :tid)", nativeQuery = true)
    List<MemberEntity> findByMemberTeamIdTid(@Param("tid") String tid);
    @Query(value = "SELECT * FROM member m where m.id = :memberId", nativeQuery = true)
    Optional<MemberEntity> findByMemberId(@Param("memberId") String memberId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member m set m.password = :password where m.uid = :uid", nativeQuery = true)
    void updatePasswordByUid(@Param("uid")String uid, @Param("password")String password);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member m set m.address = :address where m.uid = :uid", nativeQuery = true)
    void updateAddressByUid(@Param("uid")String uid, @Param("address")String address);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member m set m.phone_number = :phoneNumber where m.uid = :uid", nativeQuery = true)
    void updatePhoneNumberByUid(@Param("uid")String uid, @Param("phoneNumber")String phoneNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member m set m.prefer_position = :preferPosition where m.uid = :uid", nativeQuery = true)
    void updatePreferPositionByUid(@Param("uid")String uid, @Param("preferPosition")Integer preferPosition);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member m set m.level = :level where m.uid = :uid", nativeQuery = true)
    void updateLevelByUid(@Param("uid")String uid, @Param("level")Integer level);

    @Modifying
    @Transactional
    @Query(value = "UPDATE member m set m.phone_number_disclosure_option = :phoneNumberDisclosureOption where m.uid = :uid", nativeQuery = true)
    void updatePhoneNumberDisclosureOptionByUid(@Param("uid")String uid, @Param("phoneNumberDisclosureOption")Integer phoneNumberDisclosureOption);

}
