package com.example.pfairplayservice.controller;


import com.example.pfairplayservice.common.exception.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.RequiredParamNotFoundException;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.common.filter.FilterManager;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.model.get.MemberForGet;
import com.example.pfairplayservice.model.post.MemberForPost;
import com.example.pfairplayservice.model.put.MemberForPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/member/{uid}")
    public ResponseEntity<MemberForGet> findByUid(@PathVariable String uid) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(uid);

        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", uid));
        }

        return ResponseEntity.status(HttpStatus.OK).body(MemberForGet.from(memberEntity.get()));
    }

    @PostMapping("/member")
    public ResponseEntity<Void> createMember(@RequestBody MemberForPost memberForPost) {
        EntityFieldValueChecker.checkMemberPostFieldValue(memberForPost);
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberId(memberForPost.getId());
        if (memberEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        memberRepository.save(memberForPost.toMemberEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/member/{uid}")
    public ResponseEntity<Void> updateByUid(@PathVariable String uid, @RequestBody MemberForPut memberForPut) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(uid);
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("uid{%s} not found", uid));
        }

        EntityFieldValueChecker.checkMemberPutFieldValue(memberForPut);

        if (memberEntity.get().getAddress() != memberForPut.getAddress())
            memberRepository.updateAddressByUid(uid, memberForPut.getAddress());
        if (memberEntity.get().getPhoneNumber() != memberForPut.getAddress())
            memberRepository.updatePhoneNumberByUid(uid, memberForPut.getPhoneNumber());
        if (memberEntity.get().getPreferPosition() != memberForPut.getPreferPosition().getPosition())
            memberRepository.updatePreferPositionByUid(uid, memberForPut.getPreferPosition().getPosition());
        if (memberEntity.get().getLevel() != memberForPut.getLevel())
            memberRepository.updateLevelByUid(uid, memberForPut.getLevel());
        if (memberEntity.get().getPhoneNumberDisclosureOption() != memberForPut.getPhoneNumberDisclosureOption().getDisclosureOption())
            memberRepository.updatePhoneNumberDisclosureOptionByUid(uid, memberForPut.getPhoneNumberDisclosureOption().getDisclosureOption());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/member/{uid}")
    public ResponseEntity<Void> deleteByUid(@PathVariable String uid) {

        Optional<MemberEntity> member = memberRepository.findById(uid);

        if (!member.isPresent()) {
            throw new RequiredParamNotFoundException(String.format("uid{%s} not found", uid));
        }
        memberRepository.deleteById(uid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/member/team/{tid}")
    public ResponseEntity<List<MemberForGet>> findMemberListByTid(@PathVariable String tid) {
        List<MemberEntity> memberEntityList = memberRepository.findByMemberTeamIdTid(tid);

        if (memberEntityList == null)
            new SourceNotFoundException(String.format("member not found registered in tid{%s})", tid));

        List<MemberForGet> memberList = memberEntityList.stream().map(FilterManager::teamMemberFilter).map(MemberForGet::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(memberList);
    }


}
