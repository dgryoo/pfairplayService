package mysql.repository;

import mysql.model.TeamReviewCounterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamReviewCounterRepository extends JpaRepository<TeamReviewCounterEntity, String> {

}
