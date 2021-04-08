package com.example.pfairplayservice.controller;

import com.example.pfairplayservice.common.exception.SourceNotFoundException;
import com.example.pfairplayservice.common.filter.FilterManager;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.example.pfairplayservice.jpa.repository.TeamRepository;
import com.example.pfairplayservice.model.origin.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/team")
    public ResponseEntity<Void> createTeam(@RequestBody Team team) {

        List<TeamEntity> teamEntityList = teamRepository.findByTeamName(team.getTeamName());
        for (TeamEntity teamEntity : teamEntityList) {
            if (team.getTeamLeadMember().getUid().equals(teamEntity.getTeamLeadMember().getUid())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        teamRepository.save(team.toTeamEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
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