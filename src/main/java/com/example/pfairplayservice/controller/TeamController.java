package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.common.filter.FilterManager;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/team/{tid}")
    public ResponseEntity<Team> findByTid(@PathVariable String tid) {
        Optional<TeamEntity> team = teamRepository.findById(tid);
        if (!team.isPresent()) {
            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
        }

        team.get().setTeamLeadMember(FilterManager.teamLeadMemberFilter(team.get().getTeamLeadMember()));

        return ResponseEntity.status(HttpStatus.OK).body(Team.from(team.get()));
    }

    @GetMapping("/team/member/{uid}")
    public ResponseEntity<List<Team>> findTeamListByUid(@PathVariable String uid) {
        List<TeamEntity> teamEntityList = teamRepository.findByMemberTeamIdUid(uid);

        if (teamEntityList == null)
            new SourceNotFoundException(String.format("team not found uid{%s} registered)", uid));

        List<Team> teamList = teamEntityList.stream().map(FilterManager::teamMemberFilter).map(Team::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(teamList);
    }

}