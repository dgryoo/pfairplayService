package com.example.pfairplayservice.controller;


import com.example.pfairplayservice.common.exception.LengthOverException;
import com.example.pfairplayservice.common.exception.RequiredParamNotFoundException;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.common.filter.FilterManager;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.model.Member;
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
    public ResponseEntity<Member> findByUID(@PathVariable String uid) {
        Optional<MemberEntity> member = memberRepository.findById(uid);

        if (!member.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", uid));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Member.from(member.get()));
    }

    @PostMapping("/member")
    public ResponseEntity<Void> createMember(@RequestBody Member saveMember) {
        if (saveMember.getId().length() > 10) throw new LengthOverException("id는 10자를 초과 할 수 없습니다.");
        if (saveMember.getName() == null) throw new RequiredParamNotFoundException("이름을 정확히 입력해주세요");
        if (saveMember.getBirthday() == null) throw new RequiredParamNotFoundException("생년월일을 정확히 입력해주세요");
        if (saveMember.getAddress() == null) throw new RequiredParamNotFoundException("주소를 정확히 입력해주세요");
        if (saveMember.getPhoneNumber() == null) throw new RequiredParamNotFoundException("이름을 정확히 입력해주세요");

        memberRepository.save(saveMember.toMemberEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/member/{uid}")
    public ResponseEntity<Void> updatePasswordByUID(@PathVariable String uid, @RequestParam String password) {
        Optional<MemberEntity> member = memberRepository.findById(uid);
        if (!member.isPresent()) {
            throw new SourceNotFoundException(String.format("uid{%s} not found", uid));
        }
        member.get().setPassword(password);
        memberRepository.save(member.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/member/{uid}")
    public ResponseEntity<Void> deleteByUID(@PathVariable String uid) {

        Optional<MemberEntity> member = memberRepository.findById(uid);

        if (!member.isPresent()) {
            throw new RequiredParamNotFoundException(String.format("uid{%s} not found", uid));
        }
        memberRepository.deleteById(uid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/member/team/{tid}")
    public ResponseEntity<List<Member>> findTeamListByUid(@PathVariable String tid) {
        List<MemberEntity> memberEntityList = memberRepository.findByMemberTeamIdTid(tid);

        if (memberEntityList == null)
            new SourceNotFoundException(String.format("member not found registered in tid{%s})", tid));

        List<Member> memberList = memberEntityList.stream().map(FilterManager::teamMemberFilter).map(Member::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(memberList);
    }


}
