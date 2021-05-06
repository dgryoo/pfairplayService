package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.TestEntityGenerator;
import com.example.pfairplayservice.model.origin.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberRepository memberRepository;

    private MemberEntity givenMemberEntity;


    @Test
    @DisplayName("succeed /member/{uid}")
    public void succeedToGetMemberByUid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        String givenUid = givenMemberEntity.getUid();

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Member> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/member/" + givenUid, Member.class);

        // then
        assertEquals(givenUid, responseEntity.getBody().getUid());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // remove source
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("Not found /member/{uid}")
    public void failToGetMemberByUid() {

        // given
        String savedUid = "found";
        String queryUid = "notFound";
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setUid(savedUid);
        memberRepository.save(givenMemberEntity);
        assert savedUid != queryUid;

        // when=
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Member> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/member/" + queryUid, Member.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        // remove source
        memberRepository.delete(givenMemberEntity);

    }


}
