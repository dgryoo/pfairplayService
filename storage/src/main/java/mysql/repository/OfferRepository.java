package mysql.repository;

import mysql.model.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Integer> {

    @Query(value = "select * from offer o where o.target_match_match_no = :matchNo and o.sand_team_tid = :sandTid and receive_team_tid = :receiveTid", nativeQuery = true)
    Optional<OfferEntity> findDuplicatedOfferByTidAndMid(@Param("matchNo") int matchNo, @Param("sandTid") String sandTid, @Param("receiveTid") String receiveTid);

    @Modifying
    @Transactional
    @Query(value = "update offer o set o.offer_status = 1 where offer_no = :offerNo" , nativeQuery = true)
    void acceptOffer(@Param("offerNo") int offerNo);

    @Modifying
    @Transactional
    @Query(value = "update offer o set o.offer_status = 2 where offer_no = :offerNo" , nativeQuery = true)
    void rejectOffer(@Param("offerNo") int offerNo);

}
