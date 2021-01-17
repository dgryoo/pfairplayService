package com.example.pfairplayservice.model;

import com.example.pfairplayservice.common.filter.FilterManager;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Team {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String tid;

    private String teamName;

    private Member teamLeadMember;

    private String activityAreaAddress;

    private List<Member> memberList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date registrationDate;

    private Date foundDate;

    public static Team from(TeamEntity team) {
        Team result = new Team(team.getTid(), team.getTeamName(), FilterManager.teamLeadMemberFilter(Member.from(team.getTeamLeadMember()))
                , team.getActivityAreaAddress(), Member.fromList(team.getMemberEntityList()), team.getRegistrationDate(), team.getFoundDate());
        return result;
    }

    public static List<Team> fromList(List<TeamEntity> teamEntityList) {
        if (teamEntityList == null) return null;
        List<Team> teamListTemp = null;
        teamEntityList
                .stream()
                .forEach(teamEntity -> teamListTemp.add(Team.from(teamEntity)));
        return teamListTemp;
    }

    public static TeamEntity to(Team team) {
        TeamEntity result = new TeamEntity(team.getTid(),team.getTeamName(),Member.to(team.getTeamLeadMember())
            , team.getActivityAreaAddress(), Member.toList(team.getMemberList()),team.getRegistrationDate(),team.getFoundDate());
        return result;
    }

    public static List<TeamEntity> toList(List<Team> teamList) {
        if (teamList == null) return null;
        List<TeamEntity> teamEntityListTemp = null;
        teamList
                .stream()
                .forEach(team -> teamEntityListTemp.add(Team.to(team)));
        return teamEntityListTemp;
    }

}
