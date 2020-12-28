package com.example.pfairplayservice.member;


import com.example.pfairplayservice.testjpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
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
    public EntityModel<Member> createMember(@RequestBody Member saveMember) {

        Member member = memberRepository.save(saveMember);

        EntityModel<Member> entityModel;

        entityModel = new EntityModel<>(member);

        return entityModel;

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
