package controller;

import mysql.model.*;
import mysql.model.TeamReviewCounterEntity;
import mysql.model.TeamReviewEntity;
import mysql.repository.TeamReviewCounterRepository;
import mysql.repository.TeamReviewRepository;
import model.get.TeamReviewForGet;
import model.post.TeamReviewForPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TeamReviewController {

    final int limitValue = 10;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TeamReviewRepository teamReviewRepository;

    @Autowired
    private TeamReviewCounterRepository teamReviewCounterRepository;

    QTeamReviewEntity qTeamReviewEntity;

    QTeamReviewCounterEntity qTeamReviewCounterEntity;



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

    @GetMapping("/teamReview/tid")
    public ResponseEntity<List<TeamReviewForGet>> findTeamReviewListByTid(@RequestParam String tid,
                                                                          @RequestParam Integer page) {
        // Get JPAQueryFactory
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // Get TeamReivew List by Querydsl
        List<TeamReviewEntity> teamReviewEntityList = queryFactory
                .selectFrom(qTeamReviewEntity)
                .where(qTeamReviewEntity.tid.eq(tid))
                .offset((page-1)*limitValue+1)
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
}
