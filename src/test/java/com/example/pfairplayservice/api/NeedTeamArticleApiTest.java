package com.example.pfairplayservice.api;


import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.NeedTeamArticleEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.NeedTeamArticleRepository;
import com.example.pfairplayservice.model.enumfield.Position;
import com.example.pfairplayservice.model.get.MemberForGet;
import com.example.pfairplayservice.model.get.NeedTeamArticleForGet;
import com.example.pfairplayservice.model.post.NeedTeamArticleForPost;
import com.example.pfairplayservice.model.put.NeedTeamArticleForPut;
import com.example.pfairplayservice.model.summarized.SummarizedNeedTeamArticle;
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
public class NeedTeamArticleApiTest {

    @LocalServerPort
    private int port;

    @Autowired
    private NeedTeamArticleRepository needTeamArticleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("succeed to get /needTeamArticle{articleNo}")
    public void succeedToGetNeedTeamArticle() {

        // given

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        NeedTeamArticleEntity givenNeedTeamArticleEntity = TestEntityGenerator.generateNeedTeamArticleEntity(givenMemberEntity);
        needTeamArticleRepository.save(givenNeedTeamArticleEntity);

        int givenArticleNo = givenNeedTeamArticleEntity.getArticleNo();

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<NeedTeamArticleForGet> responseEntity = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/needTeamArticle/" + givenArticleNo
                , NeedTeamArticleForGet.class);


        // then

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(givenArticleNo, responseEntity.getBody().getArticleNo());


        // remove resource
        needTeamArticleRepository.deleteById(givenArticleNo);
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed to post /needTeamArticle")
    public void succeedToPostNeedTeamArticle() {

        // given

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        NeedTeamArticleForPost givenNeedTeamArticleForPost = TestEntityGenerator.generateNeedTeamArticleForPost(givenMemberEntity.getUid());

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/needTeamArticle"
                , givenNeedTeamArticleForPost
                , Void.class);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());


        // remove resource
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("succeed to put /needTeamArticle/{articleNo}")
    public void succeedToPutNeedTeamArticle() {

        // given

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        NeedTeamArticleEntity givenNeedTeamArticleEntity = TestEntityGenerator.generateNeedTeamArticleEntity(givenMemberEntity);
        needTeamArticleRepository.save(givenNeedTeamArticleEntity);

        NeedTeamArticleForPut givenNeedTeamArticleForPut = TestEntityGenerator.generateNeedTeamArticleForPut(givenMemberEntity.getUid());
        givenNeedTeamArticleForPut.setSubject("updatedSubject");
        givenNeedTeamArticleForPut.setDetail("updatedDetail");
        givenNeedTeamArticleForPut.setNeedPosition(Position.MF);

        HttpEntity<NeedTeamArticleForPut> givenHttpEntity = new HttpEntity<>(givenNeedTeamArticleForPut);

        int givenArticleNo = givenNeedTeamArticleEntity.getArticleNo();
        String expectSubject = givenNeedTeamArticleForPut.getSubject();
        String expectDetail = givenNeedTeamArticleForPut.getDetail();
        Position expectPosition = givenNeedTeamArticleForPut.getNeedPosition();

        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/needTeamArticle/" + givenArticleNo
                , HttpMethod.PUT
                , givenHttpEntity
                , Void.class);

        // then
        Optional<NeedTeamArticleEntity> updatedNeedTeamArticleEntity = needTeamArticleRepository.findById(givenArticleNo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectSubject, updatedNeedTeamArticleEntity.get().getSubject());
        assertEquals(expectDetail, updatedNeedTeamArticleEntity.get().getDetail());
        assertEquals(expectPosition, Position.from(updatedNeedTeamArticleEntity.get().getNeedPosition()));

        // remove resource
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("fail to put /needTeamArticle/{articleNo} by unauthorized member")
    public void failToPutNeedTeamArticle() {

        // given

        MemberEntity authorizedMemberEntity = TestEntityGenerator.generateMemberEntity();
        MemberEntity unauthorizedMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(authorizedMemberEntity);
        memberRepository.save(unauthorizedMemberEntity);

        NeedTeamArticleEntity givenNeedTeamArticleEntity = TestEntityGenerator.generateNeedTeamArticleEntity(authorizedMemberEntity);
        needTeamArticleRepository.save(givenNeedTeamArticleEntity);

        NeedTeamArticleForPut givenNeedTeamArticleForPut = TestEntityGenerator.generateNeedTeamArticleForPut(unauthorizedMemberEntity.getUid());

        HttpEntity<NeedTeamArticleForPut> givenHttpEntity = new HttpEntity<>(givenNeedTeamArticleForPut);

        int givenArticleNo = givenNeedTeamArticleEntity.getArticleNo();


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/needTeamArticle/" + givenArticleNo
                , HttpMethod.PUT
                , givenHttpEntity
                , Void.class);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // remove resource
        memberRepository.delete(authorizedMemberEntity);
        memberRepository.delete(unauthorizedMemberEntity);

    }

    @Test
    @DisplayName("succeed to delete /needTeamArticle/{articleNo}")
    public void succeedToDeleteNeedTeamArticle() {

        // given

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        NeedTeamArticleEntity givenNeedTeamArticleEntity = TestEntityGenerator.generateNeedTeamArticleEntity(givenMemberEntity);
        needTeamArticleRepository.save(givenNeedTeamArticleEntity);

        String givenUid = givenMemberEntity.getUid();
        int givenArticleNo = givenNeedTeamArticleEntity.getArticleNo();
        boolean expectIsPresent = false;


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/needTeamArticle/" + givenArticleNo + "?uid=" + givenUid
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);

        // then
        Optional<NeedTeamArticleEntity> expectEmpty = needTeamArticleRepository.findById(givenArticleNo);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectIsPresent, expectEmpty.isPresent());


        // remove resource
        memberRepository.delete(givenMemberEntity);

    }

    @Test
    @DisplayName("fail to delete /needTeamArticle/{articleNo} by unauthorized member")
    public void failToDeleteNeedTeamArticle() {

        // given

        MemberEntity authorizedMemberEntity = TestEntityGenerator.generateMemberEntity();
        MemberEntity unauthorizedMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(authorizedMemberEntity);
        memberRepository.save(unauthorizedMemberEntity);

        NeedTeamArticleEntity givenNeedTeamArticleEntity = TestEntityGenerator.generateNeedTeamArticleEntity(authorizedMemberEntity);
        needTeamArticleRepository.save(givenNeedTeamArticleEntity);

        String authorizedUid = authorizedMemberEntity.getUid();
        String unauthorizedUid = unauthorizedMemberEntity.getUid();
        assert authorizedUid != unauthorizedUid;

        int givenArticleNo = givenNeedTeamArticleEntity.getArticleNo();


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/needTeamArticle/" + givenArticleNo + "?uid=" + unauthorizedUid
                , HttpMethod.DELETE
                , HttpEntity.EMPTY
                , Void.class);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

        // remove resource
        memberRepository.delete(authorizedMemberEntity);
        memberRepository.delete(unauthorizedMemberEntity);

    }

    @Test
    @DisplayName("succeed get /needTeamArticle")
    public void succeedToGetAllSummarizedNeedTeamArticle() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        memberRepository.save(givenMemberEntity);

        NeedTeamArticleEntity givenNeedTeamArticleEntity0 = TestEntityGenerator.generateNeedTeamArticleEntity(givenMemberEntity);
        NeedTeamArticleEntity givenNeedTeamArticleEntity1 = TestEntityGenerator.generateNeedTeamArticleEntity(givenMemberEntity);
        needTeamArticleRepository.save(givenNeedTeamArticleEntity0);
        needTeamArticleRepository.save(givenNeedTeamArticleEntity1);

        int expectSize = 2;


        // when
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<List<SummarizedNeedTeamArticle>> responseEntity = testRestTemplate.exchange(
                "http://localhost:" + port + "/needTeamArticle"
                , HttpMethod.GET
                , HttpEntity.EMPTY
                , new ParameterizedTypeReference<List<SummarizedNeedTeamArticle>>() {
                });

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectSize, responseEntity.getBody().size());

        // remove resource
        memberRepository.delete(givenMemberEntity);

    }

}
