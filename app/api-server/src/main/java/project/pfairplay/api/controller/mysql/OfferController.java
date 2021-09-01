package project.pfairplay.api.controller.mysql;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.pfairplay.api.common.exception.deprecated.EntityFieldValueChecker;
import project.pfairplay.api.common.exception.deprecated.SourceNotFoundException;
import project.pfairplay.api.common.filter.FilterManager;
import project.pfairplay.api.model.get.OfferForGet;
import project.pfairplay.api.model.post.OfferForPost;
import project.pfairplay.storage.mysql.model.MatchEntity;
import project.pfairplay.storage.mysql.model.OfferEntity;
import project.pfairplay.storage.mysql.model.TeamEntity;
import project.pfairplay.storage.mysql.repository.MatchRepository;
import project.pfairplay.storage.mysql.repository.OfferRepository;
import project.pfairplay.storage.mysql.repository.TeamRepository;

import java.util.Optional;

@RestController
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Operation(summary = "매치에 참여 요청")
    @PostMapping("/offer")
    public ResponseEntity<Void> createOffer(@RequestBody OfferForPost offerForPost) {

        // RequestBody의 field값 확인
        EntityFieldValueChecker.checkOfferPostFieldValue(offerForPost);

        // 해당 matchNo의 Match가 있는지 확인
        Optional<MatchEntity> matchEntity = matchRepository.findById(offerForPost.getTargetMatchNo());

        if (!matchEntity.isPresent())
            throw new SourceNotFoundException(String.format("MatchNo : {%s}의 Match가 없습니다.", offerForPost.getTargetMatchNo()));

        // 해당 sandTeamtid의 Team이 있는지 확인
        Optional<TeamEntity> sandTeamEntity = teamRepository.findById(offerForPost.getSandTeamTid());

        if (!sandTeamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("SandTeam tid{%s} not found", offerForPost.getSandTeamTid()));
        }

        // 해당 recieveTeamtid의 Team이 있는지 확인
        Optional<TeamEntity> receiveTeamEntity = teamRepository.findById(offerForPost.getReceiveTeamTid());

        if (!receiveTeamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("ReceiveTeam tid{%s} not found", offerForPost.getReceiveTeamTid()));
        }

        // 이미 sandTeam이 해당 Match에 대한 Offer를 보냈는지 확인

        Optional<OfferEntity> offerEntity =
                offerRepository.findDuplicatedOfferByTidAndMid(offerForPost.getTargetMatchNo(), offerForPost.getSandTeamTid(), offerForPost.getReceiveTeamTid());

        if (offerEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // offer 생성
        offerRepository.save(offerForPost.toOfferEntity(matchEntity.get(), sandTeamEntity.get(), receiveTeamEntity.get()));

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "매치 참여 요청 상세 조회")
    @GetMapping("/offer/{offerNo}")
    public ResponseEntity<OfferForGet> findByOfferNo(@PathVariable int offerNo) {

        // 해당 offerNo Offer가 있는지 확인
        Optional<OfferEntity> offerEntity = offerRepository.findById(offerNo);

        if (!offerEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("OfferNo : {%s}에 해당하는 Offer가 없습니다.", offerNo));
        }

        // return ResponseEntity with filtered model
        return ResponseEntity.status(HttpStatus.OK).body(OfferForGet.from(FilterManager.offerFilter(offerEntity.get())));

    }

    @Operation(summary = "매치 참여 요청 삭제")
    @DeleteMapping("/offer/{offerNo}")
    public ResponseEntity<Void> deleteByOfferNo(@PathVariable int offerNo, @RequestParam String tid) {

        // 해당 offerNo Offer가 있는지 확인
        Optional<OfferEntity> offerEntity = offerRepository.findById(offerNo);

        if (!offerEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("OfferNo : {%s}에 해당하는 Offer가 없습니다.", offerNo));
        }

        // 보낸사람과 tid가 같지 않을 시 권한없음으로 거부
        if (!offerEntity.get().getSandTeam().getTid().equals(tid))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Operation(summary = "매치 참여 요청 수락")
    @PutMapping("/offer/accept/{offerNo}")
    public ResponseEntity<Void> acceptOfferByOfferNo(@PathVariable int offerNo, @RequestParam String tid) {

        // 해당 offerNo Offer가 있는지 확인
        Optional<OfferEntity> offerEntity = offerRepository.findById(offerNo);

        if (!offerEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("OfferNo : {%s}에 해당하는 Offer가 없습니다.", offerNo));
        }

        // offer를 수락한 tid와 request한 tid가 같지 않으면 권한없음
        if (!offerEntity.get().getReceiveTeam().getTid().equals(tid))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        // 수락한 사람이 owner인지 guest인지 확인
        String guestTeam;

        if (offerEntity.get().getTargetMatch().getOwnerTeam().getTid().equals(tid)) {
            guestTeam = offerEntity.get().getTargetMatch().getGuestTeam().getTid();
        } else {
            guestTeam = offerEntity.get().getTargetMatch().getOwnerTeam().getTid();
        }

        // Match, Offer의 status를 수락상태로 만들고, Match에 guestTeam 등록
        matchRepository.dealMatch(offerEntity.get().getTargetMatch().getMatchNo());
        matchRepository.dealMatch(offerEntity.get().getTargetMatch().getMatchNo(), guestTeam);
        offerRepository.acceptOffer(offerNo);

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Operation(summary = "매치 참여 요청 거절")
    @PutMapping("/offer/reject/{offerNo}")
    public ResponseEntity<Void> rejectOfferByOfferNo(@PathVariable int offerNo, @RequestParam String tid) {

        // 해당 offerNo Offer가 있는지 확인
        Optional<OfferEntity> offerEntity = offerRepository.findById(offerNo);

        if (!offerEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("OfferNo : {%s}에 해당하는 Offer가 없습니다.", offerNo));
        }

        // offer를 수락한 tid와 request한 tid가 같지 않으면 권한없음
        if (!offerEntity.get().getReceiveTeam().getTid().equals(tid))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        // Offer의 status를 거절상태로 만듬
        offerRepository.rejectOffer(offerNo);

        // return ResponseEntity
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
