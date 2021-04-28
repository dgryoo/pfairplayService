package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.NeedTeamArticleEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.NeedTeamArticleRepository;
import com.example.pfairplayservice.model.origin.Member;
import com.example.pfairplayservice.model.origin.NeedTeamArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class NeedTeamArticleController {

    @Autowired
    private NeedTeamArticleRepository needTeamArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/needTeamArticle")
    public ResponseEntity<Void> createNeedTeamArticle(@RequestBody NeedTeamArticle needTeamArticle) {
        EntityFieldValueChecker.checkNeedTeamArticlePostFieldValue(needTeamArticle);

        Optional<MemberEntity> memberEntity = memberRepository.findById(needTeamArticle.getWriteMember().getUid());

        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", needTeamArticle.getWriteMember().getUid()));
        }

        needTeamArticleRepository.save(needTeamArticle.toNeedTeamArticleEntity(memberEntity.get()));

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/needTeamArticle/{articleNo}")
    public ResponseEntity<NeedTeamArticle> findByArticleNo(@PathVariable int articleNo) {
        Optional<NeedTeamArticleEntity> needTeamArticleEntity = needTeamArticleRepository.findById(articleNo);

        if (!needTeamArticleEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("ArticleNo{%s} not found", articleNo));
        }

        return ResponseEntity.status(HttpStatus.OK).body(NeedTeamArticle.fromNeedTeamArticleEntity(needTeamArticleEntity.get()));
    }

}
