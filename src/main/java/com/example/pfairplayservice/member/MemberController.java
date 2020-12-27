package com.example.pfairplayservice.member;


import com.example.pfairplayservice.testjpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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



}
