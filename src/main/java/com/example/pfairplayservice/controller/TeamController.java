package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.Member;
import com.example.pfairplayservice.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/team/{tid}")
    public ResponseEntity<Team> findByUID(@PathVariable String tid) {
        Optional<TeamEntity> team = teamRepository.findById(tid);
        if (!team.isPresent()) {
            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Team.from(team.get()));
    }

}
