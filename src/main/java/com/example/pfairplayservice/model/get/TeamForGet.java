package com.example.pfairplayservice.model.get;

import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamForGet {

    private String tid;

    private String teamName;

    private MemberForGet teamLeadMember;

    private String activityAreaAddress;

    private Date registrationDate;

    private Date foundDate;

    public static TeamForGet from(TeamEntity teamEntity) {

        return TeamForGet.builder()
                .tid(teamEntity.getTid())
                .teamName(teamEntity.getTeamName())
                .teamLeadMember(MemberForGet.from(teamEntity.getTeamLeadMember()))
                .activityAreaAddress(teamEntity.getActivityAreaAddress())
                .registrationDate(teamEntity.getRegistrationDate())
                .foundDate(teamEntity.getFoundDate())
                .build();
    }

}
