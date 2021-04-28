package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.NeedTeamArticleEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.NeedTeamArticleRepository;
import com.example.pfairplayservice.model.modifier.NeedTeamArticleModifier;
import com.example.pfairplayservice.model.origin.NeedTeamArticle;
import com.example.pfairplayservice.model.summarized.SummarizedNeedTeamArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/needTeamArticle")
    public ResponseEntity<List<SummarizedNeedTeamArticle>> findAll() {
        List<NeedTeamArticleEntity> needTeamArticleEntityList = needTeamArticleRepository.findAll();

        if (needTeamArticleEntityList == null)
            new SourceNotFoundException("article not found registered in NeedTeamArticle)");

        List<SummarizedNeedTeamArticle> summarizedNeedTeamArticleList =
                needTeamArticleEntityList
                        .stream()
                        .map(needTeamArticleEntity -> SummarizedNeedTeamArticle.fromNeedTeamArticleEntity(needTeamArticleEntity))
                        .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(summarizedNeedTeamArticleList);

    }

    @GetMapping("/needTeamArticle/{articleNo}")
    public ResponseEntity<NeedTeamArticle> findByArticleNo(@PathVariable int articleNo) {
        Optional<NeedTeamArticleEntity> needTeamArticleEntity = needTeamArticleRepository.findById(articleNo);

        if (!needTeamArticleEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("ArticleNo{%s} not found", articleNo));
        }

        return ResponseEntity.status(HttpStatus.OK).body(NeedTeamArticle.fromNeedTeamArticleEntity(needTeamArticleEntity.get()));
    }

    @DeleteMapping("/needTeamArticle/{articleNo}")
    public ResponseEntity<Void> deleteByArticleNo(@PathVariable int articleNo, @RequestParam String uid) {
        Optional<NeedTeamArticleEntity> needTeamArticleEntity = needTeamArticleRepository.findById(articleNo);

        if (!needTeamArticleEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("ArticleNo{%s} not found", articleNo));
        }

        if (!isWriter(needTeamArticleEntity.get(), uid)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        needTeamArticleRepository.deleteById(articleNo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/needTeamArticle/{articleNo}")
    public ResponseEntity<Void> updateByArticleNo(@PathVariable int articleNo, @RequestBody NeedTeamArticleModifier needTeamArticleModifier) {

        Optional<NeedTeamArticleEntity> needTeamArticleEntity = needTeamArticleRepository.findById(articleNo);

        if (!needTeamArticleEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("ArticleNo{%s} not found", articleNo));
        }

        EntityFieldValueChecker.checkNeedTeamArticlePutFieldValue(needTeamArticleModifier);

        if (!needTeamArticleModifier.getWriteMember().getUid().equals(needTeamArticleEntity.get().getWriteMember().getUid())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!needTeamArticleEntity.get().getSubject().equals(needTeamArticleModifier.getSubject()))
            needTeamArticleRepository.updateSubjectByArticleNo(articleNo, needTeamArticleModifier.getSubject());
        if (!needTeamArticleEntity.get().getDetail().equals(needTeamArticleModifier.getDetail()))
            needTeamArticleRepository.updateDetailByArticleNo(articleNo, needTeamArticleModifier.getDetail());
        if (needTeamArticleEntity.get().getNeedPosition() != needTeamArticleModifier.getNeedPosition().getPosition())
            needTeamArticleRepository.updateNeedPositionByArticleNo(articleNo, needTeamArticleModifier.getNeedPosition().getPosition());
        needTeamArticleRepository.updateModifiedDateByArticleNo(articleNo);

        return ResponseEntity.status(HttpStatus.OK).build();


    }

    private boolean isWriter(NeedTeamArticleEntity needTeamArticleEntity, String uid) {
        if (needTeamArticleEntity.getWriteMember().getUid().equals(uid)) return true;
        return false;

    }
}
