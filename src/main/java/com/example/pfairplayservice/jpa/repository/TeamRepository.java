package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, String> {
}
