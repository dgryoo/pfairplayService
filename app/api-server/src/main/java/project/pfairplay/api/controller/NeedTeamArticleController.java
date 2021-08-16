package project.pfairplay.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.pfairplay.api.common.exception.deprecated.EntityFieldValueChecker;
import project.pfairplay.api.common.exception.deprecated.SourceNotFoundException;
import project.pfairplay.api.common.filter.FilterManager;
import project.pfairplay.api.model.get.NeedTeamArticleForGet;
import project.pfairplay.api.model.post.NeedTeamArticleForPost;
import project.pfairplay.api.model.put.NeedTeamArticleForPut;
import project.pfairplay.api.model.summarized.SummarizedNeedTeamArticle;
import project.pfairplay.storage.mysql.model.MemberEntity;
import project.pfairplay.storage.mysql.model.NeedTeamArticleEntity;
import project.pfairplay.storage.mysql.repository.MemberRepository;
import project.pfairplay.storage.mysql.repository.NeedTeamArticleRepository;

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
    public ResponseEntity<Void> createNeedTeamArticle(@RequestBody NeedTeamArticleForPost needTeamArticle) {
        EntityFieldValueChecker.checkNeedTeamArticlePostFieldValue(needTeamArticle);

        Optional<MemberEntity> memberEntity = memberRepository.findById(needTeamArticle.getWriteMemberUid());

        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", needTeamArticle.getWriteMemberUid()));
        }

        needTeamArticleRepository.save(needTeamArticle.toNeedTeamArticleEntity(memberEntity.get()));

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/needTeamArticle")
    public ResponseEntity<List<SummarizedNeedTeamArticle>> findAll() {
        List<NeedTeamArticleEntity> needTeamArticleEntityList = needTeamArticleRepository.findAll();

        if (needTeamArticleEntityList.size() == 0) {
            throw new SourceNotFoundException("article not found registered in NeedTeamArticle)");
        }

        List<SummarizedNeedTeamArticle> summarizedNeedTeamArticleList =
                needTeamArticleEntityList
                        .stream()
                        .map(SummarizedNeedTeamArticle::fromNeedTeamArticleEntity)
                        .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(summarizedNeedTeamArticleList);

    }

    @GetMapping("/needTeamArticle/{articleNo}")
    public ResponseEntity<NeedTeamArticleForGet> findByArticleNo(@PathVariable int articleNo) {
        Optional<NeedTeamArticleEntity> needTeamArticleEntity = needTeamArticleRepository.findById(articleNo);

        if (!needTeamArticleEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("ArticleNo{%s} not found", articleNo));
        }

        needTeamArticleEntity.get().setWriteMember(FilterManager.articleMemberFilter(needTeamArticleEntity.get().getWriteMember()));

        return ResponseEntity.status(HttpStatus.OK).body(NeedTeamArticleForGet.from(needTeamArticleEntity.get()));
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
    public ResponseEntity<Void> updateByArticleNo(@PathVariable int articleNo, @RequestBody NeedTeamArticleForPut needTeamArticleModifier) {

        Optional<NeedTeamArticleEntity> needTeamArticleEntity = needTeamArticleRepository.findById(articleNo);

        if (!needTeamArticleEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("ArticleNo{%s} not found", articleNo));
        }

        EntityFieldValueChecker.checkNeedTeamArticlePutFieldValue(needTeamArticleModifier);

        if (!needTeamArticleModifier.getWriteMemberUid().equals(needTeamArticleEntity.get().getWriteMember().getUid())) {
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
