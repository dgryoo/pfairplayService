package com.example.pfairplayservice.model;

import com.example.pfairplayservice.common.filter.FilterManager;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
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

    public static Team from(TeamEntity teamEntity) {

        return Team.builder()
                .tid(teamEntity.getTid())
                .teamName(teamEntity.getTeamName())
                .teamLeadMember(Member.from(teamEntity.getTeamLeadMember()))
                .activityAreaAddress(teamEntity.getActivityAreaAddress())
                .memberList(teamEntity.getMemberEntityList().stream().map(Member::from).collect(Collectors.toList()))
                .registrationDate(teamEntity.getRegistrationDate())
                .foundDate(teamEntity.getFoundDate())
                .build();

    }

    public TeamEntity toTeamEntity() {

        return TeamEntity.builder()
                .tid(getTid())
                .teamName(getTeamName())
                .teamLeadMember(getTeamLeadMember().toMemberEntity())
                .activityAreaAddress(getActivityAreaAddress())
                .memberEntityList(getMemberList().stream().map(Member::toMemberEntity).collect(Collectors.toList()))
                .registrationDate(getRegistrationDate())
                .foundDate(getFoundDate())
                .build();

    }

}
