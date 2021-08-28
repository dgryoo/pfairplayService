package project.pfairplay.counter.controller.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;
import project.pfairplay.storage.kafka.model.TeamReviewThumbs;

@RestController
public class RdsTeamReviewController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @KafkaListener(topics = "rdsThumbs", groupId = "rdsThumbsGroup", containerFactory = "thumbsChangeListener")
    public void updateThumbsByRedis(TeamReviewThumbs teamReviewThumbs) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        switch (teamReviewThumbs.getType()) {
            case 0:
                valueOperations.increment(teamReviewThumbs.getReviewId() + "Up");
                break;
            case 1:
                valueOperations.decrement(teamReviewThumbs.getReviewId() + "Up");
                break;
            case 2:
                valueOperations.increment(teamReviewThumbs.getReviewId() + "Down");
                break;
            case 3:
                valueOperations.decrement(teamReviewThumbs.getReviewId() + "Down");
                break;

        }
    }

}
