package mysql.repository;

import mysql.model.TeamReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamReviewRepository extends JpaRepository<TeamReviewEntity, String> {

}
