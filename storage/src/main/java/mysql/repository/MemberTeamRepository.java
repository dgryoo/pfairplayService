package mysql.repository;

import mysql.id.MemberTeamId;
import mysql.model.MemberTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeamEntity, MemberTeamId> {

}
