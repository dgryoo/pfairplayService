package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.MemberRepository;
import com.example.pfairplayservice.jpa.repository.MemberTeamRepository;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class MemberTeamController {

    @Autowired
    private MemberTeamRepository memberTeamRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/memberTeam")
    public ResponseEntity<Void> createMemberTeam(@RequestBody MemberTeamEntity memberTeam) {
        Optional<TeamEntity> teamEntity = teamRepository.findById(memberTeam.getMemberTeamId().getTid());
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("TID{%s} not found", memberTeam.getMemberTeamId().getTid()));
        }

        Optional<MemberEntity> memberEntity = memberRepository.findById(memberTeam.getMemberTeamId().getUid());
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", memberTeam.getMemberTeamId().getUid()));
        }

        Optional<MemberTeamEntity> memberTeamEntity = memberTeamRepository.findById(memberTeam.getMemberTeamId());
        if (memberTeamEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        memberTeamRepository.save(memberTeam);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/memberTeam")
    public ResponseEntity<Void> deleteMemberTeam(@RequestBody MemberTeamEntity memberTeam) {

        Optional<TeamEntity> teamEntity = teamRepository.findById(memberTeam.getMemberTeamId().getTid());
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("TID{%s} not found", memberTeam.getMemberTeamId().getTid()));
        }

        Optional<MemberEntity> memberEntity = memberRepository.findById(memberTeam.getMemberTeamId().getUid());
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", memberTeam.getMemberTeamId().getUid()));
        }

        Optional<MemberTeamEntity> memberTeamEntity = memberTeamRepository.findById(memberTeam.getMemberTeamId());
        if (!memberTeamEntity.isPresent()) {
            throw new SourceNotFoundException("해당 팀의 멤버는 존재하지 않습니다.");
        }

        memberTeamRepository.delete(memberTeam);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
