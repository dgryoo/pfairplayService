package com.example.pfairplayservice.api;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.MemberTeamRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.enumfield.DisClosureOption;
import com.example.pfairplayservice.model.enumfield.Position;
import com.example.pfairplayservice.model.get.MemberForGet;
import com.example.pfairplayservice.model.post.MemberForPost;
import com.example.pfairplayservice.model.put.MemberForPut;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberTeamRepository memberTeamRepository;


    @Test
    @DisplayName("succeed /member/{uid}")
    public void succeedToGetMemberByUid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        String givenUid = givenMemberEntity.getUid();


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<MemberForGet> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/member/" + givenUid, MemberForGet.class);

        // then
        assertEquals(givenUid, responseEntity.getBody().getUid());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove source
        memberRepository.delete(givenMemberEntity);

    }


    @Test
    @DisplayName("Not found get /member/{uid}")
    public void failToGetMemberByUid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        String givenUid = givenMemberEntity.getUid();
        String queryUid = "notFound";
        assert givenUid != queryUid;

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<MemberForGet> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/member/" + queryUid, MemberForGet.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // remove source
        memberRepository.deleteById(givenMemberEntity.getUid());

    }

    @Test
    @DisplayName("succeed post /member")
    public void succeedToPostMember() {

        // given
        MemberForPost givenMember = TestEntityGenerator.generateMemberForPost();

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/member", givenMember, Void.class);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("conflict post /member by duplicated Id")
    public void ConflictToPostMember() {

        // given
        String duplicatedId = "duplicate";

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setId(duplicatedId);
        memberRepository.save(givenMemberEntity);

        MemberForPost givenMember = TestEntityGenerator.generateMemberForPost();
        givenMember.setId(duplicatedId);


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/member", givenMember, Void.class);

        // then

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("succeed delete /member/{uid}")
    public void succeedToPutMember() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        String givenUid = givenMemberEntity.getUid();
        String updatedAddress = "업데이트된주소";
        String updatedPhoneNumber = "01099999999";
        Position updatedPosition = Position.MF;
        int updatedLevel = 5;
        DisClosureOption updatedDisclosureOption = DisClosureOption.NOBODY;

        assert (givenMemberEntity.getAddress() != updatedAddress
                && givenMemberEntity.getPhoneNumber() != updatedPhoneNumber
                && givenMemberEntity.getPreferPosition() != updatedPosition.getPosition()
                && givenMemberEntity.getLevel() != updatedLevel
                && givenMemberEntity.getPhoneNumberDisclosureOption() != updatedDisclosureOption.getDisclosureOption());

        MemberForPut givenMember = TestEntityGenerator.generateMemberForPut();
        givenMember.setAddress(updatedAddress);
        givenMember.setPhoneNumber(updatedPhoneNumber);
        givenMember.setPreferPosition(updatedPosition);
        givenMember.setLevel(updatedLevel);
        givenMember.setPhoneNumberDisclosureOption(updatedDisclosureOption);


        HttpEntity<MemberForPut> givenHttpEntity = new HttpEntity<>(givenMember);

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/member/" + givenUid
                , HttpMethod.PUT
                , givenHttpEntity
                , Void.class);

        // then

        Optional<MemberEntity> updatedMemberEntity = memberRepository.findById(givenUid);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedAddress, updatedMemberEntity.get().getAddress());
        assertEquals(updatedPhoneNumber, updatedMemberEntity.get().getPhoneNumber());
        assertEquals(updatedPosition, Position.from(updatedMemberEntity.get().getPreferPosition()));
        assertEquals(updatedLevel, updatedMemberEntity.get().getLevel());
        assertEquals(updatedDisclosureOption, DisClosureOption.from(updatedMemberEntity.get().getPhoneNumberDisclosureOption()));


        // remove source
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed delete /member/{uid}")
    public void succeedToDeleteMemberByUid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        String givenUid = givenMemberEntity.getUid();
        boolean expect = false;


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/member/" + givenUid
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);

        // then
        Optional<MemberEntity> expectEmpty = memberRepository.findById(givenUid);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expect, expectEmpty.isPresent());

    }

    @Test
    @DisplayName("succeed delete /member/{tid}")
    public void succeedToGetMemberListByTid() {

        // given
        MemberEntity givenMemberEntity0 = TestEntityGenerator.generateMemberEntity();
        MemberEntity givenMemberEntity1 = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity0);
        memberRepository.save(givenMemberEntity1);

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity0);
        teamRepository.save(givenTeamEntity);

        MemberTeamEntity memberTeamEntity0 = TestEntityGenerator.generateMemberTeamEntity(givenMemberEntity0.getUid(), givenTeamEntity.getTid());
        MemberTeamEntity memberTeamEntity1 = TestEntityGenerator.generateMemberTeamEntity(givenMemberEntity1.getUid(), givenTeamEntity.getTid());
        memberTeamRepository.save(memberTeamEntity0);
        memberTeamRepository.save(memberTeamEntity1);

        String givenTid = givenTeamEntity.getTid();
        int expectSize = 2;


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<List<MemberForGet>> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/member/team/" + givenTid
                , HttpMethod.GET
                , HttpEntity.EMPTY
                , new ParameterizedTypeReference<List<MemberForGet>>() {
                });

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectSize, responseEntity.getBody().size());

    }

}
