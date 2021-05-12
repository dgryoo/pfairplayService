package com.example.pfairplayservice.api;

import com.example.pfairplayservice.jpa.id.MemberTeamId;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.MemberTeamRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
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
public class MemberTeamApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberTeamRepository memberTeamRepository;

    @Test
    @DisplayName("succeed to post /memberTeam")
    public void succeedToPostMemberTeam() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        MemberTeamId givenMemberTeamId = TestEntityGenerator.generateMemberTeamId(givenMemberEntity.getUid(), givenTeamEntity.getTid());


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port +"/memberTeam", givenMemberTeamId, Void.class);

        // then
        Optional<MemberTeamEntity> memberTeamEntity = memberTeamRepository.findById(givenMemberTeamId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(givenMemberEntity.getUid(), memberTeamEntity.get().getMemberTeamId().getUid());
        assertEquals(givenTeamEntity.getTid(), memberTeamEntity.get().getMemberTeamId().getTid());

        // remove resource
        memberTeamRepository.delete(memberTeamEntity.get());
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);


    }

    @Test
    @DisplayName("fail to post /memberTeam by duplicated Id")
    public void failToPostMemberTeam() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        MemberTeamEntity givenMemberTeamEntity = TestEntityGenerator.generateMemberTeamEntity(givenMemberEntity.getUid(), givenTeamEntity.getTid());
        memberTeamRepository.save(givenMemberTeamEntity);

        MemberTeamId givenMemberTeamId = givenMemberTeamEntity.getMemberTeamId();


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port +"/memberTeam", givenMemberTeamId, Void.class);

        // then
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());


        // remove resource
        memberTeamRepository.delete(givenMemberTeamEntity);
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);


    }

    @Test
    @DisplayName("succeed to delete /memberTeam")
    public void succeedToDeleteMemberTeamByMemberTeamId() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        teamRepository.save(givenTeamEntity);

        MemberTeamEntity givenMemberTeamEntity = TestEntityGenerator.generateMemberTeamEntity(givenMemberEntity.getUid(), givenTeamEntity.getTid());
        memberTeamRepository.save(givenMemberTeamEntity);

        MemberTeamId givenMemberTeamId = givenMemberTeamEntity.getMemberTeamId();

        HttpEntity<MemberTeamId> givenHttpEntity = new HttpEntity<>(givenMemberTeamId);
        boolean expectIsPresent = false;


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/memberTeam"
                , HttpMethod.DELETE
                , givenHttpEntity
                , Void.class);

        // then
        Optional<MemberTeamEntity> expectEmpty = memberTeamRepository.findById(givenMemberTeamId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectIsPresent, expectEmpty.isPresent());


        // remove resource
        memberTeamRepository.delete(givenMemberTeamEntity);
        teamRepository.delete(givenTeamEntity);
        memberRepository.delete(givenMemberEntity);


    }

}
