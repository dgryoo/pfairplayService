package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.id.MemberArticleId;
import com.example.pfairplayservice.jpa.model.MemberArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberArticleRepository extends JpaRepository <MemberArticleEntity, MemberArticleId> {
}
