package project.pfairplay.storage.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.pfairplay.storage.mysql.model.TeamReviewEntity;

@Repository
public interface TeamReviewRepository extends JpaRepository<TeamReviewEntity, String> {

}
