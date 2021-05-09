package com.example.pfairplayservice.api;

import com.example.pfairplayservice.TestEntityGenerator;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MemberRepository memberRepository;


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
    @DisplayName("Not found get /member/{uid}")
    public void failToGetMemberByUid() {

        // given
        String savedUid = "found";
        String queryUid = "notFound";
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setUid(savedUid);
        givenMemberEntity.setName("setid");
        memberRepository.save(givenMemberEntity);
        savedUid = givenMemberEntity.getUid();
        System.out.println(savedUid);

        assert savedUid != queryUid;
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(savedUid);
        System.out.println(optionalMemberEntity.get().toString());

        // when
//        TestRestTemplate testRestTemplate = new TestRestTemplate();
//        ResponseEntity<Member> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/member/" + savedUid, Member.class);
//
//        // then
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//
//        // remove source
//        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed post /member")
    public void succeedToPostMember() {
        // given
        Member givenMember = TestEntityGenerator.generateMember();
        System.out.println(givenMember.toString());

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/member", givenMember, Void.class);

        // then

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }


}
