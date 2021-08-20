package project.pfairplay.api.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.pfairplay.api.model.get.TeamReviewForGet;
import project.pfairplay.storage.mysql.model.QTeamReviewEntity;
import project.pfairplay.storage.mysql.model.TeamReviewEntity;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RdsTeamReviewController {

    final int limitValue = 10;

    final List<TeamReviewForGet> emptyList = new ArrayList<>();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EntityManager entityManager;

    QTeamReviewEntity qTeamReviewEntity = QTeamReviewEntity.teamReviewEntity;

    @GetMapping("/redis/teamReview/tid")
    public ResponseEntity<List<TeamReviewForGet>> findTeamReviewListByTid(@RequestParam String tid,
                                                                    @RequestParam Integer page) {
        // Get JPAQueryFactory
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // Get TeamReviewList by Querydsl
        List<TeamReviewEntity> teamReviewEntityList = queryFactory
                .selectFrom(qTeamReviewEntity)
                .where(qTeamReviewEntity.tid.eq(tid))
                .offset((page - 1) * limitValue)
                .limit(limitValue)
                .fetch();

        // TeamReviewList null empty check, if null return emptyList
        if(teamReviewEntityList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(emptyList);
        }

        // Get TeamReviewCounter List Redis
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        Map<String, List<String>> reviewCounterMap = teamReviewEntityList.stream()
                .collect(Collectors.toMap(
                        TeamReviewEntity::getReviewId,
                        teamReviewEntity -> {
                            return valueOperations.multiGet(Arrays.asList(
                                    teamReviewEntity.getReviewId() + "Up", teamReviewEntity.getReviewId() + "Down"));
                        }));

        // TeamForGetList from teamReviewEntityList, reviewCounterMap
        List<TeamReviewForGet> teamReviewForGetList = teamReviewEntityList
                .stream()
                .map(teamReviewEntity -> TeamReviewForGet.fromReviewAndCounter(teamReviewEntity, reviewCounterMap))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(teamReviewForGetList);


    }

    @PutMapping("/redis/teamReview/inThumbsUp")
    public ResponseEntity<Void> increaseThumbsUpByTidAndReviewId(@RequestParam String reviewId) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.increment(reviewId + "Up");

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/redis/teamReview/deThumbsUp")
    public ResponseEntity<Void> decreaseThumbsUpByTidAndReviewId(@RequestParam String reviewId) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.decrement(reviewId + "Up");

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/redis/teamReview/inThumbsDown")
    public ResponseEntity<Void> increaseThumbsDownByTidAndReviewId(@RequestParam String reviewId) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.increment(reviewId + "Down");

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/redis/teamReview/deThumbsDown")
    public ResponseEntity<Void> decreaseThumbsDownByTidAndReviewId(@RequestParam String reviewId) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.decrement(reviewId + "Down");

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
