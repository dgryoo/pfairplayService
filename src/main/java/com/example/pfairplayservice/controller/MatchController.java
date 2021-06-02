package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.MatchTimeOverlapException;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MatchRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.get.MatchForGet;
import com.example.pfairplayservice.model.post.MatchForPost;
import com.example.pfairplayservice.model.put.MatchForPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;


    @PostMapping("/match")
    public ResponseEntity<Void> createMatch(@RequestBody MatchForPost matchForPost) {

        // 동시간대 Match가 있는지 확인
        List<MatchEntity> matchEntityList = matchRepository.findAllByTid(matchForPost.getOwnerTeamTid());

        checkOverlapMatchTime(matchForPost.getStartDate(), matchForPost.getEndDate(), matchEntityList);

        // RequestBody의 field값 확인
        EntityFieldValueChecker.checkMatchPostFieldValue(matchForPost);

        // ownerTeamTid의 Team이 존재하는지 확인
        Optional<TeamEntity> teamEntity = teamRepository.findById(matchForPost.getOwnerTeamTid());
        if (!teamEntity.isPresent())
            throw new SourceNotFoundException(String.format("tid{%s} not found", matchForPost.getOwnerTeamTid()));

        // Match 생성
        matchRepository.save(matchForPost.toMatchEntity(teamEntity.get()));

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/match/{matchNo}")
    public ResponseEntity<MatchForGet> findByMatchNo(@PathVariable int matchNo) {

        // 해당 matchNo의 Match가 있는지 확인
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchNo);

        if (!matchEntity.isPresent())
            throw new SourceNotFoundException(String.format("MatchNo : {%s}의 Match가 없습니다.", matchNo));

        // return ResponseEntity with filtered model
        return ResponseEntity.status(HttpStatus.OK).body(MatchForGet.from(matchEntity.get()));

    }

    @PutMapping("/match/{matchNo}")
    public ResponseEntity<Void> updateByMatchNo(@PathVariable int matchNo, @RequestBody MatchForPut matchForPut) {

        // 해당 matchNo의 Match가 있는지 확인
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchNo);

        if (!matchEntity.isPresent())
            throw new SourceNotFoundException(String.format("MatchNo : {%s}의 Match가 없습니다.", matchNo));

        // 동시간대 Match가 있는지 확인
        List<MatchEntity> matchEntityList = matchRepository.findAllByTid(matchEntity.get().getOwnerTeam().getTid());

        checkOverlapMatchTime(matchForPut.getStartDate(), matchForPut.getEndDate(), matchEntityList);

        // RequestBody의 field값 확인
        EntityFieldValueChecker.checkMatchPutFieldValue(matchForPut);

        // 기존에서 변경된 값만 업데이트
        if (matchEntity.get().getGroundNumber() != matchForPut.getPlayGround().getGroundNumber())
            matchRepository.updateGroundNumberByMatchNo(matchNo, matchForPut.getPlayGround().getGroundNumber());

        if (matchEntity.get().getPrice() != matchForPut.getPrice())
            matchRepository.updatePriceByMatchNo(matchNo, matchForPut.getPrice());

        if (matchEntity.get().getStartDate().compareTo(matchForPut.getStartDate()) != 0)
            matchRepository.updateStartDateByMatchNo(matchNo, matchForPut.getStartDate());

        if (matchEntity.get().getEndDate().compareTo(matchForPut.getEndDate()) != 0)
            matchRepository.updateEndDateByMatchNo(matchNo, matchForPut.getEndDate());

        if (!matchEntity.get().getMessage().equals(matchForPut.getMessage()))
            matchRepository.updateMessageByMatchNo(matchNo, matchForPut.getMessage());

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @DeleteMapping("/match/{matchNo}")
    public ResponseEntity<Void> deleteByMatchNo(@PathVariable int matchNo) {

        // 해당 matchNo의 Match가 있는지 확인
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchNo);

        if (!matchEntity.isPresent())
            throw new SourceNotFoundException(String.format("MatchNo : {%s}의 Match가 없습니다.", matchNo));

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/match/recommend/{tid}")
    public ResponseEntity<List<MatchForGet>> recommendMatch(@PathVariable String tid) {

        // 해당 tid의 Team이 있는지 확인
        Optional<TeamEntity> teamEntity = teamRepository.findById(tid);
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
        }

        List<MatchEntity> matchEntityList = matchRepository.findAll();

        List<MatchForGet> recommendMatchList = matchEntityList.stream()
                .filter(matchEntity -> matchEntity.getOwnerTeam().getTeamLeadMember().getLevel()
                        == teamEntity.get().getTeamLeadMember().getLevel())
                .map(matchEntity -> MatchForGet.from(matchEntity))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recommendMatchList);
    }


    // match의 시간대가 겹치는지 확인하는 함수
    private void checkOverlapMatchTime(Date startTime, Date endTime, List<MatchEntity> matchEntityList) {

        matchEntityList.stream().forEach(matchEntity -> {
            if ((startTime.compareTo(matchEntity.getStartDate()) == 1 && startTime.compareTo(matchEntity.getEndDate()) == -1)
                    || (endTime.compareTo(matchEntity.getStartDate()) == 1 && endTime.compareTo(matchEntity.getEndDate()) == -1)) {
                throw new MatchTimeOverlapException("기존 매치 시간과 중복 됩니다.");
            }

        });
    }

}
