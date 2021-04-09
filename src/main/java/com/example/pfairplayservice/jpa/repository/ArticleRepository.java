package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    // TODO : board에 따른 aritcle 불러오는 메소드 생성
}
