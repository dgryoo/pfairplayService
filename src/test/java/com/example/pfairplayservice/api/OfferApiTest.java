package com.example.pfairplayservice.api;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.OfferEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MatchRepository;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.OfferRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.get.OfferForGet;
import com.example.pfairplayservice.model.post.OfferForPost;
import com.example.pfairplayservice.util.TestEntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Test
    @DisplayName("Succeed to post /offer")
    public void succeedToPostOffer() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferForPost givenOfferForPost = TestEntityGenerator.generateOfferForPost(
                givenMatchEntity.getMatchNo(), givenOwnerTeamEntity.getTid(), givenGuestTeamEntity.getTid());


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/offer"
                , givenOfferForPost, Void.class);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // remove resource
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("Fail to post /offer by duplicated offer")
    public void failToPostOfferByDuplicatedOffer() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenDuplicatedOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenDuplicatedOfferEntity);

        OfferForPost givenDuplicatedOfferForPost = TestEntityGenerator.generateOfferForPost(
                givenMatchEntity.getMatchNo(), givenOwnerTeamEntity.getTid(), givenGuestTeamEntity.getTid());

        assert (givenDuplicatedOfferEntity.getTargetMatch().getMatchNo() == givenDuplicatedOfferForPost.getTargetMatchNo()
                && givenDuplicatedOfferEntity.getSandTeam().getTid() == givenDuplicatedOfferForPost.getSandTeamTid()
                && givenDuplicatedOfferEntity.getReceiveTeam().getTid() == givenDuplicatedOfferForPost.getReceiveTeamTid());

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/offer"
                , givenDuplicatedOfferForPost, Void.class);

        // then
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenDuplicatedOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("Succeed to get /offer/{offerNO}")
    public void succeedToGetOfferByOfferNo() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenOfferEntity);


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<OfferForGet> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/offer/" + givenOfferEntity.getOfferNo()
                , OfferForGet.class);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("Succeed to delete /offer/{offerNO}")
    public void succeedToDeleteOfferByOfferNo() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenOfferEntity);

        // when

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/offer/" + givenOfferEntity.getOfferNo() + "?tid=" + givenOfferEntity.getSandTeam().getTid()
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("Fail to delete /offer/{offerNO} by unauthorized tid")
    public void failToDeleteOfferByUnauthorizedTid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenOfferEntity);

        String unauthorizedTid = "unauthorized";
        assert givenOfferEntity.getSandTeam().getTid() != unauthorizedTid;

        // when

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/offer/" + givenOfferEntity.getOfferNo() + "?tid=" + unauthorizedTid
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed to put /offer/accept/{offerNo}")
    public void succeedToAcceptOfferByOfferNo() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenOfferEntity);

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/offer/accept/" + givenOfferEntity.getOfferNo() + "?tid=" + givenOfferEntity.getReceiveTeam().getTid()
                , HttpMethod.PUT
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("Fail to put /offer/accept/{offerNO} by unauthorized tid")
    public void failToAcceptOfferByUnauthorizedTid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenOfferEntity);

        String unauthorizedTid = "unauthorized";
        assert givenOfferEntity.getReceiveTeam().getTid() != unauthorizedTid;

        // when

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/offer/accept/" + givenOfferEntity.getOfferNo() + "?tid=" + unauthorizedTid
                , HttpMethod.PUT
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed to put /offer/reject/{offerNo}")
    public void succeedToRejectOfferByOfferNo() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenOfferEntity);

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/offer/accept/" + givenOfferEntity.getOfferNo() + "?tid=" + givenOfferEntity.getReceiveTeam().getTid()
                , HttpMethod.PUT
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("Fail to put /offer/reject/{offerNO} by unauthorized tid")
    public void failToRejectOfferByUnauthorizedTid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenOwnerTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenOwnerTeamEntity);

        TeamEntity givenGuestTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenGuestTeamEntity);

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenOwnerTeamEntity);
        matchRepository.save(givenMatchEntity);

        OfferEntity givenOfferEntity = TestEntityGenerator.generateOfferEntity(givenMatchEntity, givenOwnerTeamEntity, givenGuestTeamEntity);
        offerRepository.save(givenOfferEntity);

        String unauthorizedTid = "unauthorized";
        assert givenOfferEntity.getReceiveTeam().getTid() != unauthorizedTid;

        // when

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/offer/reject/" + givenOfferEntity.getOfferNo() + "?tid=" + unauthorizedTid
                , HttpMethod.PUT
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // remove resource
        offerRepository.delete(givenOfferEntity);
        matchRepository.delete(givenMatchEntity);
        teamRepository.delete(givenOwnerTeamEntity);
        teamRepository.delete(givenGuestTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }


}
