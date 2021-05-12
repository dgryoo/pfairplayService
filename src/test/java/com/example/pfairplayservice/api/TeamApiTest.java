package com.example.pfairplayservice.api;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.MemberTeamRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.get.MemberForGet;
import com.example.pfairplayservice.model.get.TeamForGet;
import com.example.pfairplayservice.model.post.TeamForPost;
import com.example.pfairplayservice.model.put.TeamForPut;
import com.example.pfairplayservice.util.TestEntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeamApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberTeamRepository memberTeamRepository;


    @Test
    @DisplayName("succeed /team/{tid}")
    public void succeedToGetTeamByTid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        String givenTid = givenTeamEntity.getTid();


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<TeamForGet> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/team/" + givenTid, TeamForGet.class);

        // then
        assertEquals(givenTid, responseEntity.getBody().getTid());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove source
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }


    @Test
    @DisplayName("Not found get /team/{tid}")
    public void failToGetTeamByTid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        String givenTid = givenTeamEntity.getTid();
        String queryTid = "notFound";
        assert givenTid != queryTid;

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<TeamForGet> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/team/" + queryTid, TeamForGet.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // remove source
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed post /team")
    public void succeedToPostTeam() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        String givenMemberUid = givenMemberEntity.getUid();

        TeamForPost givenTeam = TestEntityGenerator.generateTeamForPut(givenMemberUid);

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/team", givenTeam, Void.class);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("conflict post /team by duplicated teamName")
    public void conflictToPostTeam() {

        // given
        String duplicatedTeamName = "duplicate";

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        givenTeamEntity.setTeamName(duplicatedTeamName);
        teamRepository.save(givenTeamEntity);

        TeamForPost givenTeam = TestEntityGenerator.generateTeamForPut(givenMemberEntity.getUid());
        givenTeam.setTeamName(duplicatedTeamName);


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/team", givenTeam, Void.class);

        // then
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("succeed put /team/{tid}")
    public void succeedToPutTeam() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        String givenTid = givenTeamEntity.getTid();
        String updatedTeamName = "업데이트된팀이름";
        String updatedAddress = "업데이트된주소";
        Date updatedFoundDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String updatedFoundDateToString = simpleDateFormat.format(updatedFoundDate);

        assert (givenTeamEntity.getTeamName() != updatedTeamName
                && givenTeamEntity.getActivityAreaAddress() != updatedAddress
                && givenTeamEntity.getFoundDate() != updatedFoundDate);

        TeamForPut givenTeam = TestEntityGenerator.generateTeamForPut();
        givenTeam.setTeamName(updatedTeamName);
        givenTeam.setActivityAreaAddress(updatedAddress);
        givenTeam.setFoundDate(updatedFoundDate);


        HttpEntity<TeamForPut> givenHttpEntity = new HttpEntity<>(givenTeam);

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/team/" + givenTid
                , HttpMethod.PUT
                , givenHttpEntity
                , Void.class);

        // then
        Optional<TeamEntity> updatedTeamEntity = teamRepository.findById(givenTid);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTeamName, updatedTeamEntity.get().getTeamName());
        assertEquals(updatedAddress, updatedTeamEntity.get().getActivityAreaAddress());
        assertEquals(updatedFoundDateToString, updatedTeamEntity.get().getFoundDate().toString());

        // remove source
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed delete /team/{tid}")
    public void succeedToDeleteTeamByTid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        String givenTid = givenTeamEntity.getTid();
        boolean expect = false;

        String queryString = "?uid=" + givenMemberEntity.getUid();

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/team/" + givenTid + queryString
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);

        // then
        Optional<TeamEntity> expectEmpty = teamRepository.findById(givenTid);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expect, expectEmpty.isPresent());

        // remove source
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("unauthorized delete /team/{tid} by unauthorized user")
    public void failToDeleteTeamByTid() {

        // given
        MemberEntity givenAuthorizedMemberEntity = TestEntityGenerator.generateMemberEntity();
        MemberEntity givenUnauthorizedMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenAuthorizedMemberEntity);
        memberRepository.save(givenUnauthorizedMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenAuthorizedMemberEntity);
        teamRepository.save(givenTeamEntity);

        String givenTid = givenTeamEntity.getTid();

        String queryString = "?uid=" + givenUnauthorizedMemberEntity.getUid();

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/team/" + givenTid + queryString
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // remove source
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenAuthorizedMemberEntity);
        memberRepository.delete(givenUnauthorizedMemberEntity);

    }

    @Test
    @DisplayName("succeed get /member/{tid}")
    public void succeedToGetTeamListByUid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity0 = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        TeamEntity givenTeamEntity1 = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity0);
        teamRepository.save(givenTeamEntity1);

        MemberTeamEntity memberTeamEntity0 = TestEntityGenerator.generateMemberTeamEntity(givenMemberEntity.getUid(), givenTeamEntity0.getTid());
        MemberTeamEntity memberTeamEntity1 = TestEntityGenerator.generateMemberTeamEntity(givenMemberEntity.getUid(), givenTeamEntity1.getTid());
        memberTeamRepository.save(memberTeamEntity0);
        memberTeamRepository.save(memberTeamEntity1);

        String givenUid = givenMemberEntity.getUid();
        int expectSize = 2;


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<List<TeamForGet>> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/team/member/" + givenUid
                , HttpMethod.GET
                , HttpEntity.EMPTY
                , new ParameterizedTypeReference<List<TeamForGet>>() {
                });

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectSize, responseEntity.getBody().size());

    }

}
