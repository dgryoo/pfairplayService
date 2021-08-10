package controller;

import cassandra.repository.CasTeamReviewCounterRepository;
import mysql.repository.TeamReviewCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CasTeamReviewCounterController {

    @Autowired
    private CasTeamReviewCounterRepository casTeamReviewCounterRepository;

    @PutMapping("/cas/teamReview/inThumbsUp")
    public ResponseEntity<Void> increaseThumbsUpByTidAndReviewId(@RequestParam String tid,
                                                                 @RequestParam String reviewId) {
        casTeamReviewCounterRepository.increaseThumbsUpByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/cas/teamReview/deThumbsUp")
    public ResponseEntity<Void> decreaseThumbsUpByTidAndReviewId(@RequestParam String tid,
                                                                 @RequestParam String reviewId) {
        casTeamReviewCounterRepository.decreaseThumbsUpByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/cas/teamReview/inThumbsDown")
    public ResponseEntity<Void> increaseThumbsDownByTidAndReviewId(@RequestParam String tid,
                                                                   @RequestParam String reviewId) {
        casTeamReviewCounterRepository.increaseThumbsDownByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/cas/teamReview/deThumbsDown")
    public ResponseEntity<Void> decreaseThumbsDownByTidAndReviewId(@RequestParam String tid,
                                                                   @RequestParam String reviewId) {
        casTeamReviewCounterRepository.decreaseThumbsDownByTidAndReviewId(tid, reviewId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
