package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.MatchTimeOverlapException;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.common.util.DateSelector;
import com.example.pfairplayservice.common.util.MatchQueryBuilder;
import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.PlayGroundEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MatchRepository;
import com.example.pfairplayservice.jpa.repository.PlayGroundRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.enumfield.Weekday;
import com.example.pfairplayservice.model.get.MatchForGet;
import com.example.pfairplayservice.model.post.MatchForPost;
import com.example.pfairplayservice.model.put.MatchForPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
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

    @Autowired
    private PlayGroundRepository playGroundRepository;

    @Autowired
    private EntityManager entityManager;

    @PostMapping("/match")
    public ResponseEntity<Void> createMatch(@RequestBody MatchForPost matchForPost) {

        // 동시간대 Match가 있는지 확인
        List<MatchEntity> matchEntityList = matchRepository.findAllByTid(matchForPost.getOwnerTeamTid());

        if (matchEntityList != null)
            checkOverlapMatchTime(matchForPost.getStartDate(), matchForPost.getEndDate(), matchEntityList);

        // RequestBody의 field값 확인
        EntityFieldValueChecker.checkMatchPostFieldValue(matchForPost);

        // ownerTeamTid의 Team이 존재하는지 확인
        Optional<TeamEntity> teamEntity = teamRepository.findById(matchForPost.getOwnerTeamTid());
        if (!teamEntity.isPresent())
            throw new SourceNotFoundException(String.format("tid{%s} not found", matchForPost.getOwnerTeamTid()));

        // PlayGroundNo의 PlayGround가 존재하는지 확인
        Optional<PlayGroundEntity> playGroundEntity = playGroundRepository.findById(matchForPost.getPlayGroundNo());
        if (!playGroundEntity.isPresent())
            throw new SourceNotFoundException(String.format("Play Ground No : {%s} not found", matchForPost.getPlayGroundNo()));

        // Match 생성
        matchRepository.save(matchForPost.toMatchEntity(teamEntity.get(), playGroundEntity.get()));

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

        // Match를 수정할 권한이 있는 tid인지 확인
        if (matchForPut.getOwnerTeamTid() != matchEntity.get().getOwnerTeam().getTid())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // 동시간대 Match가 있는지 확인
        List<MatchEntity> matchEntityList = matchRepository.findAllByTid(matchEntity.get().getOwnerTeam().getTid());
        if (matchEntityList != null)
            checkOverlapMatchTime(matchForPut.getStartDate(), matchForPut.getEndDate(), matchEntityList);

        // RequestBody의 field값 확인
        EntityFieldValueChecker.checkMatchPutFieldValue(matchForPut);

        // 기존에서 변경된 값만 업데이트
        if (matchEntity.get().getPlayGround().getPlayGroundNo() != matchForPut.getPlayGroundNo())
            matchRepository.updateGroundNoByMatchNo(matchNo, matchForPut.getPlayGroundNo());

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
    public ResponseEntity<Void> deleteByMatchNo(@PathVariable int matchNo, String tid) {

        // 해당 matchNo의 Match가 있는지 확인
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchNo);

        if (!matchEntity.isPresent())
            throw new SourceNotFoundException(String.format("MatchNo : {%s}의 Match가 없습니다.", matchNo));

        // Match를 삭제할 권한이 있는 tid인지 확인
        if (tid != matchEntity.get().getOwnerTeam().getTid())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // 해당 matchNo의 Match를 삭제
        matchRepository.deleteById(matchNo);

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
                .map(MatchForGet::from)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(recommendMatchList);
    }

    @GetMapping("/match/specificDate")
    public ResponseEntity<List<MatchForGet>> findMatchListBySpecificDate(@RequestParam String state,
                                                                         @RequestParam String city,
                                                                         @RequestParam Date date,
                                                                         @RequestParam int minStartTime,
                                                                         @RequestParam int maxStartTime,
                                                                         @RequestParam int minLevel,
                                                                         @RequestParam int maxLevel,
                                                                         @RequestParam int playGroundNo,
                                                                         @RequestParam boolean isOnlyOngoing,
                                                                         @RequestParam int offset) {

        // build result Query
        String resultQuery = MatchQueryBuilder.builder()
                .date(date)
                .mainAddress(state, city)
                .time(minStartTime, maxStartTime)
                .playGroundNo(playGroundNo)
                .isOnlyOngoing(isOnlyOngoing)
                .offset(offset)
                .build();

        // Get List<MatchEntity>
        List<MatchEntity> matchEntityList = entityManager.createNativeQuery(resultQuery).getResultList();

        // convert MatchEntityList to MatchForGetList
        List<MatchForGet> matchForGetList = matchEntityList.stream().map(MatchForGet::from).collect(Collectors.toList());

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(matchForGetList);

    }

    @GetMapping("/match/monthAndDayOfWeek")
    public ResponseEntity<List<MatchForGet>> findByMonthAndDayOfWeek(@RequestParam int month,
                                                                     @RequestParam String dayOfWeek,
                                                                     @RequestParam String state,
                                                                     @RequestParam String city,
                                                                     @RequestParam int minStartTime,
                                                                     @RequestParam int maxStartTime,
                                                                     @RequestParam int minLevel,
                                                                     @RequestParam int maxLevel,
                                                                     @RequestParam int playGroundNo,
                                                                     @RequestParam boolean isOnlyOngoing,
                                                                     @RequestParam int offset) {
        // Get Date List
        List<String> dateList =
                DateSelector.selectDateByMonthAndDayOfWeek(month, Weekday.valueOf(dayOfWeek).getWeekdayNum());

        // build result Query
        String resultQuery = MatchQueryBuilder.builder()
                .dateList(dateList)
                .mainAddress(state, city)
                .time(minStartTime, maxStartTime)
                .playGroundNo(playGroundNo)
                .isOnlyOngoing(isOnlyOngoing)
                .offset(offset)
                .build();

        // Get List<MatchEntity>
        List<MatchEntity> matchEntityList = entityManager.createNativeQuery(resultQuery).getResultList();

        // convert MatchEntityList to MatchForGetList
        List<MatchForGet> matchForGetList = matchEntityList.stream().map(MatchForGet::from).collect(Collectors.toList());

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(matchForGetList);

    }


    // match의 시간대가 겹치는지 확인하는 함수
    private void checkOverlapMatchTime(Date startTime, Date endTime, List<MatchEntity> matchEntityList) {

        matchEntityList.stream().forEach(matchEntity -> {
            if ((startTime.compareTo(matchEntity.getStartDate()) == 1 && startTime.compareTo(matchEntity.getEndDate()) == -1)
                    || (endTime.compareTo(matchEntity.getStartDate()) == 1 && endTime.compareTo(matchEntity.getEndDate()) == -1)
                    || (startTime.compareTo(matchEntity.getStartDate()) == 0)
                    || endTime.compareTo(matchEntity.getEndDate()) == 0) {
                throw new MatchTimeOverlapException("기존 매치 시간과 중복 됩니다.");
            }

        });
    }

}
