package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {

    @Query(value = "SELECT * FROM soccer_match m  where m.owner_team_tid = :tid", nativeQuery = true)
    List<MatchEntity> findAllByTid(@Param("tid") String tid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE soccer_match m set m.play_ground_no = :groundNo where m.match_no = :matchNo", nativeQuery = true)
    void updateGroundNoByMatchNo(@Param("matchNo") int matchNo, @Param("groundNo") int groundNumber);

    @Modifying
    @Transactional
    @Query(value = "UPDATE soccer_match m set m.price = :price where m.match_no = :matchNo", nativeQuery = true)
    void updatePriceByMatchNo(@Param("matchNo") int matchNo, @Param("price") int price);

    @Modifying
    @Transactional
    @Query(value = "UPDATE soccer_match m set m.start_Date = :startDate where m.match_no = :matchNo", nativeQuery = true)
    void updateStartDateByMatchNo(@Param("matchNo") int matchNo, @Param("startDate") Date startDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE soccer_match m set m.end_date = :endDate where m.match_no = :matchNo", nativeQuery = true)
    void updateEndDateByMatchNo(@Param("matchNo") int matchNo, @Param("endDate") Date endDate);

    @Modifying
    @Transactional
    @Query(value = "UPDATE soccer_match m set m.message = :message where m.match_no = :matchNo", nativeQuery = true)
    void updateMessageByMatchNo(@Param("matchNo") int matchNo, @Param("message") String message);

    @Modifying
    @Transactional
    @Query(value = "UPDATE soccer_match m set m.status = 1 where m.match_no = :matchNo", nativeQuery = true)
    void dealMatch(@Param("matchNo") int matchNo);

    @Modifying
    @Transactional
    @Query(value = "UPDATE soccer_match m set m.guest_team_tid = :guestTeam where m.match_no = :matchNo", nativeQuery = true)
    void dealMatch(@Param("matchNo") int matchNo, @Param("guestTeam") String guestTeam);


}
