package com.example.pfairplayservice.controller;


import com.example.pfairplayservice.common.exception.RequiredParamNotFoundException;
import com.example.pfairplayservice.common.exception.SourceNotFoundException;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
        // TODO check id length

        if (saveMember.getName() == null ||
                saveMember.getBirthday() == null ||
                saveMember.getAddress() == null ||
                saveMember.getPhoneNumber() == null) {
            throw new RequiredParamNotFoundException("이름, 생년월일, 주소, 핸드폰번호를 정확히 입력해 주세요. ");
        }
        memberRepository.save(Member.to(saveMember));
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


}
