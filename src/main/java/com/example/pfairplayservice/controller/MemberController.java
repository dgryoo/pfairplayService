package com.example.pfairplayservice.controller;


import com.example.pfairplayservice.common.exception.EntityFieldValueChecker;
import com.example.pfairplayservice.common.exception.RequiredParamNotFoundException;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.common.filter.FilterManager;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.model.modifier.MemberModifier;
import com.example.pfairplayservice.model.origin.Member;
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
    public ResponseEntity<Member> findByUid(@PathVariable String uid) {
        Optional<MemberEntity> member = memberRepository.findById(uid);

        if (!member.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", uid));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Member.from(member.get()));
    }

    @PostMapping("/member")
    public ResponseEntity<Void> createMember(@RequestBody Member member) {
        EntityFieldValueChecker.checkMemberPostFieldValue(member);
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberId(member.getId());
        if (memberEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        memberRepository.save(member.toMemberEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/member/{uid}")
    public ResponseEntity<Void> updateByUid(@PathVariable String uid, @RequestBody MemberModifier memberModifier) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(uid);
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("uid{%s} not found", uid));
        }

        EntityFieldValueChecker.checkMemberPutFieldValue(memberModifier);

        if (memberEntity.get().getAddress() != memberModifier.getAddress())
            memberRepository.updateAddressByUid(uid, memberModifier.getAddress());
        if (memberEntity.get().getPhoneNumber() != memberModifier.getAddress())
            memberRepository.updatePhoneNumberByUid(uid, memberModifier.getPhoneNumber());
        if (memberEntity.get().getPreferPosition() != memberModifier.getPreferPosition().getPosition())
            memberRepository.updatePreferPositionByUid(uid, memberModifier.getPreferPosition().getPosition());
        if (memberEntity.get().getLevel() != memberModifier.getLevel())
            memberRepository.updateLevelByUid(uid, memberModifier.getLevel());
        if (memberEntity.get().getPhoneNumberDisclosureOption() != memberModifier.getPhoneNumberDisclosureOption().getDisclosureOption())
            memberRepository.updatePhoneNumberDisclosureOptionByUid(uid, memberModifier.getPhoneNumberDisclosureOption().getDisclosureOption());

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
    public ResponseEntity<List<Member>> findMemberListByTid(@PathVariable String tid) {
        List<MemberEntity> memberEntityList = memberRepository.findByMemberTeamIdTid(tid);

        if (memberEntityList == null)
            new SourceNotFoundException(String.format("member not found registered in tid{%s})", tid));

        List<Member> memberList = memberEntityList.stream().map(FilterManager::teamMemberFilter).map(Member::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(memberList);
    }


}
