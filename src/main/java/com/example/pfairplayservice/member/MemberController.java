package com.example.pfairplayservice.member;


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

import java.util.List;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> retrieveAllMembers() {
        return ResponseEntity.of(Optional.of(memberRepository.findAll()));
    }

    @GetMapping("/member/{UID}")
    public ResponseEntity<Member> findByUID(@PathVariable String UID) {
        Optional<Member> member = memberRepository.findById(UID);

        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("UID{%s} not found", UID));
        }

        return ResponseEntity.status(HttpStatus.OK).body(member.get());
    }

    @PostMapping("/member")
    public ResponseEntity<Void> createMember(@RequestBody Member saveMember) {
        if (saveMember.getName() == null ||
                saveMember.getBirthday() == null ||
                saveMember.getAddress() == null ||
                saveMember.getPhoneNumber() == null) {
            throw new requiredParamNotFoundException("이름, 생년월일, 주소, 핸드폰번호를 정확히 입력해 주세요. ");
        }
        memberRepository.save(saveMember);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/member/{UID}")
    public ResponseEntity<Void> updatePasswordByUID(@PathVariable String UID, @RequestParam String password) {
        Optional<Member> member = memberRepository.findById(UID);
        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("UID{%s} not found", UID));
        }
        member.get().setPassword(password);
        memberRepository.save(member.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/member/{UID}")
    public ResponseEntity<Void> deleteByUID(@PathVariable String UID) {

        Optional<Member> member = memberRepository.findById(UID);

        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("UID{%s} not found", UID));
        }
        memberRepository.deleteById(UID);

        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
