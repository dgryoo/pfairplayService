package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.deprecated.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.deprecated.MatchTimeOverlapException;
import com.example.pfairplayservice.common.exception.deprecated.SourceNotFoundException;
import com.example.pfairplayservice.common.util.DateSelector;
import com.example.pfairplayservice.common.util.MatchQueryBuilder;
import com.example.pfairplayservice.jpa.model.*;
import com.example.pfairplayservice.jpa.repository.MatchRepository;
import com.example.pfairplayservice.jpa.repository.PlayGroundRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.enumfield.DayOfWeek;
import com.example.pfairplayservice.model.enumfield.Status;
import com.example.pfairplayservice.model.get.MatchForGet;
import com.example.pfairplayservice.model.post.MatchForPost;
import com.example.pfairplayservice.model.put.MatchForPut;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayGroundRepository playGroundRepository;

    @Autowired
    private EntityManager entityManager;

    QMatchEntity qMatchEntity = QMatchEntity.matchEntity;

    QPlayGroundEntity qPlayGroundEntity = QPlayGroundEntity.playGroundEntity;

    QTeamEntity qTeamEntity = QTeamEntity.teamEntity;

    final int limitValue = 20;

    @PostMapping("/match")
    public ResponseEntity<Void> createMatch(@RequestBody @Valid MatchForPost matchForPost) {

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
    public ResponseEntity<MatchForGet> findByMatchNo(@PathVariable Integer matchNo) {

        // 해당 matchNo의 Match가 있는지 확인
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchNo);

        if (!matchEntity.isPresent())
            throw new SourceNotFoundException(String.format("MatchNo : {%s}의 Match가 없습니다.", matchNo));

        // return ResponseEntity with filtered model
        return ResponseEntity.status(HttpStatus.OK).body(MatchForGet.from(matchEntity.get()));

    }

    @PutMapping("/match/{matchNo}")
    public ResponseEntity<Void> updateByMatchNo(@PathVariable Integer matchNo,
                                                @RequestBody MatchForPut matchForPut) {

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
    public ResponseEntity<Void> deleteByMatchNo(@PathVariable Integer matchNo,
                                                @RequestParam @NotBlank String tid) {

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

//    @GetMapping("/match/recommend/{tid}")
//    public ResponseEntity<List<MatchForGet>> recommendMatch(@PathVariable String tid) {
//
//        // 해당 tid의 Team이 있는지 확인
//        Optional<TeamEntity> teamEntity = teamRepository.findById(tid);
//        if (!teamEntity.isPresent()) {
//            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
//        }
//
//        List<MatchEntity> matchEntityList = matchRepository.findAll();
//
//        List<MatchForGet> recommendMatchList = matchEntityList.stream()
//                .filter(matchEntity -> matchEntity.getOwnerTeam().getTeamLeadMember().getLevel()
//                        == teamEntity.get().getTeamLeadMember().getLevel())
//                .map(MatchForGet::from)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(recommendMatchList);
//    }

    @GetMapping("/match/specificDate")
    public ResponseEntity<List<MatchForGet>> findMatchListBySpecificDate(@RequestParam Date date,
                                                                         @RequestParam String state,
                                                                         @RequestParam(required = false) String city,
                                                                         @RequestParam(required = false) @Min(0) @Max(23) Integer minStartTime,
                                                                         @RequestParam(required = false) @Min(0) @Max(23) Integer maxStartTime,
                                                                         @RequestParam(required = false) @Min(1) @Max(5) Integer minLevel,
                                                                         @RequestParam(required = false) @Min(1) @Max(5) Integer maxLevel,
                                                                         @RequestParam(required = false) Integer playGroundNo,
                                                                         @RequestParam(required = false) Boolean isOnlyOngoing,
                                                                         @RequestParam Integer offset) {

        // build result Query
        String resultQuery = MatchQueryBuilder.builder()
                .mainAddress(state, city)
                .date(date)
                .time(minStartTime, maxStartTime)
                .level(minLevel, maxLevel)
                .playGroundNo(playGroundNo)
                .isOnlyOngoing(isOnlyOngoing)
                .offset(offset)
                .build();

        // Get List<MatchEntity>
        List<MatchEntity> matchEntityList = entityManager.createNativeQuery(resultQuery).getResultList();

        // convert MatchEntityList to MatchForGetList and summarize
        List<MatchForGet> matchForGetList = matchEntityList
                .stream()
                .map(MatchForGet::from)
                .map(MatchForGet::summarizeThis)
                .collect(Collectors.toList());

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(matchForGetList);

    }

    @GetMapping("/match/conditions")
    public ResponseEntity<List<MatchForGet>> findByConditions(@RequestParam @Min(1) @Max(12) Integer month,
                                                              @RequestParam String dayOfWeek,
                                                              @RequestParam String state,
                                                              @RequestParam(required = false) String city,
                                                              @RequestParam(required = false) @Min(0) @Max(23) Integer minStartTime,
                                                              @RequestParam(required = false) @Min(0) @Max(23) Integer maxStartTime,
                                                              @RequestParam(required = false) @Min(1) @Max(5) Integer minLevel,
                                                              @RequestParam(required = false) @Min(1) @Max(5) Integer maxLevel,
                                                              @RequestParam(required = false) Integer playGroundNo,
                                                              @RequestParam(required = false) Boolean isOnlyOngoing,
                                                              @RequestParam Integer offset) {

        // Get Date List
        List<String> dateList =
                DateSelector.selectDateStringByMonthAndDayOfWeek(month, DayOfWeek.valueOf(dayOfWeek).getDayOfWeekNo());

        // build result Query
        String resultQuery = MatchQueryBuilder.builder()
                .mainAddress(state, city)
                .dateList(dateList)
                .time(minStartTime, maxStartTime)
                .level(minLevel, maxLevel)
                .playGroundNo(playGroundNo)
                .isOnlyOngoing(isOnlyOngoing)
                .offset(offset)
                .build();

        // Get List<MatchEntity>
        List<MatchEntity> matchEntityList = entityManager.createNativeQuery(resultQuery, MatchEntity.class).getResultList();

        // convert MatchEntityList to MatchForGetList and summarize
        List<MatchForGet> matchForGetList = matchEntityList
                .stream()
                .map(MatchForGet::from)
                .map(MatchForGet::summarizeThis)
                .collect(Collectors.toList());

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(matchForGetList);

    }

    @GetMapping("/match/conditions/querydsl")
    public ResponseEntity<List<MatchForGet>> querydslFindByConditions(@RequestParam
                                                                      @Min(value = 1, message = "MONTH01")
                                                                      @Max(value = 12, message = "MONTH01") Integer month,
                                                                      @RequestParam
                                                                      @Pattern(regexp = "^[일|월|화|수|목|금|토]{1}", message = "DOW01") String dayOfWeek,
                                                                      @RequestParam
                                                                      @Pattern(regexp = "^[가-힣]{1,10}", message = "STATE01") String state,
                                                                      @RequestParam(required = false)
                                                                      @Pattern(regexp = "^[가-힣]{1,10}", message = "CITY01") String city,
                                                                      @RequestParam(required = false)
                                                                      @Min(value = 0, message = "MINSH01") @Max(value = 23, message = "MINSH01") Integer minStartHour,
                                                                      @RequestParam(required = false)
                                                                      @Min(value = 0, message = "MAXSH01") @Max(value = 23, message = "MAXSH01") Integer maxStartHour,
                                                                      @RequestParam(required = false)
                                                                      @Min(value = 1, message = "MINL01") @Max(value = 5, message = "MINL01") Integer minLevel,
                                                                      @RequestParam(required = false)
                                                                      @Min(value = 1, message = "MAXL01") @Max(value = 5, message = "MAXL01") Integer maxLevel,
                                                                      @RequestParam(required = false) Integer playGroundNo,
                                                                      @RequestParam(required = false) Boolean isOnlyOngoing,
                                                                      @RequestParam Integer offset) {

//        // DayOfWeek
//        if (DayOfWeek.from(dayOfWeek) == null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        // Get Date List
        List<String> dateStringList =
                DateSelector.selectDateStringByMonthAndDayOfWeek(month, DayOfWeek.valueOf(dayOfWeek).getDayOfWeekNo());

        // Get JPAQueryFactory
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // Get MatchEntity List by Querydsl
        List<MatchEntity> matchEntityList = queryFactory
                .selectFrom(qMatchEntity)
                .leftJoin(qPlayGroundEntity).on(qMatchEntity.playGround.playGroundNo.eq(qPlayGroundEntity.playGroundNo))
                .leftJoin(qTeamEntity).on(qMatchEntity.ownerTeam.tid.eq(qTeamEntity.tid))
                .where(getSimpleDate().in(dateStringList),
                        containMainAddress(state, city),
                        goeMinStartHour(minStartHour),
                        loeMaxStartHour(maxStartHour),
                        goeMinLevel(minLevel),
                        loeMaxLevel(maxLevel),
                        eqPlayGroundNo(playGroundNo),
                        inStatus(isOnlyOngoing))
                .offset(getOffsetValue(offset))
                .limit(limitValue)
                .fetch();

        // convert MatchEntityList to MatchForGetList and summarize
        List<MatchForGet> matchForGetList = matchEntityList
                .stream()
                .map(MatchForGet::from)
                .map(MatchForGet::summarizeThis)
                .collect(Collectors.toList());


        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(matchForGetList);

    }

    @PatchMapping("/match/score/{matchNo}")
    public ResponseEntity<Void> ScoreToMatch(@PathVariable Integer matchNo,
                                             @RequestParam Integer ownerScore,
                                             @RequestParam Integer guestScore) {

        // 해당 matchNo의 Match가 있는지 확인
        Optional<MatchEntity> matchEntity = matchRepository.findById(matchNo);
        if (!matchEntity.isPresent())
            throw new SourceNotFoundException(String.format("MatchNo : {%s}의 Match가 없습니다.", matchNo));

        // Score, status 업데이트
        matchRepository.updateOwnerScore(matchNo, ownerScore);
        matchRepository.updateGuestScore(matchNo, guestScore);
        matchRepository.updateStatus(matchNo, Status.SCORED.getStatus());

        // 레이팅 반영
        calculateRating(matchEntity.get().getOwnerTeam().getTid(),
                matchEntity.get().getGuestTeam().getTid(),
                ownerScore,
                guestScore);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    private void calculateRating(String ownerTid, String guestTid, int ownerScore, int guestScore) {

        // Owner팀 승리시
        if (ownerScore > guestScore) {
            teamRepository.updateRatingByTid(ownerTid, 10);
            teamRepository.updateRatingByTid(guestTid, -10);
        }

        // Guest팀 승리시
        if (ownerScore < guestScore) {
            teamRepository.updateRatingByTid(ownerTid, -10);
            teamRepository.updateRatingByTid(guestTid, 10);
        }
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

    private int getOffsetValue(Integer offset) {
        return limitValue * (offset - 1);
    }

    private BooleanExpression containMainAddress(String state, String city) {
        // main address
        String mainAddress;

        if (city != null) {
            mainAddress = state + " " + city;
        } else {
            mainAddress = state;
        }

        return qPlayGroundEntity.mainAddress.contains(mainAddress);
    }

    private BooleanExpression goeMinStartHour(Integer minStartHour) {
        if (minStartHour != null) {
            return getDateToHour().goe(minStartHour);
        }
        return null;
    }

    private BooleanExpression loeMaxStartHour(Integer maxStartHour) {
        if (maxStartHour != null) {
            return getDateToHour().loe(maxStartHour);
        }
        return null;
    }

    private BooleanExpression goeMinLevel(Integer minLevel) {
        if (minLevel != null) {
            return qTeamEntity.level.goe(minLevel);
        }
        return null;
    }

    private BooleanExpression loeMaxLevel(Integer maxLevel) {
        if (maxLevel != null) {
            return qTeamEntity.level.loe(maxLevel);
        }
        return null;
    }

    private BooleanExpression eqPlayGroundNo(Integer playGroundNo) {
        if (playGroundNo != null) {
            return qPlayGroundEntity.playGroundNo.eq(playGroundNo);
        }
        return null;
    }

    private BooleanExpression inStatus(Boolean isOnlyOngoing) {
        // statusList (isOnlyOngoing)
        List<Integer> statusList = new ArrayList<>();

        if (isOnlyOngoing != null) {
            if (isOnlyOngoing) {
                statusList.add(Status.ONGOING.getStatus()); // 0
            } else {
                statusList.add(Status.ONGOING.getStatus()); // 0
                statusList.add(Status.COMPLETE.getStatus()); // 1
            }
        } else {
            statusList.add(Status.ONGOING.getStatus()); // 0
            statusList.add(Status.COMPLETE.getStatus()); // 1
        }

        return qMatchEntity.status.in(statusList);
    }

    private NumberTemplate<Integer> getDateToHour() {
        // dateToHour NumberTemplate
        NumberTemplate<Integer> dateToHour = Expressions.numberTemplate(Integer.class, "hour({0})", qMatchEntity.startDate);

        return dateToHour;
    }

    private DateTemplate getSimpleDate() {
        return Expressions.dateTemplate(Date.class, "date_format({0}, '%Y-%m-%d')", qMatchEntity.startDate);
    }


}
