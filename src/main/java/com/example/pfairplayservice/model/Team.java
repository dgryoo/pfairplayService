package com.example.pfairplayservice.model;

import com.example.pfairplayservice.jpa.model.TeamEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Team {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String tid;

    private String teamName;

    private Member teamLeadMember;

    private String activityAreaAddress;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Date registrationDate;

    private Date foundDate;

    public static Team from(TeamEntity teamEntity) {

        return Team.builder()
                .tid(teamEntity.getTid())
                .teamName(teamEntity.getTeamName())
                .teamLeadMember(Member.from(teamEntity.getTeamLeadMember()))
                .activityAreaAddress(teamEntity.getActivityAreaAddress())
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
                .registrationDate(getRegistrationDate())
                .foundDate(getFoundDate())
                .build();
    }

}
