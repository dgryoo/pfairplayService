package project.pfairplay.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.pfairplay.api.common.exception.deprecated.EntityFieldValueChecker;
import project.pfairplay.api.common.exception.deprecated.SourceNotFoundException;
import project.pfairplay.api.common.filter.FilterManager;
import project.pfairplay.api.model.get.TeamForGet;
import project.pfairplay.api.model.post.TeamForPost;
import project.pfairplay.api.model.put.TeamForPut;
import project.pfairplay.storage.mysql.model.MemberEntity;
import project.pfairplay.storage.mysql.model.TeamEntity;
import project.pfairplay.storage.mysql.repository.MemberRepository;
import project.pfairplay.storage.mysql.repository.TeamRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/team/{tid}")
    public ResponseEntity<TeamForGet> findByTid(@PathVariable String tid) {
        Optional<TeamEntity> teamEntity = teamRepository.findById(tid);
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
        }

        teamEntity.get().setTeamLeadMember(FilterManager.teamLeadMemberFilter(teamEntity.get().getTeamLeadMember()));

        return ResponseEntity.status(HttpStatus.OK).body(TeamForGet.from(teamEntity.get()));
    }

    @PostMapping("/team")
    public ResponseEntity<Void> createTeam(@RequestBody TeamForPost teamForPost) {

        EntityFieldValueChecker.checkTeamPostFieldValue(teamForPost);

        Optional<MemberEntity> memberEntity = memberRepository.findById(teamForPost.getTeamLeadMemberUid());
        if (!memberEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("uid{%s} not found", teamForPost.getTeamLeadMemberUid()));
        }

        List<TeamEntity> teamEntityList = teamRepository.findByTeamName(teamForPost.getTeamName());
        for (TeamEntity teamEntity : teamEntityList) {
            if (teamForPost.getTeamLeadMemberUid().equals(teamEntity.getTeamLeadMember().getUid())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }

        teamRepository.save(teamForPost.toTeamEntity(memberEntity.get()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/team/{tid}")
    public ResponseEntity<Void> updateTeamByTid(@PathVariable String tid, @RequestBody TeamForPut teamForPut) {

        EntityFieldValueChecker.checkTeamPutFieldValue(teamForPut);

        Optional<TeamEntity> teamEntity = teamRepository.findById(tid);
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
        }

        if (teamEntity.get().getTeamName() != teamForPut.getTeamName())
            teamRepository.updateTeamNameByTid(tid, teamForPut.getTeamName());
        if (teamEntity.get().getActivityAreaAddress() != teamForPut.getTeamName())
            teamRepository.updateActivityAreaAddressByTid(tid, teamForPut.getActivityAreaAddress());
        if (teamEntity.get().getFoundDate() != teamForPut.getFoundDate())
            teamRepository.updateFoundDateByTid(tid, teamForPut.getFoundDate());


        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @DeleteMapping("/team/{tid}")
    public ResponseEntity<Void> deleteByTid(@PathVariable String tid, @RequestParam String uid) {
        Optional<TeamEntity> teamEntity = teamRepository.findById(tid);

        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
        }

        if (!isTeamLeader(teamEntity.get(), uid)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } 

        teamRepository.deleteById(tid);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/team/member/{uid}")
    public ResponseEntity<List<TeamForGet>> findTeamListByUid(@PathVariable String uid) {
        List<TeamEntity> teamEntityList = teamRepository.findByMemberTeamIdUid(uid);

        if (teamEntityList == null)
            throw new SourceNotFoundException(String.format("team not found uid{%s} registered)", uid));

        List<TeamForGet> teamList = teamEntityList.stream().map(FilterManager::teamMemberFilter).map(TeamForGet::from).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(teamList);
    }

    @PatchMapping("/team/recommend/{tid}")
    public ResponseEntity<Void> recommendTeam(@PathVariable String tid) {
        // tid의 TeamEntity가 존재하는지 확인
        Optional<TeamEntity> teamEntity = teamRepository.findById(tid);
        if (!teamEntity.isPresent()) {
            throw new SourceNotFoundException(String.format("tid{%s} not found", tid));
        }

        // 추천 적용
        teamRepository.recommendTeamByTid(tid);

        // return
        return ResponseEntity.status(HttpStatus.OK).build();


    }

    private boolean isTeamLeader(TeamEntity teamEntity, String uid) {
        if (teamEntity.getTeamLeadMember().getUid().equals(uid)) return true;
        return false;
    }

}