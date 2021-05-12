package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.id.MemberTeamId;
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
    public ResponseEntity<Void> createMemberTeam(@RequestBody MemberTeamId memberTeamId) {
        Optional<TeamEntity> teamEntity = teamRepository.findById(memberTeamId.getTid());
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("TID{%s} not found", memberTeamId.getTid()));
        }

        Optional<MemberEntity> memberEntity = memberRepository.findById(memberTeamId.getUid());
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", memberTeamId.getUid()));
        }

        Optional<MemberTeamEntity> memberTeamEntity = memberTeamRepository.findById(memberTeamId);
        if (memberTeamEntity.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        MemberTeamEntity willSaveMemberTeamEntity = MemberTeamEntity.builder()
                .memberTeamId(memberTeamId)
                .build();

        memberTeamRepository.save(willSaveMemberTeamEntity);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/memberTeam")
    public ResponseEntity<Void> deleteMemberTeam(@RequestBody MemberTeamId memberTeamId) {

        Optional<TeamEntity> teamEntity = teamRepository.findById(memberTeamId.getTid());
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("TID{%s} not found", memberTeamId.getTid()));
        }

        Optional<MemberEntity> memberEntity = memberRepository.findById(memberTeamId.getUid());
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("UID{%s} not found", memberTeamId.getUid()));
        }

        Optional<MemberTeamEntity> memberTeamEntity = memberTeamRepository.findById(memberTeamId);
        if (!memberTeamEntity.isPresent()) {
            throw new SourceNotFoundException("해당 팀의 멤버는 존재하지 않습니다.");
        }

        memberTeamRepository.deleteById(memberTeamId);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
