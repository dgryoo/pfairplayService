package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.NeedTeamArticleRepository;
import com.example.pfairplayservice.model.origin.NeedTeamArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        Optional<MemberEntity> memberEntity = memberRepository.findById(needTeamArticle.getWriteMemberUid());

        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", needTeamArticle.getWriteMemberUid()));
        }

        needTeamArticleRepository.save(needTeamArticle.toNeedTeamArticleEntity(memberEntity.get()));

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}
