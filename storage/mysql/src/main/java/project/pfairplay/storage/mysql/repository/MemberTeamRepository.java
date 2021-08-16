package project.pfairplay.storage.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.pfairplay.storage.mysql.id.MemberTeamId;
import project.pfairplay.storage.mysql.model.MemberTeamEntity;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeamEntity, MemberTeamId> {

}
