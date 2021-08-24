package project.pfairplay.counter.controller.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;
import project.pfairplay.storage.kafka.model.TeamReviewThumbs;
import project.pfairplay.storage.mysql.repository.TeamReviewCounterRepository;

@RestController
public class TeamReviewCounterController {

    @Autowired
    private TeamReviewCounterRepository teamReviewCounterRepository;

    @KafkaListener(topics = "thumbs", groupId = "thumbsGroup", containerFactory = "thumbsChangeListener")
    public void updateThumbs(TeamReviewThumbs teamReviewThumbs) {

        switch (teamReviewThumbs.getType()) {
            case 0:
                teamReviewCounterRepository.increaseThumbsUpByReviewId(teamReviewThumbs.getReviewId());
                break;
            case 1:
                teamReviewCounterRepository.decreaseThumbsUpByReviewId(teamReviewThumbs.getReviewId());
                break;
            case 2:
                teamReviewCounterRepository.increaseThumbsDownByReviewId(teamReviewThumbs.getReviewId());
                break;
            case 3:
                teamReviewCounterRepository.decreaseThumbsDownByReviewId(teamReviewThumbs.getReviewId());
                break;

        }

    }

}
