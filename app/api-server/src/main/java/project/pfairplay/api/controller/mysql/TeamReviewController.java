package project.pfairplay.api.controller.mysql;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import project.pfairplay.api.model.enumfield.ThumbsType;
import project.pfairplay.api.model.get.TeamReviewForGet;
import project.pfairplay.api.model.post.TeamReviewForPost;
import project.pfairplay.storage.kafka.model.TeamReviewThumbs;
import project.pfairplay.storage.mysql.model.QTeamReviewCounterEntity;
import project.pfairplay.storage.mysql.model.QTeamReviewEntity;
import project.pfairplay.storage.mysql.model.TeamReviewCounterEntity;
import project.pfairplay.storage.mysql.model.TeamReviewEntity;
import project.pfairplay.storage.mysql.repository.TeamReviewCounterRepository;
import project.pfairplay.storage.mysql.repository.TeamReviewRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class TeamReviewController {

    final int limitValue = 10;

    private static final String TOPIC = "thumbs";

    @Autowired
    private KafkaTemplate<String, TeamReviewThumbs> kafkaTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TeamReviewRepository teamReviewRepository;

    @Autowired
    private TeamReviewCounterRepository teamReviewCounterRepository;

    QTeamReviewEntity qTeamReviewEntity;

    QTeamReviewCounterEntity qTeamReviewCounterEntity;


    @Operation(summary = "팀 리뷰 등록")
    @PostMapping("/teamReview")
    public ResponseEntity<Void> createTeamReview(@RequestBody TeamReviewForPost teamReviewForPost) {

        // reviewId UUID 생성
        String reviewId = UUID.randomUUID().toString();

        // TeamReviewEntity 생성 및 저장
        TeamReviewEntity teamReviewEntity = teamReviewForPost.toTeamReviewEntity(reviewId);
        teamReviewRepository.save(teamReviewEntity);

        // TeamReviewCounterEntity 생성 및 저장
        TeamReviewCounterEntity teamReviewCounterEntity = teamReviewForPost.toTeamReviewCounterEntity(reviewId);
        teamReviewCounterRepository.save(teamReviewCounterEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Operation(summary = "팀 리뷰 조회")
    @GetMapping("/teamReview/tid")
    public ResponseEntity<List<TeamReviewForGet>> findTeamReviewListByTid(@RequestParam String tid,
                                                                          @RequestParam Integer page) {
        // Get JPAQueryFactory
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // Get TeamReivew List by Querydsl
        List<TeamReviewEntity> teamReviewEntityList = queryFactory
                .selectFrom(qTeamReviewEntity)
                .where(qTeamReviewEntity.tid.eq(tid))
                .offset((page - 1) * limitValue + 1)
                .limit(limitValue)
                .fetch();

        // TeamReviewCounter reviewIdCondition List
        List<String> reviewIdCondition = teamReviewEntityList
                .stream()
                .map(teamReviewEntity -> teamReviewEntity.getReviewId())
                .collect(Collectors.toList());

        // Get TeamReivew List by Querydsl
        List<TeamReviewCounterEntity> teamReviewCounterEntityList = queryFactory
                .selectFrom(qTeamReviewCounterEntity)
                .where(qTeamReviewCounterEntity.reviewId.in(reviewIdCondition))
                .fetch();

        // TeamReviewCounterList to Map
        Map<String, TeamReviewCounterEntity> teamReviewCounterMap = teamReviewCounterEntityList
                .stream()
                .collect(Collectors.toMap(
                        TeamReviewCounterEntity::getReviewId,
                        teamReviewCounterEntity -> teamReviewCounterEntity));

        // TeamReviewEntity, TeamReviewCounterEntity -> TeamReviewForGet
        List<TeamReviewForGet> reviewForGetList = new ArrayList<>();

        for (TeamReviewEntity review : teamReviewEntityList) {
            reviewForGetList.add(TeamReviewForGet
                    .fromReviewAndCounter(review,
                            teamReviewCounterMap.get(review.getReviewId())));
        }

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).body(reviewForGetList);

    }

    @Operation(summary = "팀 리뷰 좋아요 Up")
    @PutMapping("/teamReview/inThumbsUp")
    public ResponseEntity<Void> increaseThumbsUpByTidAndReviewId(@RequestParam String reviewId) {

        TeamReviewThumbs teamReviewThumbs = TeamReviewThumbs.builder()
                .reviewId(reviewId)
                .type(ThumbsType.IncreaseThumbsUp.getTypeNumber())
                .build();

        sendMessage(teamReviewThumbs);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "팀 리뷰 좋아요 Down")
    @PutMapping("/teamReview/deThumbsUp")
    public ResponseEntity<Void> decreaseThumbsUpByTidAndReviewId(@RequestParam String reviewId) {
        TeamReviewThumbs teamReviewThumbs = TeamReviewThumbs.builder()
                .reviewId(reviewId)
                .type(ThumbsType.DecreaseThumbsUp.getTypeNumber())
                .build();

        sendMessage(teamReviewThumbs);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "팀 리뷰 싫어요 Up")
    @PutMapping("/teamReview/inThumbsDown")
    public ResponseEntity<Void> increaseThumbsDownByTidAndReviewId(@RequestParam String reviewId) {
        TeamReviewThumbs teamReviewThumbs = TeamReviewThumbs.builder()
                .reviewId(reviewId)
                .type(ThumbsType.IncreaseThumbsDown.getTypeNumber())
                .build();

        sendMessage(teamReviewThumbs);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "팀 리뷰 싫어요 Down")
    @PutMapping("/teamReview/deThumbsDown")
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
