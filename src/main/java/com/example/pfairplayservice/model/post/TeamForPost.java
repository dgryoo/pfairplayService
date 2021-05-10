package com.example.pfairplayservice.model.post;

import com.example.pfairplayservice.jpa.model.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamForPost {

    private String teamName;

    private String teamLeadMemberUid;

    private String activityAreaAddress;

    private Date foundDate;

    public TeamEntity toTeamEntity() {

        return TeamEntity.builder()
                .teamName(getTeamName())
                .activityAreaAddress(getActivityAreaAddress())
                .registrationDate(new Date())
                .foundDate(getFoundDate())
                .build();
    }

}
