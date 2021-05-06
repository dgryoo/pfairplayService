package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.TestEntityGenerator;
import com.example.pfairplayservice.model.origin.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberControllerTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberController memberController;

    @Test
    @DisplayName("succeed to get member by uid")
    public void succeedToGetMemberByUid() {

        // given

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        String givenUid = givenMemberEntity.getUid();
        when(memberRepository.findById(givenUid)).thenReturn(Optional.of(givenMemberEntity));

        // when
        ResponseEntity<Member> result = memberController.findByUid(givenUid);

        // then
        assertEquals(givenUid, result.getBody().getUid());
        assertEquals(HttpStatus.OK, result.getStatusCode());

        // verify
        verify(memberRepository, times(1)).findById(givenUid);

    }

    @Test
    @DisplayName("fail to get member by uid")
    public void failToGetMemberByUid() {

        // given

        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
        String givenUid = givenMemberEntity.getUid();
        when(memberRepository.findById(givenUid)).thenReturn(Optional.empty());

        // when, then
        assertThrows(SourceNotFoundException.class, () -> memberController.findByUid(givenUid));

        // verify
        verify(memberRepository, times(1)).findById(givenUid);

    }
//
//    @Test
//    public void givenMember_whenCreateMember_thenReturnHttpStatusCreated() {
//
//        // given
//        Member givenMember = TestEntityGenerator.generateMember();
//
//        // when
//
//        ResponseEntity<Void> expectResponseEntity = memberController.createMember(givenMember);
//
//        // then
//
//        assertEquals(expectResponseEntity.getStatusCode(), HttpStatus.CREATED);
//
//        // remove source
////        memberRepository.deleteById(givenMember.getUid());
//
//    }
//
//    @Test
//    public void givenMember_whenCreateDuplicatedId_thenReturnHttpStatusConflict() {
//
//        // given
//        Member givenMember = TestEntityGenerator.generateMember();
//        givenMember.setId("duplicated");
//        Member duplicatedIdMember = TestEntityGenerator.generateMember();
//        duplicatedIdMember.setId("duplicated");
//
//        // when
//
//        memberController.createMember(givenMember);
//        ResponseEntity<Void> expectResponseEntity = memberController.createMember(givenMember);
//
//        // then
//
//        assertEquals(expectResponseEntity.getStatusCode(), HttpStatus.CONFLICT);
//
//        // remove source
//
//    }
//
//    @Test
//    public void givenMemberEntity_whenDeleteById_thenReturnHttpStatusOk() {
//
//        // given
//        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
//
//        memberRepository.save(givenMemberEntity);
//
//        // when
//
//        ResponseEntity<Void> expectResponseEntity = memberController.deleteByUid(givenMemberEntity.getUid());
//
//        // then
//
//        assertEquals(expectResponseEntity.getStatusCode(), HttpStatus.OK);
//
//
//        // remove source
//
//    }
//
//    @Test
//    public void givenMemberEntity_whenDeleteByNotExistUid_thenReturnSourceNotFoundException() {
//
//        // given
//        MemberEntity givenMemberEntity = TestEntityGenerator.generateMemberEntity();
//
//        memberRepository.save(givenMemberEntity);
//
//        // when
//
//        try {
//            ResponseEntity<Void> expectMember = memberController.deleteByUid("NotExistUid");
//        }
//
//        // then
//        catch (Exception e) {
//            assertEquals(e.getMessage(), "uid{NotExistUid} not found");
//        }
//
//        // remove source
//        memberRepository.delete(givenMemberEntity);
//
//    }
//


}
