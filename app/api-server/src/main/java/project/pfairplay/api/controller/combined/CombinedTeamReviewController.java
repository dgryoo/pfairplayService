package project.pfairplay.api.controller.combined;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import project.pfairplay.api.model.enumfield.ThumbsType;
import project.pfairplay.api.model.get.TeamReviewForGet;
import project.pfairplay.api.model.post.TeamReviewForPost;
import project.pfairplay.storage.cassandra.model.TeamReviewByReviewerTid;
import project.pfairplay.storage.cassandra.model.TeamReviewByTid;
import project.pfairplay.storage.cassandra.pk.TeamReviewByTidPrimaryKey;
import project.pfairplay.storage.cassandra.repository.TeamReviewByReviewerTidRepository;
import project.pfairplay.storage.cassandra.repository.TeamReviewByTidRepository;
import project.pfairplay.storage.kafka.model.TeamReviewThumbs;
import project.pfairplay.storage.mysql.model.TeamReviewCounterEntity;
import project.pfairplay.storage.mysql.repository.TeamReviewCounterRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CombinedTeamReviewController {

    final int limitValue = 10;

    private static final String TOPIC = "rdsThumbs";

    @Autowired
    private KafkaTemplate<String, TeamReviewThumbs> kafkaTemplate;
    
    @Autowired
    private TeamReviewCounterRepository teamReviewCounterRepository;

    @Autowired
    private TeamReviewByTidRepository teamReviewByTidRepository;

    @Autowired
    private TeamReviewByReviewerTidRepository teamReviewByReviewerTidRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    

    @PostMapping("/combined/teamReview")
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

    @GetMapping("/combined/teamReview/{tid}")
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
        List<TeamReviewCounterEntity> teamReviewCounterList = teamReviewCounterRepository.findTeamReviewByReviewIdList(reviewIdCondition);

        // TeamReviewCounter to Map
        Map<String, TeamReviewCounterEntity> reviewCounterMap = new HashMap<>();

        for (TeamReviewCounterEntity teamReviewCounterEntity : teamReviewCounterList) {
            reviewCounterMap.put(teamReviewCounterEntity.getReviewId(), teamReviewCounterEntity);
        }

        // TeamReview, TeamReviewCounter -> TeamReviewForGet
        List<TeamReviewForGet> reviewForGetList = teamReviewByTidList.stream()
                .map(teamReviewByTid -> TeamReviewForGet.fromReviewAndCounter(teamReviewByTid,
                        reviewCounterMap.get(teamReviewByTid.getTeamReviewByTidPrimaryKey().getReviewId())))
                .collect(Collectors.toList());

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(reviewForGetList);

    }

    @GetMapping("/combined/teamReview/my/{reviewerTid}")
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

        // TeamReviewCounter 조회를 위한 reviewIdCondition
        List<String> reviewIdCondition = teamReviewByTidList.stream()
                .map(teamReviewByTid -> teamReviewByTid.getTeamReviewByTidPrimaryKey()
                        .getReviewId())
                .collect(Collectors.toList());

        // TeamReviewCounter 조회
        List<TeamReviewCounterEntity> teamReviewCounterList = teamReviewCounterRepository.findTeamReviewByReviewIdList(reviewIdCondition);

        // TeamReviewCounter to Map
        Map<String, TeamReviewCounterEntity> reviewCounterMap = new HashMap<>();

        for (TeamReviewCounterEntity teamReviewCounterEntity : teamReviewCounterList) {
            reviewCounterMap.put(teamReviewCounterEntity.getReviewId(), teamReviewCounterEntity);
        }

        // TeamReview, TeamReviewCounter -> TeamReviewForGet
        List<TeamReviewForGet> reviewForGetList = teamReviewByTidList.stream()
                .map(teamReviewByTid -> TeamReviewForGet.fromReviewAndCounter(teamReviewByTid,
                        reviewCounterMap.get(teamReviewByTid.getTeamReviewByTidPrimaryKey().getReviewId())))
                .collect(Collectors.toList());

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(reviewForGetList);
        
    }

    @PutMapping("/combined/teamReview/inThumbsUp")
    public ResponseEntity<Void> increaseThumbsUpByTidAndReviewId(@RequestParam String reviewId) {

        TeamReviewThumbs teamReviewThumbs = TeamReviewThumbs.builder()
                .reviewId(reviewId)
                .type(ThumbsType.IncreaseThumbsUp.getTypeNumber())
                .build();

        sendMessage(teamReviewThumbs);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/combined/teamReview/deThumbsUp")
    public ResponseEntity<Void> decreaseThumbsUpByTidAndReviewId(@RequestParam String reviewId) {
        TeamReviewThumbs teamReviewThumbs = TeamReviewThumbs.builder()
                .reviewId(reviewId)
                .type(ThumbsType.DecreaseThumbsUp.getTypeNumber())
                .build();

        sendMessage(teamReviewThumbs);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/combined/teamReview/inThumbsDown")
    public ResponseEntity<Void> increaseThumbsDownByTidAndReviewId(@RequestParam String reviewId) {
        TeamReviewThumbs teamReviewThumbs = TeamReviewThumbs.builder()
                .reviewId(reviewId)
                .type(ThumbsType.IncreaseThumbsDown.getTypeNumber())
                .build();

        sendMessage(teamReviewThumbs);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/combined/teamReview/deThumbsDown")
    public ResponseEntity<Void> decreaseThumbsDownByTidAndReviewId(@RequestParam String reviewId) {
        TeamReviewThumbs teamReviewThumbs = TeamReviewThumbs.builder()
                .reviewId(reviewId)
                .type(ThumbsType.DecreaseThumbsDown.getTypeNumber())
                .build();

        sendMessage(teamReviewThumbs);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public void sendMessage(TeamReviewThumbs teamReviewThumbs) {
        kafkaTemplate.send(TOPIC,teamReviewThumbs.getReviewId(), teamReviewThumbs);
    }

}
