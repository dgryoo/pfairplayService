package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.NeedTeamArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeedTeamArticleRepository extends JpaRepository<NeedTeamArticleEntity, Integer> {

}
