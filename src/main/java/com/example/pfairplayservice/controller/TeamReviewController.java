package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.cassandra.model.TeamReviewByReviewerTid;
import com.example.pfairplayservice.cassandra.model.TeamReviewByTid;
import com.example.pfairplayservice.cassandra.model.TeamReviewCounter;
import com.example.pfairplayservice.cassandra.pk.TeamReviewByTidPrimaryKey;
import com.example.pfairplayservice.cassandra.pk.TeamReviewCounterPrimaryKey;
import com.example.pfairplayservice.cassandra.repository.TeamReviewByReviewerTidRepository;
import com.example.pfairplayservice.cassandra.repository.TeamReviewByTidRepository;
import com.example.pfairplayservice.cassandra.repository.TeamReviewCounterRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.get.TeamReviewForGet;
import com.example.pfairplayservice.model.post.TeamReviewForPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TeamReviewController {

    final int limitValue = 10;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamReviewByTidRepository teamReviewByTidRepository;

    @Autowired
    private TeamReviewCounterRepository teamReviewCounterRepository;

    @Autowired
    private TeamReviewByReviewerTidRepository teamReviewByReviewerTidRepository;

    @PostMapping("/teamReview")
    public ResponseEntity<Void> createTeamReview(@RequestBody TeamReviewForPost teamReviewForPost) {

        // 2개 테이블에 공통으로 필요한 writeDate, reviewId 생성
        Date writeDate = new Date();

        String reviewId = UUID.randomUUID().toString();

        // TeamReviewByTid 생성 및 저장

        TeamReviewByTid teamReviewByTid = teamReviewForPost.toTeamReviewByTid(writeDate, reviewId);

        teamReviewByTidRepository.save(teamReviewByTid);

        // TeamReviewByReviewerTid 생성 및 저장

        TeamReviewByReviewerTid teamReviewByReviewerTid = teamReviewForPost.toTeamReviewByReviewerTid(writeDate, reviewId);

        teamReviewByReviewerTidRepository.save(teamReviewByReviewerTid);

        // return ResponseEntity

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/teamReview/{tid}")
    public ResponseEntity<List<TeamReviewForGet>> findTeamReviewByTid(@PathVariable String tid,
                                                                      @RequestParam Integer page) {
        // TeamReviewByTidList 조회
        List<TeamReviewByTid> teamReviewByTidList =
                teamReviewByTidRepository.findTeamReviewByTid(tid, limitValue * page);

        // empty check, empty 일때 NOTFOUND 리턴
        if (teamReviewByTidList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // TeamReviewCounter 조회를 위한 reviewIdCondition
        List<String> reviewIdCondition = teamReviewByTidList.stream()
                .map(teamReviewByTid -> teamReviewByTid.getTeamReviewByTidPrimaryKey()
                    .getReviewId())
                .collect(Collectors.toList());

        // TeamReviewCounter 조회
        List<TeamReviewCounter> teamReviewCounterList =
                teamReviewCounterRepository.findTeamReviewCounterByTidAndReviewIdList(tid, reviewIdCondition);

        // TeamReviewCounter to Map
        Map<String, TeamReviewCounter> reviewCounterMap = new HashMap<>();

        for (TeamReviewCounter counter : teamReviewCounterList) {
            reviewCounterMap.put(counter.getTeamReviewCounterPrimaryKey().getReviewId(), counter);
        }

        // TeamReview, TeamReviewCounter -> TeamReviewForGet
        List<TeamReviewForGet> reviewForGetList = new ArrayList<>();

        for (TeamReviewByTid review : teamReviewByTidList) {
            reviewForGetList.add(TeamReviewForGet
                    .fromReviewAndCounter(review,
                            reviewCounterMap.get(review.getTeamReviewByTidPrimaryKey().getReviewId())));
        }

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(reviewForGetList);

    }

    @GetMapping("/myReview/{reviewerTid}")
    public ResponseEntity<List<TeamReviewForGet>> findTeamReviewByReviewerTid(@PathVariable String reviewerTid,
                                                                              @RequestParam Integer page) {

        // TeamReviewByReviewerTid 조회
        List<TeamReviewByReviewerTid> teamReviewByReviewerTidList =
                teamReviewByReviewerTidRepository.findTeamReviewByReviewerTid(reviewerTid, limitValue * page);

        // empty check, empty 일때 NOTFOUND 리턴
        if (teamReviewByReviewerTidList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Mapping ByReviewerId to ByTid
        List<TeamReviewByTid> teamReviewByTidList = teamReviewByReviewerTidList.parallelStream()
                .map(teamReviewByReviewerTid -> teamReviewByTidRepository
                        .findById(TeamReviewByTidPrimaryKey.builder()
                                .tid(teamReviewByReviewerTid.getTid())
                                .writeDate(teamReviewByReviewerTid.getTeamReviewByReviewerTidPrimaryKey().getWriteDate())
                                .reviewId(teamReviewByReviewerTid.getTeamReviewByReviewerTidPrimaryKey().getReviewId())
                                .build()))
                .map(teamReviewByTid -> teamReviewByTid.get())
                .collect(Collectors.toList());

        // TeamReviewCounter 조회
        List<Optional<TeamReviewCounter>> teamReviewCounterList = teamReviewByTidList.parallelStream()
                .map(teamReviewByTid -> teamReviewCounterRepository.findTeamReviewCounterByTidAndReviewId(
                        teamReviewByTid.getTeamReviewByTidPrimaryKey().getTid(),
                        teamReviewByTid.getTeamReviewByTidPrimaryKey().getReviewId()))
                .collect(Collectors.toList());

        // TeamReviewCounterList to Map
        Map<String, TeamReviewCounter> reviewCounterMap = new HashMap<>();

        for (Optional<TeamReviewCounter> o : teamReviewCounterList) {
            if(o.isPresent()) {
                reviewCounterMap.put(o.get().getTeamReviewCounterPrimaryKey().getReviewId(), o.get());
            }
        }

        // TeamReview, TeamReviewCounter -> TeamReviewForGet
        List<TeamReviewForGet> reviewForGetList = new ArrayList<>();

        for (TeamReviewByTid review : teamReviewByTidList) {
            reviewForGetList.add(TeamReviewForGet
                    .fromReviewAndCounter(review,
                            reviewCounterMap.get(review.getTeamReviewByTidPrimaryKey().getReviewId())));
        }

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(reviewForGetList);

    }

    @PutMapping("/teamReview/inThumbsUp")
    public ResponseEntity<Void> increaseThumbsUpByTidAndReviewId(@RequestParam String tid,
                                                                 @RequestParam String reviewId) {
        teamReviewCounterRepository.increaseThumbsUpByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/teamReview/deThumbsUp")
    public ResponseEntity<Void> decreaseThumbsUpByTidAndReviewId(@RequestParam String tid,
                                                                 @RequestParam String reviewId) {
        teamReviewCounterRepository.decreaseThumbsUpByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/teamReview/inThumbsDown")
    public ResponseEntity<Void> increaseThumbsDownByTidAndReviewId(@RequestParam String tid,
                                                                   @RequestParam String reviewId) {
        teamReviewCounterRepository.increaseThumbsDownByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/teamReview/deThumbsDown")
    public ResponseEntity<Void> decreaseThumbsDownByTidAndReviewId(@RequestParam String tid,
                                                                   @RequestParam String reviewId) {
        teamReviewCounterRepository.decreaseThumbsDownByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
