package project.pfairplay.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import project.pfairplay.api.model.get.TeamReviewForGet;
import project.pfairplay.api.model.post.TeamReviewForPost;
import project.pfairplay.storage.cassandra.model.TeamReviewByReviewerTid;
import project.pfairplay.storage.cassandra.model.TeamReviewByTid;
import project.pfairplay.storage.cassandra.model.TeamReviewCounter;
import project.pfairplay.storage.cassandra.pk.TeamReviewByTidPrimaryKey;
import project.pfairplay.storage.cassandra.repository.CasTeamReviewCounterRepository;
import project.pfairplay.storage.cassandra.repository.TeamReviewByReviewerTidRepository;
import project.pfairplay.storage.cassandra.repository.TeamReviewByTidRepository;
import project.pfairplay.storage.kafka.model.TeamReviewThumbs;
import project.pfairplay.storage.mysql.repository.TeamRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CasTeamReviewController {

    final int limitValue = 10;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamReviewByTidRepository teamReviewByTidRepository;

    @Autowired
    private CasTeamReviewCounterRepository casTeamReviewCounterRepository;

    @Autowired
    private TeamReviewByReviewerTidRepository teamReviewByReviewerTidRepository;

    @PostMapping("/cas/teamReview")
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

    @GetMapping("/cas/teamReview/{tid}")
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
                casTeamReviewCounterRepository.findTeamReviewCounterByTidAndReviewIdList(tid, reviewIdCondition);

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

    @GetMapping("/cas/teamReview/my/{reviewerTid}")
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
                .map(teamReviewByTid -> casTeamReviewCounterRepository.findTeamReviewCounterByTidAndReviewId(
                        teamReviewByTid.getTeamReviewByTidPrimaryKey().getTid(),
                        teamReviewByTid.getTeamReviewByTidPrimaryKey().getReviewId()))
                .collect(Collectors.toList());

        // TeamReviewCounterList to Map
        Map<String, TeamReviewCounter> reviewCounterMap = new HashMap<>();

        for (Optional<TeamReviewCounter> o : teamReviewCounterList) {
            if (o.isPresent()) {
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

}
