package com.example.pfairplayservice.api;

import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MatchRepository;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.get.MatchForGet;
import com.example.pfairplayservice.model.post.MatchForPost;
import com.example.pfairplayservice.model.post.MemberForPost;
import com.example.pfairplayservice.model.put.MatchForPut;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatchApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("succeed post /match")
    public void succeedToPostMatch() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        MatchForPost givenMatchForPost = TestEntityGenerator.generateMatchForPost(givenTeamEntity.getTid());

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/match", givenMatchForPost, Void.class);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // remove source
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed get /match/{matchNo}")
    public void succeedToGetMatch() {
        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        MatchEntity giveMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity);
        matchRepository.save(giveMatchEntity);

        int givenMatchNo = giveMatchEntity.getMatchNo();


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<MatchForGet> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/match/" + givenMatchNo, MatchForGet.class);


        // then
        assertEquals(givenMatchNo, responseEntity.getBody().getMatchNo());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove source
        matchRepository.delete(giveMatchEntity);
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);
    }

    @Test
    @DisplayName("succeed put /match/{matchNo}")
    public void succeedToPutMatch() {
        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        MatchEntity giveMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity);
        matchRepository.save(giveMatchEntity);

        int givenMatchNo = giveMatchEntity.getMatchNo();

        MatchForPut givenMatchForPut = TestEntityGenerator.generateMatchForPut(giveMatchEntity.getOwnerTeam().getTid());

        HttpEntity<MatchForPut> givenHttpEntity = new HttpEntity<>(givenMatchForPut);


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/match/" + givenMatchNo
                , HttpMethod.PUT
                , givenHttpEntity
                , Void.class);


        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove source
        matchRepository.delete(giveMatchEntity);
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);
    }

    @Test
    @DisplayName("succeed delete /match/{matchNo}")
    public void succeedToDeleteMatch() {
        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        MatchEntity giveMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity);
        matchRepository.save(giveMatchEntity);

        int givenMatchNo = giveMatchEntity.getMatchNo();
        boolean expect = false;


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/match/" + givenMatchNo
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);


        // then
        Optional<MatchEntity> expectEmpty = matchRepository.findById(givenMatchNo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expect, expectEmpty.isPresent());

        // remove source
        matchRepository.delete(giveMatchEntity);
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);
    }



}
