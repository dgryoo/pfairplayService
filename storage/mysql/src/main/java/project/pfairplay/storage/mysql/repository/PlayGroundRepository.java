package project.pfairplay.storage.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.pfairplay.storage.mysql.model.PlayGroundEntity;

import java.util.List;

@Repository
public interface PlayGroundRepository extends JpaRepository<PlayGroundEntity, Integer> {

    @Query(value = "select * from play_ground pg where pg.name like :%name%", nativeQuery = true)
    List<PlayGroundEntity> findPlayGroundListByGroundName(@Param("name") String name);

    @Query(value = "select * from play_ground pg where replace(pg.main_address, ' ', '') like :mainAddress%", nativeQuery = true)
    List<PlayGroundEntity> findPlayGroundListByMainAddress(@Param("mainAddress") String mainAddress);
}
