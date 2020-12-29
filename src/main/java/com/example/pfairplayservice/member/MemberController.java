package com.example.pfairplayservice.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
    public EntityModel<Member> findByUID(@PathVariable String UID) {
        Optional<Member> member = memberRepository.findById(UID);

        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("UID{%s} not found", UID));
        }

        EntityModel<Member> entityModel;
        entityModel = new EntityModel<>(member.get());

        return entityModel;
    }

    @PostMapping("/member")
    public ResponseEntity<Void> createMember(@RequestBody Member saveMember) {
        memberRepository.save(saveMember);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/member/{UID}")
    public int updatePasswordByUID(@PathVariable String UID, @RequestParam String password) {
        Optional<Member> member = memberRepository.findById(UID);

        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("UID{%s} not found", UID));
        }
        System.out.println("requestparam password : " + password);
        member.get().setPassword(password);
        System.out.println("updated member password : " + member.get().getPassword());
        memberRepository.save(member.get());
        return 1;

    }

    @DeleteMapping("/member/{UID}")
    public void deleteByUID(@PathVariable String UID) {

        Optional<Member> member = memberRepository.findById(UID);

        if (!member.isPresent()) {
            throw new MemberNotFoundException(String.format("UID{%s} not found", UID));
        }
        memberRepository.deleteById(UID);
    }


}
