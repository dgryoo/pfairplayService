package com.example.pfairplayservice.jpa.controller;

import com.example.pfairplayservice.common.exception.deprecated.MatchTimeOverlapException;
import com.example.pfairplayservice.common.exception.deprecated.SourceNotFoundException;
import com.example.pfairplayservice.controller.MatchController;
import com.example.pfairplayservice.jpa.model.MatchEntity;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.PlayGroundEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MatchRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.get.MatchForGet;
import com.example.pfairplayservice.model.post.MatchForPost;
import com.example.pfairplayservice.model.put.MatchForPut;
import com.example.pfairplayservice.util.TestEntityGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchControllerTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private MatchController matchController;

    @Test
    @DisplayName("Succeed to get match by matchNo")
    public void succeedToGetMatchByMatchNo() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);

        PlayGroundEntity givenPlayGroundEntity = TestEntityGenerator.generatePlayGroundEntity();

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity,givenPlayGroundEntity);

        int givenMatchNo = givenMatchEntity.getMatchNo();
        when(matchRepository.findById(givenMatchNo)).thenReturn(Optional.of(givenMatchEntity));

        // when
        ResponseEntity<MatchForGet> result = matchController.findByMatchNo(givenMatchNo);

        // then
        assertEquals(givenMatchNo, result.getBody().getMatchNo());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // verify
        verify(matchRepository, times(1)).findById(givenMatchNo);

    }

    @Test
    @DisplayName("Fail to get match by not exist matchNo")
    public void failToGetMatchByNotExistMatchNo() {

        // given
        int notExistMatchNo = 9999;
        when(matchRepository.findById(notExistMatchNo)).thenReturn(Optional.empty());

        // when, then
        assertThrows(SourceNotFoundException.class, () -> matchController.findByMatchNo(notExistMatchNo));

        // verify
        verify(matchRepository, times(1)).findById(notExistMatchNo);


    }

    @Test
    @DisplayName("Succeed to post match")
    public void succeedToPostMatch() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setUid("givenUid");

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        givenTeamEntity.setTid("givenTid");

        MatchForPost givenMatchForPost = TestEntityGenerator.generateMatchForPost(givenTeamEntity.getTid());

        when(matchRepository.findAllByTid(givenMatchForPost.getOwnerTeamTid())).thenReturn(null);
        when(teamRepository.findById(givenMatchForPost.getOwnerTeamTid())).thenReturn(Optional.of(givenTeamEntity));

        // when
        ResponseEntity<Void> result = matchController.createMatch(givenMatchForPost);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // verify
        verify(matchRepository, times(1)).findAllByTid(givenTeamEntity.getTid());
        verify(teamRepository, times(1)).findById(givenMatchForPost.getOwnerTeamTid());

    }

    @Test
    @DisplayName("fail to post match by overlap match time")
    public void failToPostMatchByOverlapMatchTime() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setUid("givenUid");

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        givenTeamEntity.setTid("givenTid");

        PlayGroundEntity givenPlayGroundEntity = TestEntityGenerator.generatePlayGroundEntity();

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity, givenPlayGroundEntity);

        MatchForPost givenMatchForPost = TestEntityGenerator.generateMatchForPost(givenTeamEntity.getTid());

        Date overlapDate = new Date();
        givenMatchEntity.setStartDate(overlapDate);
        givenMatchEntity.setEndDate(overlapDate);
        givenMatchForPost.setStartDate(overlapDate);
        givenMatchForPost.setEndDate(overlapDate);

        List<MatchEntity> overlapMatchEntityList = new ArrayList<>();
        overlapMatchEntityList.add(givenMatchEntity);

        when(matchRepository.findAllByTid(givenMatchForPost.getOwnerTeamTid())).thenReturn(overlapMatchEntityList);

        // when, then
        assertThrows(MatchTimeOverlapException.class, () -> matchController.createMatch(givenMatchForPost));

        // verify
        verify(matchRepository, times(1)).findAllByTid(givenTeamEntity.getTid());

    }

    @Test
    @DisplayName("fail to post match by not exist tid")
    public void failToPostMatchByNotExistTid() {

        // given
        String notExistTid = "not exist";

        MatchForPost givenMatchForPost = TestEntityGenerator.generateMatchForPost(notExistTid);

        when(matchRepository.findAllByTid(givenMatchForPost.getOwnerTeamTid())).thenReturn(null);
        when(teamRepository.findById(givenMatchForPost.getOwnerTeamTid())).thenReturn(Optional.empty());


        // when, then
        assertThrows(SourceNotFoundException.class, () -> matchController.createMatch(givenMatchForPost));

        // verify
        verify(matchRepository, times(1)).findAllByTid(givenMatchForPost.getOwnerTeamTid());
        verify(teamRepository, times(1)).findById(givenMatchForPost.getOwnerTeamTid());

    }

    @Test
    @DisplayName("succeed to update match by matchNo")
    public void succeedToUpdateMatchByMatchNo() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setUid("givenUid");

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        givenTeamEntity.setTid("givenTid");

        PlayGroundEntity givenPlayGroundEntity = TestEntityGenerator.generatePlayGroundEntity();

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity, givenPlayGroundEntity);
        givenMatchEntity.setMatchNo(1);

        MatchForPut givenMatchForPut = TestEntityGenerator.generateMatchForPut(givenMatchEntity.getOwnerTeam().getTid());


        when(matchRepository.findById(givenMatchEntity.getMatchNo())).thenReturn(Optional.of(givenMatchEntity));
        when(matchRepository.findAllByTid(givenMatchEntity.getOwnerTeam().getTid())).thenReturn(null);

        // when
        ResponseEntity<Void> result = matchController.updateByMatchNo(givenMatchEntity.getMatchNo(), givenMatchForPut);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // verify
        verify(matchRepository, times(1)).findById(givenMatchEntity.getMatchNo());
        verify(matchRepository, times(1)).findAllByTid(givenMatchEntity.getOwnerTeam().getTid());

    }

    @Test
    @DisplayName("fail to put match by unauthorized tid")
    public void failToPutMatchByUnauthorizedTid() {

        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setUid("givenUid");

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        givenTeamEntity.setTid("givenTid");

        PlayGroundEntity givenPlayGroundEntity = TestEntityGenerator.generatePlayGroundEntity();

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity, givenPlayGroundEntity);
        givenMatchEntity.setMatchNo(1);

        String unauthorizedTid = "unauthorized";

        MatchForPut givenMatchForPut = TestEntityGenerator.generateMatchForPut(unauthorizedTid);

        assert givenMatchEntity.getOwnerTeam().getTid() != givenMatchForPut.getOwnerTeamTid();

        when(matchRepository.findById(givenMatchEntity.getMatchNo())).thenReturn(Optional.of(givenMatchEntity));

        // when
        ResponseEntity<Void> result = matchController.updateByMatchNo(givenMatchEntity.getMatchNo(), givenMatchForPut);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());

        // verify
        verify(matchRepository, times(1)).findById(givenMatchEntity.getMatchNo());

    }

    @Test
    @DisplayName("succeed to delete match by matchNo")
    public void succeedToDeleteMatchByMatchNo() {
        // given
        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        givenMemberEntity.setUid("givenUid");

        TeamEntity givenTeamEntity = TestEntityGenerator.generateTeamEntity(givenMemberEntity);
        givenTeamEntity.setTid("givenTid");

        PlayGroundEntity givenPlayGroundEntity = TestEntityGenerator.generatePlayGroundEntity();

        MatchEntity givenMatchEntity = TestEntityGenerator.generateMatchEntity(givenTeamEntity, givenPlayGroundEntity);
        givenMatchEntity.setMatchNo(1);

        when(matchRepository.findById(givenMatchEntity.getMatchNo())).thenReturn(Optional.of(givenMatchEntity));

        // when
        ResponseEntity<Void> result = matchController.deleteByMatchNo(givenMatchEntity.getMatchNo(), givenMatchEntity.getOwnerTeam().getTid());

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // verify
        verify(matchRepository, times(1)).findById(givenMatchEntity.getMatchNo());

    }

}
