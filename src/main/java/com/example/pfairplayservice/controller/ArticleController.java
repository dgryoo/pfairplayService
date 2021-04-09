package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.jpa.repository.ArticleRepository;
import com.example.pfairplayservice.model.origin.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("board/needteam")
    public ResponseEntity<List<Article>> findAllNeedTeamArticle() {

        List<Article> articleList = articleRepository.findAllNeedTeam;

        return ResponseEntity.status(HttpStatus.OK).body(articleList);

    }

    @PostMapping
    public ResponseEntity<Void> create(@ResponseBody Article article) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateByAriticleNo(ArticleModifier articleModifier) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByAriticleNo(int articleNo) {

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
